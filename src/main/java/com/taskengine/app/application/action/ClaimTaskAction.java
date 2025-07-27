package com.taskengine.app.application.action;

import java.util.UUID;

public record ClaimTaskAction(UUID taskId, String username) {

    public ClaimTaskAction {
        if (taskId == null) {
            throw new IllegalArgumentException("Task ID must not be null");
        }
    }
}
