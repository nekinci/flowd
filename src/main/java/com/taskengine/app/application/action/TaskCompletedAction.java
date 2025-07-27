package com.taskengine.app.application.action;

import java.util.Map;
import java.util.UUID;

public record TaskCompletedAction(UUID taskId, Map<String, Object> variables) {

    public TaskCompletedAction {
        if (taskId == null) {
            throw new IllegalArgumentException("Process ID and Task ID must not be null");
        }
    }
}
