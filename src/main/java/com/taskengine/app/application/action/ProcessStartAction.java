package com.taskengine.app.application.action;

import java.util.Map;

public record ProcessStartAction(String processDefinitionId, Map<String, Object> variables) {}