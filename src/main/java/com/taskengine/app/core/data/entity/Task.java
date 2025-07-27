package com.taskengine.app.core.data.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task extends AuditableEntity implements Entity {

    private UUID id;
    private String assignee;
    private String group;
    private UUID executionId;
    private String taskDefinitionId;
    private String taskName;
    private String taskDescription;
    private TaskStatus status;


    public enum TaskStatus {
        CLAIMED,
        UNCLAIMED,
        COMPLETED,
    }
}
