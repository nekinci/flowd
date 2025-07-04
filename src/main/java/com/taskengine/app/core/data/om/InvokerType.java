package com.taskengine.app.core.data.om;

import com.taskengine.app.parser.converter.ConversionException;

public enum InvokerType {
        BEAN,
        EXPRESSION,
        CUSTOM_CLASS;

        public InvokerType fromValue(String value) {
            for (InvokerType type : InvokerType.values()) {
                if (type.name().equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new ConversionException("Unknown delegate type: " + value);
        }
    }
