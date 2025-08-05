package com.taskengine.app.infra.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Convert;

@Convert
public class TypedValueConverter implements jakarta.persistence.AttributeConverter<TypedValue, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TypedValue attribute) {
        try {
            return attribute == null ? null : objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public TypedValue convertToEntityAttribute(String dbData) {

        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, TypedValue.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to convert JSON to TypedValue", e);
        }

    }


}
