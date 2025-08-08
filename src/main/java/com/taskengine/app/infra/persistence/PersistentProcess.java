package com.taskengine.app.infra.persistence;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "PROCESS")
public class PersistentProcess extends BaseEntity {

    @Id
    @Column(name = "ID", length = 36, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")                 // Hibernate 5.6
    private UUID id;
    @Column(name = "DEFINITION_ID")
    private String definitionId;
    @Column(name = "VERSION")
    private Long version;

    @ManyToOne
    @JoinColumn(name = "FLOW_ID", nullable = false)
    private PersistentFlow flow;
}
