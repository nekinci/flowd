package com.taskengine.app.application;

import com.taskengine.app.application.action.ClaimTaskAction;
import com.taskengine.app.application.action.TaskCompletedAction;
import com.taskengine.app.core.service.engine.Engine;

public class TaskServiceImpl implements TaskService {


    private final Engine engine;

    public TaskServiceImpl(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void completeTask(TaskCompletedAction action) {
        engine.completeTask(action.taskId(), action.variables());
    }

    @Override
    public void claimTask(ClaimTaskAction action) {
        engine.claimTask(action.taskId(), action.username());
    }
}
