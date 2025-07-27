package com.taskengine.app.core.invoker;

import com.taskengine.app.core.data.om.InvokerType;
import com.taskengine.app.core.service.engine.ExecutionContext;

import java.util.Map;

public interface ServiceTaskInvoker {
    void invoke(ExecutionContext context,
                         Map<String, String> attributes);
    InvokerType type();
}
