package com.taskengine.app.core.data.om;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;

@EqualsAndHashCode(callSuper = true)
@Data
public non-sealed class StartEventNode extends Node {


    private boolean isDefault = false;
    public StartEventNode(boolean isDefault) {
        super(NodeType.START_NODE);
        this.isDefault = true;
    }
    public StartEventNode(String id,
                          String name,
                          ProcessNode processNode,
                          boolean isDefault) {
        super(id, name, processNode, NodeType.START_NODE, new HashMap<>());
        this.isDefault = isDefault;
    }

}
