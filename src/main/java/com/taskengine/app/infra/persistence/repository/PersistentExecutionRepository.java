package com.taskengine.app.infra.persistence.repository;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.infra.persistence.PersistentExecution;
import com.taskengine.app.infra.persistence.PersistentProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersistentExecutionRepository
        extends JpaRepository<PersistentExecution, UUID> {

    Optional<PersistentExecution> findByIdAndVersion(UUID id, Long version);

    List<PersistentExecution> findByParentIdAndStatusIn(
            UUID parentId, List<Execution.Status> statuses);

    List<PersistentExecution> findByStatusIn(List<Execution.Status> statuses);
}
