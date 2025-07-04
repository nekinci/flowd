package com.taskengine.app.core.data.om;

import com.taskengine.app.core.data.om.flow.Flow;

public class SequenceFlowNode extends Flow {

    public SequenceFlowNode(String id, Node sourceRef, Node targetRef, String conditionExpression) {
        super(id, sourceRef, targetRef, conditionExpression);
    }
}
