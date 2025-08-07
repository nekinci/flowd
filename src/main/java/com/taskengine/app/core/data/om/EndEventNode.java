package com.taskengine.app.core.data.om;

import java.util.HashMap;

public class EndEventNode extends Node {
    public EndEventNode(String id, String name, ProcessNode processNode) {
        super(id, name, processNode, NodeType.END_NODE, new HashMap<>());
    }

}
