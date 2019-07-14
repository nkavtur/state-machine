package home.nkavtur.distributedstatemachine.statemachine.action;

import org.springframework.scheduling.Trigger;

import java.util.Date;

public abstract class OneTimeAction<E> implements Action<E> {

    private final Trigger trigger;
    private E event;

    public OneTimeAction() {
        this.trigger = triggerContext -> {
            if (triggerContext.lastCompletionTime() != null) {
                return null;
            }
            return new Date();
        };
    }

    @Override
    public Trigger getTrigger() {
        return trigger;
    }

    @Override
    public E getEvent() {
        return event;
    }

    @Override
    public void setEvent(E event) {
        this.event = event;
    }
}
