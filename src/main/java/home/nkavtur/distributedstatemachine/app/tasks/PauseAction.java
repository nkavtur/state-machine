package home.nkavtur.distributedstatemachine.app.tasks;

import home.nkavtur.distributedstatemachine.app.Events;
import home.nkavtur.distributedstatemachine.statemachine.action.ExecutionContext;
import home.nkavtur.distributedstatemachine.statemachine.action.OneTimeAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PauseAction extends OneTimeAction<Events> {

    private Logger logger = LoggerFactory.getLogger(PauseAction.class);

    @Override
    public void execute(ExecutionContext<Events> executionContext) {
        logger.info("Pausing ...");
    }
}
