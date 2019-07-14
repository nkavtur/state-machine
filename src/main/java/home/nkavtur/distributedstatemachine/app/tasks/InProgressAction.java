package home.nkavtur.distributedstatemachine.app.tasks;

import home.nkavtur.distributedstatemachine.app.Events;
import home.nkavtur.distributedstatemachine.statemachine.action.ExecutionContext;
import home.nkavtur.distributedstatemachine.statemachine.action.PeriodicAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;

public class InProgressAction extends PeriodicAction<Events> {

    private Logger logger = LoggerFactory.getLogger(InProgressAction.class);

    public InProgressAction(Trigger trigger) {
        super(trigger);
    }

    @Override
    public void execute(ExecutionContext<Events> executionContext) {
        logger.info("Running ...");
    }
}
