package com.taskengine.app.core.service.handler;

import com.taskengine.app.core.data.om.ExclusiveGatewayNode;
import com.taskengine.app.core.expression.EvaluatorFactory;
import com.taskengine.app.core.expression.ExpressionContext;
import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.core.service.engine.ExecutionContext;

public class ExclusiveGatewayNodeHandler implements NodeHandler<ExclusiveGatewayNode> {


    private final EvaluatorFactory evaluatorFactory;

    public ExclusiveGatewayNodeHandler(EvaluatorFactory evaluatorFactory) {
        this.evaluatorFactory = evaluatorFactory;
    }


    @Override
    public void handle(ExclusiveGatewayNode node, ExecutionContext context) {

        System.out.println("[ExclusiveGatewayNode] Handling Exclusive Gateway: " + node.getId());



        String nextNodeId = node.getOutgoing().stream()
                .filter(outgoing -> outgoing.getConditionExpression() != null
                        && (Boolean) evaluatorFactory.createEvaluator(outgoing.getExpressionLanguage()).evaluate(outgoing.getConditionExpression(), new ExpressionContext(context.getVariables())))
                .findFirst()
                .map(outgoing -> outgoing.getTargetRef().getId())
                .orElse(node.getDefaultFlow());

        context.moveTo(nextNodeId);
        System.out.println("[ExclusiveGatewayNode] Moved to next node: " + nextNodeId);

    }

    @Override
    public Class<ExclusiveGatewayNode> getNodeType() {
        return ExclusiveGatewayNode.class;
    }
}
