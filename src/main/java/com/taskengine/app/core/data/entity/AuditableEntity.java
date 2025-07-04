package com.taskengine.app.core.data.entity;

import java.time.LocalDateTime;

public abstract class AuditableEntity implements Entity {
    
    private String createdBy;
    private String updatedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
