package home.nkavtur.distributedstatemachine.app.tasks;

import home.nkavtur.distributedstatemachine.app.Events;
import home.nkavtur.distributedstatemachine.statemachine.action.ExecutionContext;
import home.nkavtur.distributedstatemachine.statemachine.action.PeriodicAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;

public class CompletingAction extends PeriodicAction<Events> {

    private Logger logger = LoggerFactory.getLogger(CompletingAction.class);

    public CompletingAction(Trigger trigger) {
        super(trigger);
    }

    @Override
    public void execute(ExecutionContext<Events> executionContext) {
        logger.info("Completing...");
    }
}
