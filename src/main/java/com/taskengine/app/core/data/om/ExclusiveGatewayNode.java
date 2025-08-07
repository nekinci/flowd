package com.taskengine.app.core.data.om;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class ExclusiveGatewayNode extends Node {

    private final String defaultFlow;

    public ExclusiveGatewayNode(String id, ProcessNode processNode, String name, String defaultFlow) {
        super(id, name, processNode, NodeType.EXCLUSIVE_GATEWAY, new HashMap<>());
        this.defaultFlow = defaultFlow;
    }

}
