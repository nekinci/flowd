package com.taskengine.app.core.provider;

import com.taskengine.app.core.data.om.ProcessOM;

import java.io.InputStream;
import java.util.List;

public interface Parser {

    List<ProcessOM> parse(InputStream is) throws ParserException;
            ;
}
