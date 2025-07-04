package com.taskengine.app.parser.converter;

import com.taskengine.app.TServiceTask;
import com.taskengine.app.core.Util;
import com.taskengine.app.core.data.om.ServiceTaskNode;
import org.springframework.stereotype.Component;

@Component
public class ServiceTaskNodeConverter implements Converter<TServiceTask, ServiceTaskNode> {
    @Override
    public Class<TServiceTask> getSourceType() {
        return TServiceTask.class;
    }

    @Override
    public ServiceTaskNode convert(Context context, TServiceTask source) {
        return new ServiceTaskNode(source.getId(),
                source.getName(),
                null,
                Util.convertMap(source.getOtherAttributes()));
    }
}
