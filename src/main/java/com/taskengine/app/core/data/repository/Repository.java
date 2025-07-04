package com.taskengine.app.core.data.repository;

import com.taskengine.app.core.data.entity.Entity;

import java.util.Optional;

public interface Repository<T extends Entity, ID> {

    Optional<T> findById(ID id);

    T save(T entity);

    void delete(T entity);

    void deleteById(ID id);

    boolean existsById(ID id);

}
