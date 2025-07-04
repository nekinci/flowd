package com.taskengine.app.core.data.entity;

import com.taskengine.app.core.data.om.ProcessOM;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
public class Process extends AuditableEntity implements Entity {

    private UUID id;
    private String definitionId;
    private Long version;
    private UUID flowId;
    private ProcessOM processOM;

    public String getKey() {
        return definitionId + "@" + version;
    }


}
