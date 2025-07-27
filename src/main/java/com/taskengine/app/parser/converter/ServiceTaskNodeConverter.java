package com.taskengine.app.parser.converter;

import com.taskengine.app.TServiceTask;
import com.taskengine.app.core.Util;
import com.taskengine.app.core.data.om.InvokerType;
import com.taskengine.app.core.data.om.ServiceTaskNode;
import org.springframework.stereotype.Component;

import java.util.Map;

public class ServiceTaskNodeConverter implements Converter<TServiceTask, ServiceTaskNode> {
    @Override
    public Class<TServiceTask> getSourceType() {
        return TServiceTask.class;
    }

    @Override
    public ServiceTaskNode convert(Context context, TServiceTask source) {
        Map<String, String> attributes = Util.convertMap(source.getOtherAttributes());
        String invokerType = attributes.get("invokerType");

        ServiceTaskNode serviceTaskNode = new ServiceTaskNode(source.getId(),
                source.getName(),
                context.getCurrentProcessNode(),
                attributes,
                InvokerType.fromValue(invokerType)
        );

        return serviceTaskNode;
    }

}
