package com.taskengine.app.core.data.om;

import com.taskengine.app.core.data.om.flow.Flow;
import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.core.service.engine.ExecutionContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Node implements Element {
    protected String id;
    protected String name;
    protected ProcessOM processOM;
    protected NodeType nodeType;

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

    public void accept(NodeHandler handler, ExecutionContext context) {
        handler.handle(this, context);
    }

}
