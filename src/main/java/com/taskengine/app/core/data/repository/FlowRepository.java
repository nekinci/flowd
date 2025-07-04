package com.taskengine.app.core.data.repository;


import com.taskengine.app.core.data.entity.Flow;

import java.util.Optional;
import java.util.UUID;

public interface FlowRepository extends Repository<Flow, UUID> {

    Optional<Flow> findByName(String name);
}
