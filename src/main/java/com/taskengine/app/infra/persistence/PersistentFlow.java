package com.taskengine.app.infra.persistence;

import com.taskengine.app.core.data.entity.FlowKind;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Data
@Entity
@Table(name = "FLOW")
public class PersistentFlow extends BaseEntity {


    @Id
    @Column(name = "ID", length = 36, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "NAME")
    private String name;


    @Lob
    @Column(name = "CONTENT", columnDefinition = "BLOB")
    private byte[] content;

    @Column(name = "FLOW_KIND")
    @Enumerated(EnumType.STRING)
    private FlowKind flowKind;

    @Version
    private Long version;

}
