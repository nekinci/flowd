package com.taskengine.app.core.invoker;

import com.taskengine.app.core.data.om.InvokerType;
import com.taskengine.app.core.service.engine.EngineException;
import com.taskengine.app.core.service.engine.ExecutionContext;

import java.util.Map;

public class ActionInvoker
        implements ServiceTaskInvoker{

    private final ActionRegistry actionRegistry;

    public ActionInvoker(ActionRegistry actionRegistry) {
        this.actionRegistry = actionRegistry;
    }


    @Override
    public void invoke(ExecutionContext context, Map<String, String> attributes) {
        ActionExecution actionExecution = new ActionExecution(context);
        actionRegistry.getAction(attributes.get("action"))
                .orElseThrow(() -> new EngineException("Action implementation could not be found: " + attributes.get("action")))
                .execute(actionExecution);

    }

    @Override
    public InvokerType type() {
        return InvokerType.ACTION;
    }
}
