package com.taskengine.app.core;

import org.w3c.dom.Element;

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

    public static void assertNamespace(Element e) {
        if (!FlowdConstant.SCHEMA.equals(e.getNamespaceURI())) {
            throw new IllegalArgumentException("Element " + e.getLocalName() + " does not belong to the Flowd namespace: " + FlowdConstant.SCHEMA);
        }
    }
}
