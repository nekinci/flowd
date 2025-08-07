package com.taskengine.app.infra.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "PROCESS")
public class PersistentProcess extends BaseEntity {

    @Id
    @Column(name = "ID")
    private UUID id;
    @Column(name = "DEFINITION_ID")
    private String definitionId;
    @Column(name = "VERSION")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "FLOW_ID", nullable = false)
    private PersistentFlow flow;
}
