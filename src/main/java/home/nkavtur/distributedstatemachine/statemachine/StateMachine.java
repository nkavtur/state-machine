package home.nkavtur.distributedstatemachine.statemachine;

import home.nkavtur.distributedstatemachine.statemachine.state.State;

import java.util.Collection;

/**
 * Provides api for very simple implementation of finite state machine.
 * For simplicity purpose it does not support restrictions on state transitions.
 * In particular, each state is triggered by the concrete one event.
 */
public interface StateMachine<E> {

    void start();

    void sendEvent(E event);

    State<E> getCurrentState();

    Collection<State<E>> getStates();

    State<E> getState(E event);

    void setCurrentState(State<E> state);

    State<E> getEndState();

}
