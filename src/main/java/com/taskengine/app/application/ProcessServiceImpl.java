package com.taskengine.app.application;

import com.taskengine.app.application.action.ProcessStartAction;
import com.taskengine.app.core.service.engine.Engine;

public class ProcessServiceImpl implements ProcessService{

    private final Engine engine;

    public ProcessServiceImpl(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void startProcess(ProcessStartAction action) {
     engine.startProcess(action.getProcessDefinitionId(), action.getVariables());
    }
}
