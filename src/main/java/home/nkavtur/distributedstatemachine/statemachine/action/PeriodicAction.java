package home.nkavtur.distributedstatemachine.statemachine.action;

import org.springframework.scheduling.Trigger;

public abstract class PeriodicAction<E> implements Action<E> {

    private final Trigger trigger;
    private E event;

    public PeriodicAction(Trigger trigger) {
        this.trigger = trigger;
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
