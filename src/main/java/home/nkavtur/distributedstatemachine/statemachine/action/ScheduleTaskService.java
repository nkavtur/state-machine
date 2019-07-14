package home.nkavtur.distributedstatemachine.statemachine.action;

import org.springframework.scheduling.Trigger;

public interface ScheduleTaskService {

    void schedule(String id, Runnable runnable, Trigger trigger);

    void cancel(String id);

    void cancelAll();

}