package com.taskengine.app.core.invoker;

import com.taskengine.app.core.data.om.InvokerType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServiceTaskInvokerRegistry {
    private final Map<InvokerType, ServiceTaskInvoker> invokers = new HashMap<>();
    private final static ServiceTaskInvokerRegistry DEFAULT_REGISTRY = new ServiceTaskInvokerRegistry();

    public static ServiceTaskInvokerRegistry getDefaultRegistry() {
        return DEFAULT_REGISTRY;
    }

    public static ServiceTaskInvokerRegistry newRegistry() {
        return new ServiceTaskInvokerRegistry();
    }

    public void registerInvoker(ServiceTaskInvoker invoker) {
        if (invoker == null) {
            throw new IllegalArgumentException("Invoker cannot be null");
        }
        invokers.put(invoker.type(), invoker);
    }

    public Optional<ServiceTaskInvoker> getInvoker(String type) {
        return Optional.ofNullable(invokers.get(type));
    }



}
