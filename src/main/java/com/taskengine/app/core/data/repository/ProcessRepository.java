package com.taskengine.app.core.data.repository;

import com.taskengine.app.core.data.entity.Process;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProcessRepository extends Repository<Process, UUID> {
    Optional<Process> findByDefinitionIdAndVersion(String definitionId, Long version);
    Optional<Process> findLatestVersionByDefinitionId(String definitionId);
    List<Process> findAllVersionsByDefinitionId(String definitionId);
}
