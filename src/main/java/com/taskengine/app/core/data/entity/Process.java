package com.taskengine.app.core.data.entity;

import com.taskengine.app.core.data.om.ProcessNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Process extends AuditableEntity implements Entity {

    private UUID id;
    private String definitionId;
    private Long version;
    private UUID flowId;
    private ProcessNode processNode;

    public String getKey() {
        return definitionId + "@" + version;
    }


}
