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

        switch (node) {
            case StartEventNode startEventNode -> getHandler(StartEventNode.class)
                    .handle(startEventNode, context);
            case EndEventNode endEventNode -> getHandler(EndEventNode.class)
                    .handle(endEventNode, context);
            case ServiceTaskNode serviceTaskNode -> getHandler(ServiceTaskNode.class)
                    .handle(serviceTaskNode, context);
            case ExclusiveGatewayNode exclusiveGatewayNode -> getHandler(ExclusiveGatewayNode.class)
                    .handle(exclusiveGatewayNode, context);
            case UserTaskNode userTaskNode -> getHandler(UserTaskNode.class)
                    .handle(userTaskNode, context);
            // Do not add a default case here, as it would mask errors
        }
    }
}
