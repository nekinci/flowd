package com.taskengine.app.infra.repository;

import com.taskengine.app.core.data.entity.Entity;
import com.taskengine.app.infra.persistence.BaseEntity;

public abstract class DomainEntityConverter<D extends Entity, E extends BaseEntity> {

    public abstract E toEntity(D domainEntity);
    public abstract D toDomain(E entity);
}
