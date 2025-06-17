package com.taskengine.app.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@Entity
public class FlowDefinition {


    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "VERSION")
    private Long version;

    @Lob
    @Column(name = "BPMN_XML", columnDefinition = "BLOB")
    private byte[] bpmnXml;

    @Column(name = "BPMN_VERSION")
    private BpmnVersion bpmnVersion;


    public enum BpmnVersion {
        BPMN_20,
    }


}
