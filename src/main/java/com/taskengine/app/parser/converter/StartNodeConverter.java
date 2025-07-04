package com.taskengine.app.parser.converter;

import com.taskengine.app.TStartEvent;
import com.taskengine.app.core.data.om.StartEventNode;
import org.springframework.stereotype.Component;

@Component
public class StartNodeConverter implements Converter<TStartEvent, StartEventNode> {

    @Override
    public Class<TStartEvent> getSourceType() {
        return TStartEvent.class;
    }

    @Override
    public StartEventNode convert(Context context, TStartEvent source) {
        StartEventNode startEventNode = new StartEventNode(true); // TODO
        startEventNode.setId(source.getId());
        startEventNode.setName(source.getName());
        return startEventNode;
    }
}
