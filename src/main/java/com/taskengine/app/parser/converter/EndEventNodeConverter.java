package com.taskengine.app.parser.converter;

import com.taskengine.app.TEndEvent;
import com.taskengine.app.core.data.om.EndEventNode;
import org.springframework.stereotype.Component;

public class EndEventNodeConverter implements Converter<TEndEvent, EndEventNode> {

    @Override
    public Class<TEndEvent> getSourceType() {
        return TEndEvent.class;
    }

    @Override
    public EndEventNode convert(Context context, TEndEvent source) {

        return new EndEventNode(
                source.getId(),
                source.getName(),
                context.getCurrentProcessNode()
        );
    }


}
