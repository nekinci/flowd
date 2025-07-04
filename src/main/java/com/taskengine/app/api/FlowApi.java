package com.taskengine.app.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class FlowApi {

    @GetMapping("/api/flow")
    public String getFlow() {
        // This is a placeholder implementation.
        // In a real application, you would return the flow data.
        return "Flow data";
    }
}
