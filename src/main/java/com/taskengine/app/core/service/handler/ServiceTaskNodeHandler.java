package com.taskengine.app.core.service.handler;

import com.taskengine.app.core.data.om.InvokerType;
import com.taskengine.app.core.data.om.ServiceTaskNode;
import com.taskengine.app.core.invoker.ServiceTaskInvoker;
import com.taskengine.app.core.invoker.ServiceTaskInvokerRegistry;
import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.core.service.engine.ExecutionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ServiceTaskNodeHandler implements NodeHandler<ServiceTaskNode> {

    private static final Logger logger = Logger.getLogger(ServiceTaskNodeHandler.class.getName());

    private final ServiceTaskInvokerRegistry registry;

    public ServiceTaskNodeHandler(ServiceTaskInvokerRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void handle(ServiceTaskNode node, ExecutionContext context) {
        InvokerType invokerType = node.getInvokerType();
        logger.info("[ServiceTaskNode] Handling Service Task: " + node.getId() + " with type: " + invokerType);

        ServiceTaskInvoker invoker = registry.getInvoker(invokerType.name())
                .orElseThrow(() -> new IllegalArgumentException("No invoker found for type: " + invokerType));

        logger.info("[ServiceTaskNode] Invoking service task with invoker: " + invoker.getClass().getName());

    }

    @Override
    public Class<ServiceTaskNode> getNodeType() {
        return ServiceTaskNode.class;
    }
}
