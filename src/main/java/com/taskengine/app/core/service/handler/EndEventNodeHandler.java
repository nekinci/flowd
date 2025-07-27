package com.taskengine.app.core.service.handler;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.core.data.om.EndEventNode;
import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.core.service.engine.ExecutionContext;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Logger;

@Slf4j
public class EndEventNodeHandler implements NodeHandler<EndEventNode> {

    private static final Logger logger = Logger.getLogger(EndEventNodeHandler.class.getName());
    @Override
    public void handle(EndEventNode node, ExecutionContext context) {
        logger.info("[EndEventNode] Handling EndEvent: " + node.getId());
        context.setCompleted(true);
        context.setStatus(Execution.Status.COMPLETED);
        logger.info("[EndEventNode] Process ended at node: " + node.getId());
    }

    @Override
    public Class<EndEventNode> getNodeType() {
        return EndEventNode.class;
    }
}
