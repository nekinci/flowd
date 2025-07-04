package com.taskengine.app.core.data.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class Flow extends AuditableEntity implements Entity {
    private UUID id;
    private String name;
    private byte[] content;
    private FlowKind flowKind;
}
