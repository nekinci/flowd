package com.taskengine.app.infra.repository;

import com.taskengine.app.core.data.entity.Process;
import com.taskengine.app.core.data.repository.ProcessRepository;
import com.taskengine.app.infra.persistence.PersistentProcess;
import com.taskengine.app.infra.persistence.repository.PersistentFlowRepository;
import com.taskengine.app.infra.persistence.repository.PersistentProcessRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProcessRepositoryImpl extends DomainEntityConverter<Process, PersistentProcess>
        implements ProcessRepository {

    private final PersistentProcessRepository persistentProcessRepository;
    private final PersistentFlowRepository flowRepository;

    public ProcessRepositoryImpl(PersistentProcessRepository persistentProcessRepository, PersistentFlowRepository flowRepository) {
        this.persistentProcessRepository = persistentProcessRepository;
        this.flowRepository = flowRepository;
    }

    @Override
    public Optional<Process> findByDefinitionIdAndVersion(String definitionId, Long version) {
        return persistentProcessRepository
                .findByDefinitionIdAndVersion(definitionId, version)
                .map(this::toDomain);
    }

    @Override
    public Optional<Process> findLatestVersionByDefinitionId(String definitionId) {
        return persistentProcessRepository.findLatestVersionByDefinitionId(definitionId)
                .map(this::toDomain);
    }

    @Override
    public List<Process> findAllVersionsByDefinitionId(String definitionId) {
        return persistentProcessRepository
                .findByDefinitionId(definitionId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Process> findById(UUID uuid) {
        return persistentProcessRepository.findById(uuid)
                .map(this::toDomain);
    }

    @Override
    public Process save(Process entity) {
        return toDomain(persistentProcessRepository.save(toEntity(entity)));
    }

    @Override
    public void delete(Process entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(UUID uuid) {
        persistentProcessRepository.deleteById(uuid);
    }

    @Override
    public boolean existsById(UUID uuid) {
        return persistentProcessRepository.existsById(uuid);
    }

    @Override
    public PersistentProcess toEntity(Process domainEntity) {
        PersistentProcess persistentProcess = persistentProcessRepository.findById(domainEntity.getId())
                .orElse(new PersistentProcess());
        if (persistentProcess.getId() == null) {
            persistentProcess.setId(domainEntity.getId());
        }

        persistentProcess.setDefinitionId(domainEntity.getDefinitionId());
        persistentProcess.setVersion(domainEntity.getVersion());

        persistentProcess.setFlow(flowRepository.findById(domainEntity.getFlowId()).orElseThrow(
                () -> new IllegalArgumentException("Flow not found for ID: " + domainEntity.getFlowId())));
        return persistentProcess;
    }

    @Override
    public Process toDomain(PersistentProcess entity) {
        return new Process(
                entity.getId(),
                entity.getDefinitionId(),
                entity.getVersion(),
                entity.getFlow().getId(),
                null
        );
    }
}
