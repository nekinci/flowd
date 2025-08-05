package com.taskengine.app.core.data.om.flow;

import com.taskengine.app.core.data.om.Element;
import com.taskengine.app.core.data.om.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Flow implements Element {
    protected String id;
    private Node sourceRef;
    private Node targetRef;
    private String conditionExpression;
    private String expressionLanguage;
}
