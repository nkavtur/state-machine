package home.nkavtur.distributedstatemachine.statemachine.action;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

public class ScheduleTaskServiceImpl implements ScheduleTaskService {

    private final TaskScheduler scheduler;
    private volatile Map<String, ScheduledFuture<?>> tasksMap = new ConcurrentHashMap<>();

    public ScheduleTaskServiceImpl(TaskScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedule(String id, Runnable runnable, Trigger trigger) {
        if (tasksMap.get(id) == null) {
            synchronized (this) {
                if (tasksMap.get(id) == null) {
                    ScheduledFuture<?> scheduledFuture = scheduler.schedule(runnable, trigger);
                    tasksMap.put(id, scheduledFuture);
                }
            }
        }
    }

    public void cancel(String id) {
        ScheduledFuture<?> scheduledTask = tasksMap.get(id);
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
            tasksMap.remove(id);
        }
    }

    @Override
    public void cancelAll() {
        tasksMap.values().forEach(scheduledFuture -> scheduledFuture.cancel(true));
    }
}