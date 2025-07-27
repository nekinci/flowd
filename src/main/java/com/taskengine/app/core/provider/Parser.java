package com.taskengine.app.core.provider;

import com.taskengine.app.core.data.om.ProcessNode;

import java.io.InputStream;
import java.util.List;

public interface Parser {

    List<ProcessNode> parse(InputStream is) throws ParserException;
            ;
}
