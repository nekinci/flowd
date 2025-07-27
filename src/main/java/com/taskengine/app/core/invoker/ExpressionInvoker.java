package com.taskengine.app.core.invoker;

import com.taskengine.app.core.data.om.InvokerType;
import com.taskengine.app.core.service.engine.ExecutionContext;

import java.util.Map;

public class ExpressionInvoker
        implements ServiceTaskInvoker {
    @Override
    public void invoke(ExecutionContext context,
                       Map<String, String> attributes) {
        String expression = attributes.get("expression");
        // implement
    }

    @Override
    public InvokerType type() {
        return InvokerType.EXPRESSION;
    }
}
