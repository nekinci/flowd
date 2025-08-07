package com.taskengine.app.infra.repository;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.core.data.repository.ExecutionRepository;
import com.taskengine.app.infra.persistence.PersistentExecution;
import com.taskengine.app.infra.persistence.TypedValue;
import com.taskengine.app.infra.persistence.repository.PersistentExecutionRepository;
import com.taskengine.app.infra.persistence.repository.PersistentProcessRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
            persistentExecution.setParent(parentExecution);
        }

        persistentExecution.setPersistentProcess(persistentProcessRepository.findById(domainEntity.getProcessId())
                .orElseThrow());
        persistentExecution.setProcessDefinitionId(domainEntity.getProcessDefinitionId());

        Map<String, TypedValue> values = domainEntity.getVariables().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new TypedValue(entry.getValue().getClass().getName(), entry.getValue())));

        persistentExecution.setCurrentNodeId(domainEntity.getCurrentNodeId());
        persistentExecution.setStartTime(domainEntity.getStartTime());
        persistentExecution.setEndTime(domainEntity.getEndTime());
        persistentExecution.setStatus(domainEntity.getStatus());
        persistentExecution.setVariables(values);

        return persistentExecution;
    }

    @Override
    public Execution toDomain(Execution execution, PersistentExecution entity) {
        execution.setId(entity.getId());
        execution.setInstanceId(entity.getInstanceId());
        execution.setProcessDefinitionId(entity.getProcessDefinitionId());
        execution.setProcessVersion(entity.getPersistentProcess().getVersion());
        execution.setProcessId(entity.getPersistentProcess().getId());
        Map<String, Object> variables = entity.getVariables().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getValue()));
        execution.setVariables(variables);
        execution.setParentId(entity.getParent() != null ? entity.getParent().getId() : null);
        execution.setCurrentNodeId(entity.getCurrentNodeId());
        execution.setStartTime(entity.getStartTime());
        execution.setEndTime(entity.getEndTime());
        execution.setStatus(entity.getStatus());
        execution.setVersion(entity.getVersion());
        return execution;
    }



    @Override
    public Optional<Execution> findByIdAndVersion(UUID id, Long version) {
        return persistentExecutionRepository.findByIdAndVersion(id, version)
                .map(persistentExecution -> toDomain(new Execution(), persistentExecution));
    }

    @Override
    public Execution saveByVersion(Execution parentExecution, long version) {
        findByIdAndVersion(parentExecution.getId(), version)
                .ifPresentOrElse(
                        existingExecution -> {
                            existingExecution.setVersion(version);
                            save(existingExecution);
                        },
                        () -> {
                            throw new IllegalArgumentException("Execution with ID " + parentExecution.getId() + " and version " + version + " not found.");
                        }
                );
        return findById(parentExecution.getId()).get();
    }

    @Override
    public List<Execution> getActiveExecutionsByParentId(UUID parentId) {
        return persistentExecutionRepository.findByParentIdAndStatusIn(parentId, List.of(Execution.Status.WAITING,
                        Execution.Status.RUNNING,
                        Execution.Status.PLANNED,
                        Execution.Status.WAITING_ACTION))
                .stream()
                .map(persistentExecution -> toDomain(new Execution(), persistentExecution))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Execution> findExecutionsByStatusIn(List<Execution.Status> statusList) {
        return persistentExecutionRepository.findByStatusIn(statusList)
                .stream()
                .map(persistentExecution -> toDomain(new Execution(), persistentExecution))
                .collect(Collectors.toList());
    }
}
