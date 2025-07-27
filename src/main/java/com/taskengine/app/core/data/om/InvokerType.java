package com.taskengine.app.core.data.om;

import com.taskengine.app.parser.converter.ConverterException;

public enum InvokerType {
        ACTION,
        EXPRESSION;

        public static InvokerType fromValue(String value) {
            for (InvokerType type : InvokerType.values()) {
                if (type.name().equalsIgnoreCase(value)) {
                    return type;
                }
            }
            throw new ConverterException("Unknown delegate type: " + value);
        }
    }
