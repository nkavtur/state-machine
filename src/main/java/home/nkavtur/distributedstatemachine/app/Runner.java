package home.nkavtur.distributedstatemachine.app;

import home.nkavtur.distributedstatemachine.statemachine.StateMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);


    private final StateMachine<Events> stateMachine;

    public Runner(StateMachine<Events> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        stateMachine.start();
        logger.info("Just after execute. State is [{}]", stateMachine.getCurrentState());

        stateMachine.start();

        while (true) {
            try {
                Thread.currentThread().sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
