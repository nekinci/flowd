package com.taskengine.app.infra.persistence;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "EXECUTION")
public class PersistentExecution extends BaseEntity{

    @Id
    @Column(name = "ID")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PROCESS_ID", nullable = false)
    private PersistentProcess persistentProcess;

    @Column(name = "PROCESS_DEF_ID", nullable = false)
    private String processDefinitionId;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private com.taskengine.app.core.data.entity.Execution.Status status;

    @Column(name = "INSTANCE_ID")
    private UUID instanceId;

    @Column(name = "CURRENT_NODE_ID")
    private String currentNodeId;

    @ManyToOne
    @JoinColumn(name = "PARENT_EXECUTION_ID", referencedColumnName = "ID")
    private PersistentExecution parent;

    @ElementCollection
    @CollectionTable(name = "EXECUTION_VARIABLES", joinColumns = @JoinColumn(name = "EXECUTION_ID"))
    @MapKeyColumn(name = "VARIABLE_NAME")
    @Column(name = "VARIABLE_VALUE", columnDefinition = "CLOB")
    @Convert(attributeName = "value", converter = TypedValueConverter.class)
    private Map<String, TypedValue> variables = new HashMap<>();

    @OneToMany(mappedBy = "persistentExecution", cascade = CascadeType.ALL)
    private List<PersistentTask> tasks = new ArrayList<>();

    @Version
    private Long version;

}
