package com.taskengine.app.parser.converter;

import com.taskengine.app.TFlowElement;
import com.taskengine.app.core.data.om.Element;

public interface Converter<T extends TFlowElement, R extends Element> {
    Class<T> getSourceType();
    R convert(Context context, T source);
}
