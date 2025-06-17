package com.taskengine.app.core.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.UUID;

public class Execution {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "FLOW_ID", nullable = false)
    private FlowDefinition flowDefinition;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @Column(name = "FLOW")
    @Enumerated(EnumType.STRING)
    private Status status;


    public enum Status {
        RUNNING,
        COMPLETED,
        FAILED,
        CANCELLED;
    }
}
