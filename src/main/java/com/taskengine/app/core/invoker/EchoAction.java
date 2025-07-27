package com.taskengine.app.core.invoker;

import java.util.logging.Logger;

public class EchoAction implements Action {

    private static final Logger logger = Logger.getLogger(EchoAction.class.getName());

    @Override
    public void execute(ActionExecution actionExecution) {
        logger.info("[EchoAction] Executing Echo Action");
        logger.info("Hello, world!");
        logger.info("[EchoAction] Echo Action executed successfully");
    }
}
