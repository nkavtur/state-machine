package home.nkavtur.distributedstatemachine.statemachine;

import home.nkavtur.distributedstatemachine.statemachine.state.State;

import java.util.Collection;

public class DefaultStateMachine<E extends Enum> implements StateMachine<E> {

    private final StateMachineExecutor<E> executor;
    private final Collection<State<E>> states;
    private final State<E> endState;

    private volatile boolean isStarted;

    private State<E> currentState;

    public DefaultStateMachine(StateMachineExecutor<E> executor, Collection<State<E>> states, State<E> endState) {
        this.executor = executor;
        this.executor.setStateMachine(this);
        this.states = states;
        this.endState = endState;
    }

    @Override
    public void start() {
        if (!isStarted) {
            synchronized (this) {
                if (!isStarted) {
                    executor.execute();
                    isStarted = true;
                }
            }
        }
    }

    @Override
    public void sendEvent(E event) {
        executor.sendEvent(event);
    }

    @Override
    public State<E> getCurrentState() {
        return currentState;
    }

    @Override
    public State<E> getEndState() {
        return endState;
    }

    @Override
    public Collection<State<E>> getStates() {
        return states;
    }

    @Override
    public State<E> getState(E event) {
        return states.stream()
                .filter(s -> s.getEvent() == event)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Unknown event " + event));
    }

    @Override
    public void setCurrentState(State<E> state) {
        this.currentState = state;
    }
}
