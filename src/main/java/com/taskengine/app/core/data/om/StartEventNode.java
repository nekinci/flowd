package com.taskengine.app.core.data.om;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StartEventNode extends Node {


    private boolean isDefault = false;
    public StartEventNode(boolean isDefault) {
        super(NodeType.START_NODE);
        this.isDefault = true;
    }
    public StartEventNode(String id, String name, ProcessOM processOM, boolean isDefault) {
        super(id, name, processOM, NodeType.START_NODE);
        this.isDefault = isDefault;
    }

}
