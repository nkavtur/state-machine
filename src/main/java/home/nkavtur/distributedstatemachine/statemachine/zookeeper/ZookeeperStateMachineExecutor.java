package home.nkavtur.distributedstatemachine.statemachine.zookeeper;

import static java.util.Optional.ofNullable;

import home.nkavtur.distributedstatemachine.statemachine.StateMachine;
import home.nkavtur.distributedstatemachine.statemachine.StateMachineExecutor;
import home.nkavtur.distributedstatemachine.statemachine.action.Action;
import home.nkavtur.distributedstatemachine.statemachine.action.ExecutionContext;
import home.nkavtur.distributedstatemachine.statemachine.action.ScheduleTaskService;
import home.nkavtur.distributedstatemachine.statemachine.state.State;
import home.nkavtur.distributedstatemachine.zookeeper.ZookeeperOperations;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ZookeeperStateMachineExecutor<E extends Enum> implements StateMachineExecutor<E> {

    private final static Logger logger = LoggerFactory.getLogger(ZookeeperStateMachineExecutor.class);

    private StateMachine<E> stateMachine;
    private final CuratorFramework client;
    private final ZookeeperOperations zookeeperOperations;
    private final ScheduleTaskService scheduleTaskService;
    private final String path;

    private NodeCache nodeCache;

    public ZookeeperStateMachineExecutor(CuratorFramework client,
                                         ZookeeperOperations zookeeperOperations,
                                         ScheduleTaskService scheduleTaskService,
                                         String path) {
        this.client = client;
        this.zookeeperOperations = zookeeperOperations;
        this.scheduleTaskService = scheduleTaskService;
        this.path = path;
    }

    @Override
    public void execute() {
        nodeCache = new NodeCache(client, path);
        nodeCache.getListenable().addListener(() -> {
            byte[] data = zookeeperOperations.get(path);
            String eventName = ofNullable(data).map(String::new).orElse(null);
            E event = resolveEvent(eventName);
            if (eventName != null) {
                accept(event);
            }
        });

        try {
            nodeCache.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        scheduleTaskService.cancelAll();
    }

    @Override
    public void setStateMachine(StateMachine<E> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void sendEvent(E event) {
        zookeeperOperations.set(event.name(), path);
    }

    public void accept(E event) {
        State<E> newState = stateMachine.getState(event);

        logger.info("Performing transition from [{}] to [{}]", stateMachine.getCurrentState(), newState);

        exitState(stateMachine.getCurrentState());

        entryState(newState);

        if (isEndState(newState)) {
            this.stop();
            return;
        }
    }

    private boolean isEndState(State<E> newState) {
        return newState.getEvent() == stateMachine.getEndState().getEvent();
    }

    private void exitState(State<E> state) {
        ofNullable(state)
                .map(State::getAction)
                .map(Action::getEvent)
                .map(Enum::name)
                .ifPresent(scheduleTaskService::cancel);
    }

    private void entryState(State<E> state) {
        stateMachine.setCurrentState(state);

        Action<E> action = state.getAction();
        if (action == null) {
            return;
        }

        Runnable runnable = () -> action.execute(new ExecutionContext<>(stateMachine, state));
        scheduleTaskService.schedule(action.getEvent().name(), runnable, action.getTrigger());
    }

    private E resolveEvent(String name) {
        E event = stateMachine.getStates().iterator().next().getEvent();
        return Optional.ofNullable(name).map(n -> (E) Enum.valueOf(event.getClass(), n)).orElse(null);
    }

}
