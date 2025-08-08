package com.taskengine.app.core.data.repository;

import com.taskengine.app.core.data.entity.Execution;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExecutionRepository
        extends Repository<Execution, UUID> {
    Optional<Execution> findByIdAndVersion(UUID id, Long version);

    Execution saveByVersion(Execution parentExecution, long version);

    List<Execution> getActiveExecutionsByParentId(UUID parentId);

    List<Execution> getCompletedExecutionsByParentId(UUID parentId);

    List<Execution> findExecutionsByStatusIn(List<Execution.Status> statusList);
}
