package com.taskengine.app.application.action;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProcessStartAction {
    private String processDefinitionId;
    private Map<String, Object> variables;
}