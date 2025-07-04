package com.taskengine.app.parser.converter;

import com.taskengine.app.TFlowElement;
import com.taskengine.app.TSequenceFlow;
import com.taskengine.app.core.data.om.Node;
import com.taskengine.app.core.data.om.SequenceFlowNode;
import org.springframework.stereotype.Component;

@Component
public class SequenceFlowConverter implements Converter<TSequenceFlow, SequenceFlowNode> {
    @Override
    public Class<TSequenceFlow> getSourceType() {
        return TSequenceFlow.class;
    }

    @Override
    public SequenceFlowNode convert(Context context, TSequenceFlow source) {
        Node sourceNode = null;
        Node targetNode = null;
        if (source.getSourceRef() != null) {
            TFlowElement sourceRef = (TFlowElement) source.getSourceRef();
            String id = sourceRef.getId();
            sourceNode = context.getCurrentProcessOM().getNode(id);
            if (sourceNode == null) {
                throw new IllegalArgumentException("Source reference not found: " + id);
            }
        }

        if (source.getTargetRef() != null) {
            TFlowElement targetRef = (TFlowElement) source.getTargetRef();
            String id = targetRef.getId();
            targetNode = context.getCurrentProcessOM().getNode(id);
            if (targetNode == null) {
                throw new IllegalArgumentException("Target reference not found: " + id);
            }
        }

        if (sourceNode == null || targetNode == null) {
            throw new IllegalArgumentException("Source or target node is null for sequence flow: " + source.getId());
        }



        return new SequenceFlowNode(source.getId(), sourceNode, targetNode, null);
    }
}
