package com.taskengine.app.core.data.om;

import lombok.Getter;

import java.util.Map;

@Getter
public class ServiceTaskNode extends Node {


    public static final String SERVICE_TASK_PROPERTY = "serviceTask";

    private InvokerType invokerType;

    public ServiceTaskNode(String id, String name, ProcessOM processOM, Map<String, String> attributes) {
        super(id, name, processOM, NodeType.SERVICE_TASK);
        setInvokerType(attributes);

    }

    private void setInvokerType(Map<String, String> attributes) {
        String serviceTypeValue = attributes.getOrDefault(SERVICE_TASK_PROPERTY, InvokerType.EXPRESSION.name());
        this.invokerType = InvokerType.valueOf(serviceTypeValue.toUpperCase());
    }



}
