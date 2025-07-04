package com.taskengine.app.infra.repository;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.core.data.repository.ExecutionRepository;
import com.taskengine.app.infra.persistence.PersistentExecution;
import com.taskengine.app.infra.persistence.repository.PersistentExecutionRepository;
import com.taskengine.app.infra.persistence.repository.PersistentFlowRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class ExecutionRepositoryImpl
        extends DomainEntityConverter<Execution, PersistentExecution> implements ExecutionRepository {

    private final PersistentExecutionRepository persistentExecutionRepository;
    private final PersistentFlowRepository flowRepository;

    public ExecutionRepositoryImpl(PersistentExecutionRepository persistentExecutionRepository,
                                   PersistentFlowRepository flowRepository) {
        this.persistentExecutionRepository = persistentExecutionRepository;
        this.flowRepository = flowRepository;
    }

    @Override
    public Optional<Execution> findById(UUID uuid) {
        return persistentExecutionRepository.findById(uuid)
                .map(this::toDomain);
    }

    @Override
    public Execution save(Execution entity) {
        PersistentExecution persistentExecution = toEntity(entity);
        return toDomain(persistentExecutionRepository.save(persistentExecution));
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

        persistentExecution.setProcessId(domainEntity.getProcessId());
        persistentExecution.setProcessDefinitonId(domainEntity.getProcessDefinitionId());

        persistentExecution.setCurrentNodeId(domainEntity.getCurrentNodeId());
        persistentExecution.setStartTime(domainEntity.getStartTime());
        persistentExecution.setEndTime(domainEntity.getEndTime());
        persistentExecution.setStatus(domainEntity.getStatus());
       // persistentExecution.setVariables(domainEntity.getVariables());

        return persistentExecution;
    }

    @Override
    public Execution toDomain(PersistentExecution entity) {
        Execution execution = new Execution();
        execution.setId(entity.getId());
        execution.setInstanceId(entity.getInstanceId());
        execution.setProcessDefinitionId(entity.getProcessDefinitonId());
        execution.setProcessId(entity.getProcessId());
       // execution.setVariables(entity.getVariables());
        execution.setParentId(entity.getParentExecution() != null ? entity.getParentExecution().getId() : null);
        execution.setCurrentNodeId(entity.getCurrentNodeId());
        execution.setStartTime(entity.getStartTime());
        execution.setEndTime(entity.getEndTime());
        execution.setStatus(entity.getStatus());
        return execution;
    }
}
