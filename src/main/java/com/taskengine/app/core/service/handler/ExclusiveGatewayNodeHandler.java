package com.taskengine.app.core.service.handler;

import com.taskengine.app.core.data.om.ExclusiveGatewayNode;
import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.core.service.engine.ExecutionContext;

public class ExclusiveGatewayNodeHandler implements NodeHandler<ExclusiveGatewayNode> {
    @Override
    public void handle(ExclusiveGatewayNode node, ExecutionContext context) {

    }

    @Override
    public Class<ExclusiveGatewayNode> getNodeType() {
        return ExclusiveGatewayNode.class;
    }
}
