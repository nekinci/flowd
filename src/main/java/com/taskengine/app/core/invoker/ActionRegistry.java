package com.taskengine.app.core.invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ActionRegistry {

    private final Map<String, Action> actionMap = new HashMap<>();


    public void registerAction(String actionName, Action action) {
        if (actionName == null || actionName.isEmpty()) {
            throw new IllegalArgumentException("Action name cannot be null or empty");
        }
        if (action == null) {
            throw new IllegalArgumentException("Action cannot be null");
        }
        actionMap.put(actionName, action);
    }


    public Optional<Action> getAction(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Action name cannot be null or empty");
        }
        return Optional.ofNullable(actionMap.get(name));
    }
}
