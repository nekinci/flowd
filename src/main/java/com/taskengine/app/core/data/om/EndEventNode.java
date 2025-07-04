package com.taskengine.app.core.data.om;

public class EndEventNode extends Node {

    public EndEventNode(String id, String name, ProcessOM processOM) {
        super(id, name, processOM, NodeType.END_NODE);
    }


}
