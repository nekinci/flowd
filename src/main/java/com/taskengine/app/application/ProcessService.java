package com.taskengine.app.application;

import com.taskengine.app.application.action.ProcessStartAction;

public interface ProcessService {

    void startProcess(ProcessStartAction action);
}
