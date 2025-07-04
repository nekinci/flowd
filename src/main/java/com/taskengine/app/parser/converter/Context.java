package com.taskengine.app.parser.converter;

import com.taskengine.app.core.data.om.ProcessOM;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Context {
    private ProcessOM currentProcessOM;
}
