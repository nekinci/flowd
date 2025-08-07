package com.taskengine.app.application.action;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskCompletedAction {
    private UUID taskId;
    private Map<String, Object> variables;

}
