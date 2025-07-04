package com.taskengine.app.infra.persistence;

import com.taskengine.app.core.data.entity.FlowKind;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "FLOW")
public class PersistentFlow extends BaseEntity {


    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Lob
    @Column(name = "CONTENT", columnDefinition = "BLOB")
    private byte[] content;

    @Column(name = "FLOW_KIND")
    @Enumerated(EnumType.STRING)
    private FlowKind flowKind;




}
