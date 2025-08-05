package com.taskengine.app.parser.converter;

import com.taskengine.app.TParallelGateway;
import com.taskengine.app.core.Util;
import com.taskengine.app.core.data.om.ParallelGatewayNode;

public class ParallelGatewayConverter implements Converter<TParallelGateway, ParallelGatewayNode>{
    @Override
    public Class<TParallelGateway> getSourceType() {
        return TParallelGateway.class;
    }

    @Override
    public ParallelGatewayNode convert(Context context, TParallelGateway source) {
        return new ParallelGatewayNode(
                source.getId(),
                context.getCurrentProcessNode(),
                source.getName(),
                Util.convertMap(source.getOtherAttributes())
        );
    }
}
