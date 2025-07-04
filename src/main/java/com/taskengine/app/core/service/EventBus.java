package com.taskengine.app.core.service;

import com.taskengine.app.core.data.event.Event;

public interface EventBus {
    void publish(Event<?> event);

}
