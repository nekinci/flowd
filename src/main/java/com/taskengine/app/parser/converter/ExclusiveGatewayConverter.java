package com.taskengine.app.parser.converter;

import com.taskengine.app.TExclusiveGateway;
import com.taskengine.app.TFlowElement;
import com.taskengine.app.TSequenceFlow;
import com.taskengine.app.core.data.om.ExclusiveGatewayNode;

public class ExclusiveGatewayConverter implements Converter<TExclusiveGateway, ExclusiveGatewayNode> {
    @Override
    public Class<TExclusiveGateway> getSourceType() {
        return TExclusiveGateway.class;
    }

    @Override
    public ExclusiveGatewayNode convert(Context context, TExclusiveGateway source) {

        return new ExclusiveGatewayNode(
                source.getId(),
                context.getCurrentProcessNode(),
                source.getName(),
                source.getDefault() != null ? ((TFlowElement)((TSequenceFlow) source.getDefault()).getTargetRef()).getId() : null
        );
    }
}
