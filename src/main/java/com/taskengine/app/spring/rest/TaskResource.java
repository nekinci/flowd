package com.taskengine.app.spring.rest;

import com.taskengine.app.application.TaskService;
import com.taskengine.app.application.action.ClaimTaskAction;
import com.taskengine.app.application.action.TaskCompletedAction;
import com.taskengine.app.spring.data.TaskCompleteRequest;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskResource {

    private final TaskService taskService;

    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{taskId}/complete")
    public void completeTask(@PathVariable("taskId") UUID taskId, @RequestBody TaskCompleteRequest request) {
        taskService.completeTask(new TaskCompletedAction(taskId, request.getVariables()));
    }

    @PostMapping("/{taskId}/claim")
    public void claimTask(@PathVariable("taskId") UUID taskId, @RequestParam String username) {
        taskService.claimTask(new ClaimTaskAction(taskId, username));
    }
}
