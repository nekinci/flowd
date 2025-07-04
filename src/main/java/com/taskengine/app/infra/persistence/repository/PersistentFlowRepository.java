package com.taskengine.app.infra.persistence.repository;

import com.taskengine.app.infra.persistence.PersistentFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersistentFlowRepository extends JpaRepository<PersistentFlow, UUID> {
    Optional<PersistentFlow> findByName(String name);
}
