package home.nkavtur.distributedstatemachine.statemachine.state;

import home.nkavtur.distributedstatemachine.statemachine.action.Action;

public interface State<E> {

    E getEvent();

    Action<E> getAction();
}
