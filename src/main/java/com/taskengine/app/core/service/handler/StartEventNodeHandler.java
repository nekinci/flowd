package com.taskengine.app.core.service.handler;

import com.taskengine.app.core.data.om.StartEventNode;
import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.core.service.engine.ExecutionContext;

import java.util.logging.Logger;

public class StartEventNodeHandler implements NodeHandler<StartEventNode> {

    private static final Logger logger = Logger.getLogger(StartEventNodeHandler.class.getName());

    @Override
    public void handle(StartEventNode node, ExecutionContext context) {
        logger.info("[StartEventNode] Handling StartEvent: {}" + node.getId());
        String id = node.getOutgoing().get(0).getTargetRef().getId();
        context.moveTo(id);
        logger.info("[StartEventNode] Moved to next node: " + id);
    }

    @Override
    public Class<StartEventNode> getNodeType() {
        return StartEventNode.class;
    }
}
