package com.taskengine.app.core.service.engine;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.core.data.om.Node;
import com.taskengine.app.core.data.om.ProcessOM;
import lombok.Data;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Data
public class ExecutionContext {

    private String processDefinitionId;
    private Engine engine;
    private Execution execution;
    private String currentNodeId;

    private UUID parentExecutionId;
    private UUID parentInstanceId;
    private UUID instanceId;
    private UUID executionId;
    private boolean isStarted = false;
    private boolean isCompleted = false;
    private Map<String, Object> variables;


    public static ExecutionContext create(Engine engine, Execution execution, ProcessOM processOM) {
        ExecutionContext context = new ExecutionContext();
        context.setProcessDefinitionId(processOM.getId());
        context.setEngine(engine);
        context.setVariables(execution.getVariables());
        context.setExecution(execution);
        context.setExecutionId(UUID.randomUUID());
        context.setInstanceId(UUID.randomUUID());
        context.setParentExecutionId(null);
        context.setParentInstanceId(null);
        context.setCurrentNodeId(processOM.getStartNode().getId());
        context.setStarted(true);
        return context;
    }

    public Object getVariable(String key) {
        if (variables == null) {
            return null;
        }
        return variables.get(key);
    }

    public void setVariable(String key, Object value) {
        if (variables == null) {
            throw new IllegalStateException("Variables map is not initialized.");
        }
        variables.put(key, value);
    }


    public Optional<Node> getCurrentNode() {

        if (engine == null || processDefinitionId == null) {
            throw new EngineException("Engine or processId is not set in ExecutionContext.");
        }

        Node node = engine.getProcess(processDefinitionId)
                .orElseThrow(() -> new EngineException("Process not found for id: " + processDefinitionId))
                .getProcessOM()
                .getNode(currentNodeId);

        return Optional.ofNullable(node);
    }

    public void moveTo(String nodeId) {
        if (nodeId == null || nodeId.isEmpty()) {
            throw new IllegalArgumentException("Node ID cannot be null or empty");
        }
        this.currentNodeId = nodeId;
    }

}
