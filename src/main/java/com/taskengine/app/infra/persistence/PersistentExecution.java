package com.taskengine.app.infra.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "EXECUTION")
public class PersistentExecution extends BaseEntity{

    @Id
    @Column(name = "ID")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FLOW_ID", nullable = false)
    private PersistentFlow persistentFlow;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @Column(name = "FLOW")
    @Enumerated(EnumType.STRING)
    private com.taskengine.app.core.data.entity.Execution.Status status;

    @Column(name = "INSTANCE_ID")
    private UUID instanceId;

    @Column(name = "CURRENT_NODE_ID")
    private String currentNodeId;

    @ManyToOne
    @JoinColumn(name = "PARENT_EXECUTION_ID", referencedColumnName = "ID")
    private PersistentExecution parentExecution;

    private UUID processId;
    private String processDefinitonId;

    @ElementCollection
    @CollectionTable(name = "EXECUTION_VARIABLES", joinColumns = @JoinColumn(name = "EXECUTION_ID"))
    @MapKeyColumn(name = "VARIABLE_NAME")
    @Column(name = "VARIABLE_VALUE")
    private Map<String, String> variables = new HashMap<>();


}
