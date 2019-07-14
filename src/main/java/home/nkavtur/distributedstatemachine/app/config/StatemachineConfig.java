package home.nkavtur.distributedstatemachine.app.config;

import static home.nkavtur.distributedstatemachine.app.Events.COMPLETED;
import static home.nkavtur.distributedstatemachine.app.Events.COMPLETING;
import static home.nkavtur.distributedstatemachine.app.Events.IN_PROGRESS;
import static home.nkavtur.distributedstatemachine.app.Events.PAUSED;
import static home.nkavtur.distributedstatemachine.app.Events.REMOVING;

import home.nkavtur.distributedstatemachine.app.Events;
import home.nkavtur.distributedstatemachine.app.tasks.CompletingAction;
import home.nkavtur.distributedstatemachine.app.tasks.InProgressAction;
import home.nkavtur.distributedstatemachine.app.tasks.PauseAction;
import home.nkavtur.distributedstatemachine.app.tasks.RemovingAction;
import home.nkavtur.distributedstatemachine.statemachine.StateMachine;
import home.nkavtur.distributedstatemachine.statemachine.StateMachineBuilder;
import home.nkavtur.distributedstatemachine.statemachine.StateMachineExecutor;
import home.nkavtur.distributedstatemachine.statemachine.action.ScheduleTaskService;
import home.nkavtur.distributedstatemachine.statemachine.action.ScheduleTaskServiceImpl;
import home.nkavtur.distributedstatemachine.statemachine.zookeeper.ZookeeperStateMachineExecutor;
import home.nkavtur.distributedstatemachine.zookeeper.ZookeeperOperations;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class StatemachineConfig {

    private final CuratorFramework client;
    private final ZookeeperOperations zookeeperTemplate;
    private final InProgressAction inProgressAction;
    private final PauseAction pauseAction;
    private final CompletingAction completingAction;
    private final RemovingAction removingAction;

    private final String path = "/state";

    public StatemachineConfig(CuratorFramework client, ZookeeperOperations zookeeperTemplate, InProgressAction inProgressAction,
                              PauseAction pauseAction, CompletingAction completingAction, RemovingAction removingAction) {
        this.client = client;
        this.zookeeperTemplate = zookeeperTemplate;
        this.inProgressAction = inProgressAction;
        this.pauseAction = pauseAction;
        this.completingAction = completingAction;
        this.removingAction = removingAction;
    }

    @Bean
    public StateMachine<Events> stateMachine(StateMachineBuilder<Events> builder) {
        return builder
                .withState(IN_PROGRESS, inProgressAction)
                .withState(PAUSED, pauseAction)
                .withState(COMPLETING, completingAction)
                .withState(REMOVING, removingAction)
                .withEndState(COMPLETED)
                .build();
    }

    @Bean
    public StateMachineBuilder<Events> stateMachineBuilder(StateMachineExecutor<Events> executor) {
        return new StateMachineBuilder<>(executor);
    }

    @Bean
    public StateMachineExecutor<Events> stateMachineExecutor() {
        return new ZookeeperStateMachineExecutor<>(client, zookeeperTemplate, scheduleTaskService(), path);
    }

    @Bean
    public ScheduleTaskService scheduleTaskService() {
        return new ScheduleTaskServiceImpl(taskScheduler());
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        return threadPoolTaskScheduler;
    }

}