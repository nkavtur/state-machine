package home.nkavtur.distributedstatemachine.statemachine.action;

import org.springframework.scheduling.Trigger;

/**
 * Action to be executed when state machine enters particular state.
 */
public interface Action<E> {

    Trigger getTrigger();

    void execute(ExecutionContext<E> executionContext);

    E getEvent();

    void setEvent(E event);
}
