package com.taskengine.app.core.data.event;

import java.util.Map;

public interface Event<T> {
    String getType();
    T getPayload();
    Map<String, Object> getMetadata();
    Long getTimestamp();
    String deserializationType();
}
