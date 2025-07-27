package com.taskengine.app.application;

import com.taskengine.app.application.action.ClaimTaskAction;
import com.taskengine.app.application.action.TaskCompletedAction;

public interface TaskService {

    void completeTask(TaskCompletedAction action);
    void claimTask(ClaimTaskAction action);
}
