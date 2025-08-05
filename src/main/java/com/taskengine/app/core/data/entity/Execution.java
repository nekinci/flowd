package com.taskengine.app.core.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
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
    private Long childCount = 0L;

    private Map<String, Object> variables;

    private Long version = 0L;

    public void incrementVersion() {
        version++;
    }
    public enum Status {
        RUNNING,
        WAITING_ACTION,
        COMPLETED,
        FAILED,
        CANCELLED,
        WAITING,
        PLANNED,
        TASK_COMPLETED;
    }
}
