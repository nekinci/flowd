package com.taskengine.app.core.service;

import com.taskengine.app.core.data.om.Node;
import com.taskengine.app.core.service.engine.ExecutionContext;

public interface NodeHandler<T extends Node> {
    void handle(T node, ExecutionContext context);

    Class<T> getNodeType();
}
