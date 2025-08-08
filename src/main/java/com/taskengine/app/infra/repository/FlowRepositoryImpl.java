package com.taskengine.app.infra.repository;

import com.taskengine.app.core.data.entity.Flow;
import com.taskengine.app.core.data.entity.Process;
import com.taskengine.app.core.data.repository.FlowRepository;
import com.taskengine.app.infra.persistence.PersistentFlow;
import com.taskengine.app.infra.persistence.repository.PersistentFlowRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
public class FlowRepositoryImpl
        extends DomainEntityConverter<Flow, PersistentFlow>
        implements FlowRepository {
    private final PersistentFlowRepository persistentFlowRepository;

    public FlowRepositoryImpl(PersistentFlowRepository persistentFlowRepository) {
        this.persistentFlowRepository = persistentFlowRepository;
    }

    @Override
    public Optional<Flow> findById(UUID uuid) {
        return persistentFlowRepository.findById(uuid)
                .map(persistentFlow -> toDomain(new Flow(), persistentFlow));
    }

    @Override
    public Flow save(Flow entity) {
        PersistentFlow persistentFlow = persistentFlowRepository.save(toEntity(entity));
        return toDomain(entity, persistentFlow);
    }

    @Override
    public void delete(Flow entity) {
        deleteById(entity.getId());
    }

    @Override
    public void deleteById(UUID uuid) {
        persistentFlowRepository.deleteById(uuid);
    }

    @Override
    public boolean existsById(UUID uuid) {
        return persistentFlowRepository.existsById(uuid);
    }

    @Override
    public PersistentFlow toEntity(Flow domainEntity) {
        PersistentFlow persistentFlow = persistentFlowRepository.findById(domainEntity.getId())
                .orElse(new PersistentFlow());
        if (persistentFlow.getId() == null) {
            persistentFlow.setId(domainEntity.getId());
        }
        persistentFlow.setName(domainEntity.getName());
        persistentFlow.setContent(domainEntity.getContent());
        persistentFlow.setFlowKind(domainEntity.getFlowKind());
        return persistentFlow;
    }

    @Override
    public Flow toDomain(Flow flow, PersistentFlow entity) {
        flow.setId(entity.getId());
        flow.setName(entity.getName());
        flow.setContent(entity.getContent());
        flow.setFlowKind(entity.getFlowKind());
        return flow;
    }

    @Override
    public Optional<Flow> findByName(String name) {
        return persistentFlowRepository.findByName(name)
                .map(persistentFlow -> toDomain(new Flow(), persistentFlow));
    }
}
