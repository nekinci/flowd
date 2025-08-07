package com.taskengine.app.core.service;


import com.taskengine.app.core.data.om.*;
import com.taskengine.app.core.service.engine.ExecutionContext;
import com.taskengine.app.core.service.handler.UserTaskNodeHandler;

import java.util.HashMap;
import java.util.Map;

public class HandlerRegistry {


    private final Map<Class<? extends Node>, NodeHandler<? extends Node>> handlers;


    public HandlerRegistry() {
        this.handlers = new HashMap<>();
    }

    public HandlerRegistry(Map<Class<? extends Node>, NodeHandler<? extends Node>> handlers) {
        this.handlers = handlers;
    }


    public <T extends Node> void register(Class<T> handlerClass, NodeHandler<T> handler) {
        this.handlers.put(handlerClass, handler);
    }

    @SuppressWarnings("unchecked")
    public <T extends Node> NodeHandler<T> getHandler(Class<T> handlerClass) {
        return (NodeHandler<T>) this.handlers.get(handlerClass);
    }


    public void dispatch(Node node, ExecutionContext context) {


        if (node instanceof StartEventNode) {
            getHandler(StartEventNode.class)
                    .handle((StartEventNode) node, context);
        } else if (node instanceof EndEventNode) {
            getHandler(EndEventNode.class)
                    .handle((EndEventNode) node, context);
        } else if (node instanceof ServiceTaskNode) {
            getHandler(ServiceTaskNode.class)
                    .handle((ServiceTaskNode) node, context);
        } else if (node instanceof ExclusiveGatewayNode) {
            getHandler(ExclusiveGatewayNode.class)
                    .handle((ExclusiveGatewayNode) node, context);
        } else if (node instanceof UserTaskNode) {
            getHandler(UserTaskNode.class)
                    .handle((UserTaskNode) node, context);
        } else if (node instanceof ParallelGatewayNode) {
            getHandler(ParallelGatewayNode.class)
                    .handle((ParallelGatewayNode) node, context);
        }

    }
}
