package com.taskengine.app.infra.persistence;

import com.taskengine.app.core.data.entity.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "TASK")
public class PersistentTask extends BaseEntity {

    @Id
    private UUID id;
    private String taskName;
    private String taskDescription;
    private String taskDefinitionId;
    private String assignee;
    @Column(name = "GROUP_NAME")
    private String group;
    private Task.TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "EXECUTION_ID", nullable = false)
    private PersistentExecution persistentExecution;
}
