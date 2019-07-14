package home.nkavtur.distributedstatemachine.statemachine.action;

import home.nkavtur.distributedstatemachine.statemachine.StateMachine;
import home.nkavtur.distributedstatemachine.statemachine.state.State;

public class ExecutionContext<E> {

    private StateMachine<E> stateMachine;
    private State<E> state;

    public ExecutionContext(StateMachine<E> stateMachine, State<E> state) {
        this.stateMachine = stateMachine;
        this.state = state;
    }

    public StateMachine<E> getStateMachine() {
        return stateMachine;
    }

    public void setStateMachine(StateMachine<E> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public State<E> getState() {
        return state;
    }

    public void setState(State<E> state) {
        this.state = state;
    }
}
