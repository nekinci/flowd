package com.taskengine.app.core.expression;

import com.taskengine.app.core.service.engine.Context;
import lombok.Getter;
import lombok.Setter;
import org.graalvm.polyglot.HostAccess;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ExpressionContext implements Context {

    private Map<String, Object> variables = new HashMap<>();

    public ExpressionContext() {}

    public ExpressionContext(Map<String, Object> variables) {
        this.variables = variables;
    }

    @HostAccess.Export
    public Object getVariable(String name) {
        return variables.get(name);
    }

    @HostAccess.Export
    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }




}
