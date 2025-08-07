package com.taskengine.app.parser.converter;

import com.taskengine.app.TFlowElement;
import com.taskengine.app.TSequenceFlow;
import com.taskengine.app.core.data.om.Element;
import com.taskengine.app.core.data.om.flow.Flow;
import com.taskengine.app.core.data.om.Node;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

public class ConverterService {


    private final List<Converter<?, ?>> converters;

    public ConverterService(List<Converter<?, ?>> converters) {
        this.converters = converters;
    }


    public <T extends TFlowElement, R extends Node> R convert(Context context, T source) {
        Converter<T, R> converter = getConverter(source.getClass());
        return converter.convert(context, source);
    }

    @SuppressWarnings("unchecked")
    public <T extends TSequenceFlow, R extends Flow> R convert(Context context, T source) {
        Converter<T, Element> converter = (Converter<T, Element>) converters.stream()
                .filter(c -> c.getSourceType().equals(source.getClass()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No converter found for type: " + source.getClass().getName()));
        return (R) converter.convert(context, source);
    }


    @SuppressWarnings("unchecked")
    public <T extends TFlowElement, R extends Node> Converter<T, R> getConverter(Class<? extends TFlowElement> sourceType) {
        Assert.notNull(sourceType, "Source type must not be null");
        Converter<?, ?> converter = converters.stream()
                .filter(c -> c.getSourceType().equals(sourceType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No converter found for type: " + sourceType.getName()));

        return (Converter<T, R>) converter;
    }
}
