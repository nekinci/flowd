package com.taskengine.app.core.data.om;

import com.taskengine.app.core.data.om.flow.Flow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract sealed class Node implements Element
        permits EndEventNode, ExclusiveGatewayNode, ParallelGatewayNode, ServiceTaskNode, StartEventNode, UserTaskNode {
    protected String id;
    protected String name;
    protected ProcessNode processNode;
    protected NodeType nodeType;
    protected Map<String, String> attributes;

    protected final List<Flow> incoming = new ArrayList<>();
    protected final List<Flow> outgoing = new ArrayList<>();


    public Node(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public void addIncoming(Flow flow) {
        incoming.add(flow);
    }

    public void addOutgoing(Flow flow) {
        outgoing.add(flow);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", processNode=" + processNode +
                ", nodeType=" + nodeType +
                ", attributes=" + attributes +
                '}';
    }

}
