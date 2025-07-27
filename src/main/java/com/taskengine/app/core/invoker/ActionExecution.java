package com.taskengine.app.core.invoker;

import com.taskengine.app.core.service.engine.ExecutionContext;

public class ActionExecution {
    private final ExecutionContext executionContext;

    public ActionExecution(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }


    public Object getVariable(String variableName) {
        return executionContext.getVariable(variableName);
    }

    public void setVariable(String variableName, Object value) {
        executionContext.setVariable(variableName, value);
    }
}
