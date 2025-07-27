package com.taskengine.app.infra.repository;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.core.data.repository.ExecutionRepository;
import com.taskengine.app.infra.persistence.PersistentExecution;
import com.taskengine.app.infra.persistence.repository.PersistentExecutionRepository;
import com.taskengine.app.infra.persistence.repository.PersistentProcessRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class ExecutionRepositoryImpl
        extends DomainEntityConverter<Execution, PersistentExecution> implements ExecutionRepository {

    private final PersistentExecutionRepository persistentExecutionRepository;
    private final PersistentProcessRepository persistentProcessRepository;

    public ExecutionRepositoryImpl(PersistentExecutionRepository persistentExecutionRepository,
                                   PersistentProcessRepository persistentProcessRepository) {
        this.persistentExecutionRepository = persistentExecutionRepository;
        this.persistentProcessRepository = persistentProcessRepository;
    }

    @Override
    public Optional<Execution> findById(UUID uuid) {
        return persistentExecutionRepository.findById(uuid)
                .map(persistentExecution -> {
                    Execution execution = new Execution();
                    return toDomain(execution, persistentExecution);
                });
    }

    @Override
    @Transactional
    public Execution save(Execution entity) {
        PersistentExecution persistentExecution = toEntity(entity);
        return toDomain(entity, persistentExecutionRepository.save(persistentExecution));
    }

    @Override
    public void delete(Execution entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(UUID uuid) {
        persistentExecutionRepository.deleteById(uuid);
    }

    @Override
    public boolean existsById(UUID uuid) {
        return persistentExecutionRepository.existsById(uuid);
    }

    @Override
    public PersistentExecution toEntity(Execution domainEntity) {
        PersistentExecution persistentExecution = persistentExecutionRepository.findById(domainEntity.getId())
                .orElse(new PersistentExecution());
        if (persistentExecution.getId() == null) {
            persistentExecution.setId(domainEntity.getId());
        }

        persistentExecution.setInstanceId(domainEntity.getInstanceId());

        if (domainEntity.getParentId() != null) {
            PersistentExecution parentExecution = persistentExecutionRepository.findById(domainEntity.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent execution not found for ID: " + domainEntity.getParentId()));
            persistentExecution.setParentExecution(parentExecution);
        }

        persistentExecution.setPersistentProcess(persistentProcessRepository.findById(domainEntity.getProcessId())
                .orElseThrow());
        persistentExecution.setProcessDefinitionId(domainEntity.getProcessDefinitionId());


        persistentExecution.setCurrentNodeId(domainEntity.getCurrentNodeId());
        persistentExecution.setStartTime(domainEntity.getStartTime());
        persistentExecution.setEndTime(domainEntity.getEndTime());
        persistentExecution.setStatus(domainEntity.getStatus());
       // persistentExecution.setVariables(domainEntity.getVariables());

        return persistentExecution;
    }

    @Override
    public Execution toDomain(Execution execution, PersistentExecution entity) {
        execution.setId(entity.getId());
        execution.setInstanceId(entity.getInstanceId());
        execution.setProcessDefinitionId(entity.getProcessDefinitionId());
        execution.setProcessVersion(entity.getPersistentProcess().getVersion());
        execution.setProcessId(entity.getPersistentProcess().getId());
       // execution.setVariables(entity.getVariables());
        execution.setParentId(entity.getParentExecution() != null ? entity.getParentExecution().getId() : null);
        execution.setCurrentNodeId(entity.getCurrentNodeId());
        execution.setStartTime(entity.getStartTime());
        execution.setEndTime(entity.getEndTime());
        execution.setStatus(entity.getStatus());
        return execution;
    }
}
