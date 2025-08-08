package com.taskengine.app.infra.persistence.repository;

import com.taskengine.app.infra.persistence.PersistentProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersistentProcessRepository
        extends JpaRepository<PersistentProcess, UUID> {

    Optional<PersistentProcess> findByDefinitionIdAndVersion(
            String definitionId, Long version);

    @Query("SELECT p FROM PersistentProcess p " +
           "WHERE p.definitionId = ?1 " +
           "ORDER BY p.version DESC")
    List<PersistentProcess> findLatestVersionByDefinitionIdHelper(String definitionId);

    default Optional<PersistentProcess> findLatestVersionByDefinitionId(String definitionId) {
        List<PersistentProcess> processes = findLatestVersionByDefinitionIdHelper(definitionId);
        return processes.isEmpty() ? Optional.empty() : Optional.of(processes.get(0));
    }

    List<PersistentProcess> findByDefinitionId(String definitionId);


}
