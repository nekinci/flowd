package com.taskengine.app.infra.persistence;

import com.taskengine.app.core.data.entity.Task;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "TASK")
public class PersistentTask extends BaseEntity {

    @Id
    @Column(name = "ID", length = 36, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String taskName;
    private String taskDescription;
    private String taskDefinitionId;

    private String assignee;
    @Column(name = "GROUP_NAME")
    private String group;
    @Column(name = "TASK_STATUS")
    @Enumerated(EnumType.STRING)
    private Task.TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "EXECUTION_ID", nullable = false)
    private PersistentExecution persistentExecution;
}
