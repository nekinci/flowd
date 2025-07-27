package com.taskengine.app.core.data.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
public class Execution extends AuditableEntity implements Entity {

    private UUID id;
    private UUID instanceId;
    private UUID parentId; // parent execution ID

    private LocalDateTime startTime;
    private String currentNodeId;
    private LocalDateTime endTime;
    private Status status;

    private UUID processId;
    private String processDefinitionId;
    private Long processVersion;

    private Map<String, Object> variables;



    public enum Status {
        RUNNING,
        WAITING_ACTION,
        COMPLETED,
        FAILED,
        CANCELLED,
        TASK_COMPLETED;
    }
}
