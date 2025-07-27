package com.taskengine.app.infra.repository;

import com.taskengine.app.core.data.entity.Task;
import com.taskengine.app.core.data.repository.TaskRepository;
import com.taskengine.app.infra.persistence.PersistentTask;
import com.taskengine.app.infra.persistence.repository.PersistentExecutionRepository;
import com.taskengine.app.infra.persistence.repository.PersistentTaskRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class TaskRepositoryImpl extends DomainEntityConverter<Task, PersistentTask>
        implements TaskRepository {


    private final PersistentExecutionRepository persistentExecutionRepository;
    private final PersistentTaskRepository persistentTaskRepository;

    public TaskRepositoryImpl(PersistentExecutionRepository persistentExecutionRepository, PersistentTaskRepository persistentTaskRepository) {
        this.persistentExecutionRepository = persistentExecutionRepository;
        this.persistentTaskRepository = persistentTaskRepository;
    }

    @Override
    public Optional<Task> findById(UUID uuid) {
        return persistentTaskRepository.findById(uuid)
                .map(persistentTask -> toDomain(new Task(), persistentTask));
    }


    @Override
    @Transactional
    public Task save(Task entity) {
        return toDomain(entity, persistentTaskRepository.save(toEntity(entity)));
    }

    @Override
    public void delete(Task entity) {
        deleteById(entity.getId());
    }

    public void deleteById(UUID uuid) {
        persistentTaskRepository.deleteById(uuid);
    }

    public boolean existsById(UUID uuid) {
        return persistentTaskRepository.existsById(uuid);
    }

    @Override
    public PersistentTask toEntity(Task domainEntity) {
        PersistentTask persistentTask = persistentTaskRepository.findById(domainEntity.getId())
                .orElse(new PersistentTask());

        if (persistentTask.getId() == null) {
            persistentTask.setId(domainEntity.getId());
        }

        persistentTask.setTaskName(domainEntity.getTaskName());
        persistentTask.setTaskDescription(domainEntity.getTaskDescription());
        persistentTask.setTaskDefinitionId(domainEntity.getTaskDefinitionId());
        persistentTask.setAssignee(domainEntity.getAssignee());
        persistentTask.setGroup(domainEntity.getGroup());
        persistentTask.setStatus(domainEntity.getStatus());
        persistentTask.setPersistentExecution(persistentExecutionRepository.findById(domainEntity.getExecutionId()).orElseThrow());

        return persistentTask;
    }

    @Override
    public Task toDomain(Task domainEntity, PersistentTask sourceEntity) {

        domainEntity.setId(sourceEntity.getId());
        domainEntity.setAssignee(sourceEntity.getAssignee());
        domainEntity.setGroup(sourceEntity.getGroup());
        domainEntity.setExecutionId(sourceEntity.getPersistentExecution().getId());
        domainEntity.setTaskDefinitionId(sourceEntity.getTaskDefinitionId());
        domainEntity.setTaskName(sourceEntity.getTaskName());
        domainEntity.setTaskDescription(sourceEntity.getTaskDescription());
        domainEntity.setStatus(sourceEntity.getStatus());
        return domainEntity;
    }
}
