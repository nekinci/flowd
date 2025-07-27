package com.taskengine.app.infra.persistence.repository;

import com.taskengine.app.infra.persistence.PersistentTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersistentTaskRepository extends JpaRepository<PersistentTask, UUID> {
}
