package com.taskengine.app.core.data.om;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public non-sealed class UserTaskNode extends Node {

    private String assignee;
    private String group;


    public UserTaskNode(String assignee, String group, String id, ProcessNode processNode, Map<String, String> attributes) {
        super(id, processNode.getName(), processNode, NodeType.USER_TASK, attributes);
        this.assignee = assignee;
        this.group = group;
    }

}
