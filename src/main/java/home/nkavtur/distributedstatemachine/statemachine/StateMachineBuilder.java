package home.nkavtur.distributedstatemachine.statemachine;

import home.nkavtur.distributedstatemachine.statemachine.action.Action;
import home.nkavtur.distributedstatemachine.statemachine.state.State;
import home.nkavtur.distributedstatemachine.statemachine.state.StateMachineState;

import java.util.ArrayList;
import java.util.Collection;

public class StateMachineBuilder<E extends Enum> {

    private final StateMachineExecutor<E> executor;

    private Collection<State<E>> states = new ArrayList<>();
    private State<E> endState;

    public StateMachineBuilder(StateMachineExecutor<E> executor) {
        this.executor = executor;
    }

    public StateMachineBuilder<E> withState(E event, Action action) {
        action.setEvent(event);
        this.states.add(new StateMachineState<>(event, action));
        return this;
    }

    public StateMachineBuilder<E> withState(E event) {
        this.states.add(new StateMachineState<>(event, null));
        return this;
    }

    /**
     * Should not have action, because it will be always interrupted the stop operation.
     */
    public StateMachineBuilder<E> withEndState(E event) {
        this.endState = new StateMachineState<>(event, null);
        return this.withState(event);
    }

    public StateMachine<E> build() {
        return new DefaultStateMachine<>(executor, states, endState);
    }


}
