package com.taskengine.app.parser.converter;

import com.taskengine.app.TExclusiveGateway;
import com.taskengine.app.core.data.om.ExclusiveGatewayNode;
import org.springframework.stereotype.Component;

@Component
public class ExclusiveGatewayConverter implements Converter<TExclusiveGateway, ExclusiveGatewayNode> {
    @Override
    public Class<TExclusiveGateway> getSourceType() {
        return TExclusiveGateway.class;
    }

    @Override
    public ExclusiveGatewayNode convert(Context context, TExclusiveGateway source) {
        return new ExclusiveGatewayNode(
                source.getId(),
                context.getCurrentProcessOM(),
                source.getName(),
                null
        );
    }
}
