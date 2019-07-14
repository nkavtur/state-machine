package home.nkavtur.distributedstatemachine.statemachine.state;

import home.nkavtur.distributedstatemachine.statemachine.action.Action;

public class StateMachineState<E extends Enum> implements State<E> {

    private final E event;
    private final Action action;

    public StateMachineState(E event, Action action) {
        this.event = event;
        this.action = action;
    }

    @Override
    public E getEvent() {
        return event;
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "StateMachineState{"
                + "event=" + event
                + '}';
    }
}
