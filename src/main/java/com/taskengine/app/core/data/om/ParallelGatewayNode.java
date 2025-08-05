package com.taskengine.app.core.data.om;

import com.taskengine.app.core.data.om.flow.Flow;

import java.util.Map;

public non-sealed class ParallelGatewayNode extends Node {

    public ParallelGatewayNode(String id,
                               ProcessNode processNode,
                               String name,
                               Map<String, String> attributes) {
        super(id, name, processNode, NodeType.PARALLEL_GATEWAY, attributes);
    }


    public boolean isJoining() {
        return incoming.size() > 1;
    }

    public boolean isSplitting() {
        return outgoing.size() > 1;
    }

}
