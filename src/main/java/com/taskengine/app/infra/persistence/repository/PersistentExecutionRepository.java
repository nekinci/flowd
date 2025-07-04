package com.taskengine.app.infra.persistence.repository;

import com.taskengine.app.infra.persistence.PersistentExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersistentExecutionRepository extends JpaRepository<PersistentExecution, UUID> {
}
