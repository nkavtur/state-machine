package home.nkavtur.distributedstatemachine.app.config;

import home.nkavtur.distributedstatemachine.app.tasks.CompletingAction;
import home.nkavtur.distributedstatemachine.app.tasks.PauseAction;
import home.nkavtur.distributedstatemachine.app.tasks.InProgressAction;
import home.nkavtur.distributedstatemachine.app.tasks.RemovingAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
public class TasksConfig {

    @Bean
    public InProgressAction pollingTask() {
        return new InProgressAction(new CronTrigger("*/5 * * * * *"));
    }

    @Bean
    public CompletingAction completingTask() {
        return new CompletingAction(new CronTrigger("*/5 * * * * *"));
    }

    @Bean
    public RemovingAction removingTask() {
        return new RemovingAction();
    }

    @Bean
    public PauseAction pauseTask() {
        return new PauseAction();
    }
}
