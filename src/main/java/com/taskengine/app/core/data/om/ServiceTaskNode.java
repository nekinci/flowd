package com.taskengine.app.core.data.om;

import lombok.Getter;

import java.util.Map;

@Getter
public non-sealed class ServiceTaskNode extends Node {


    public static final String SERVICE_TASK_PROPERTY = "serviceTask";
    private final InvokerType invokerType;


    public ServiceTaskNode(String id,
                           String name,
                           ProcessNode processNode,
                           Map<String, String> attributes,
                           InvokerType invokerType) {
        super(id, name, processNode, NodeType.SERVICE_TASK, attributes);
        this.invokerType = invokerType;

    }


}
