package com.taskengine.app.core.data.om;

import lombok.Getter;

@Getter
public class ExclusiveGatewayNode extends Node {

    private final String defaultFlow;

    public ExclusiveGatewayNode(String id, ProcessOM processOM, String name, String defaultFlow) {
        super(id, name, processOM, NodeType.EXCLUSIVE_GATEWAY);
        this.defaultFlow = defaultFlow;
    }

}
