package com.taskengine.app.core;

import javax.xml.namespace.QName;
import java.util.Map;
import java.util.stream.Collectors;

public final class Util {

    private Util() {
        throw new IllegalStateException("Utility class should not be instantiated");
    }

    public static QName withDefaultNamespace(String localPart) {
        return new QName(FlowdConstant.SCHEMA, localPart, FlowdConstant.PREFIX);
    }

    public static Map<String, String> convertMap(Map<QName, String> attributes) {
        return attributes.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getLocalPart(),
                        Map.Entry::getValue
                ));
    }
}
