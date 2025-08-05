package com.taskengine.app.spring.rest;

import com.taskengine.app.application.ProcessService;
import com.taskengine.app.application.action.ProcessStartAction;
import com.taskengine.app.spring.data.ProcessStartRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/processes")
public class ProcessResource {

    private final ProcessService processService;

    public ProcessResource(ProcessService processService) {
        this.processService = processService;
    }

    @PostMapping("/start")
    @Async
    public void startProcess(@RequestBody ProcessStartRequest request) {
        processService.startProcess(new ProcessStartAction(request.getProcessDefinitionId(), request.getVariables()));
    }
}
