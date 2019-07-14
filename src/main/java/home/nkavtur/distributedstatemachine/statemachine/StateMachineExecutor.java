package home.nkavtur.distributedstatemachine.statemachine;

/**
 * Provides abstraction for the state machine operation logic.
 * Created in order to separate underlying implementation of event handling and action execution.
 */
public interface StateMachineExecutor<E> {

    void execute();

    void stop();

    void sendEvent(E event);

    void setStateMachine(StateMachine<E> stateMachine);
}
