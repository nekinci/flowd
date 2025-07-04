package com.taskengine.app.core.data.om;

import com.taskengine.app.core.data.om.flow.Flow;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public class ProcessOM {

    @Getter
    private String id;

    @Getter
    private String name;
    private final Map<String, Node> nodes;
    private final Map<String, StartEventNode> startNodes;

    private final Map<String, Flow> flows;

    public ProcessOM(String id, String name) {
        this.id = id;
        this.name = name;
        this.nodes = new HashMap<>();
        this.startNodes = new HashMap<>();
        this.flows = new HashMap<>();
    }

    public void addNode(Node node) {
        if (node instanceof StartEventNode) {
            startNodes.put(node.getId(), (StartEventNode) node);
        }
        nodes.put(node.getId(), node);
    }

    public StartEventNode getStartNode() {
        for (Map.Entry<String, StartEventNode> stringStartEventNodeEntry : startNodes.entrySet()) {
            if (stringStartEventNodeEntry.getValue().isDefault()) {
                return stringStartEventNodeEntry.getValue();
            }
        }

        throw new IllegalStateException("No default start node found");
    }

    public Node getNode(String key) {
        return nodes.get(key);
    }

    public void addFlow(Flow flow) {
        this.flows.put(flow.getId(), flow);
        this.nodes.get(flow.getSourceRef().getId()).addOutgoing(flow);
        this.nodes.get(flow.getTargetRef().getId()).addIncoming(flow);

    }

    public Flow getFlow(String key) {
        return flows.get(key);
    }



}
