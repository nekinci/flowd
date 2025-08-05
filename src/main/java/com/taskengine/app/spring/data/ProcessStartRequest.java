package com.taskengine.app.spring.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessStartRequest {
    private String processDefinitionId;
    private Map<String, Object> variables;
}
