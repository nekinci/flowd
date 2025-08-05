package com.taskengine.app.core.service.engine;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.core.data.om.Node;
import com.taskengine.app.core.data.om.ProcessNode;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
public class ExecutionContext implements Context {

    private String processDefinitionId;
    private Long processVersion;
    private Engine engine;
    private Execution.Status status;
    private Execution execution;
    private String currentNodeId;

    private UUID parentExecutionId;
    private UUID parentInstanceId;
    private UUID instanceId;
    private UUID taskId;
    private UUID executionId;
    private boolean isStarted = false;
    private boolean isCompleted = false;
    private Map<String, Object> variables;

    private Map<String, Object> taskVariables;


    public Execution getExecution() {
        if (execution == null) {
            throw new IllegalStateException("Execution is not set in ExecutionContext.");
        }

        execution.setStatus(getStatus());
        execution.setCurrentNodeId(getCurrentNodeId());
        execution.setProcessVersion(getProcessVersion());
        execution.setVariables(this.getVariables());
        return execution;
    }

    public Execution createChildExecution(String outgoingId) {
        Execution childExecution = new Execution();
        childExecution.setId(UUID.randomUUID());
        childExecution.setInstanceId(getInstanceId());
        childExecution.setParentId(getExecutionId());
        childExecution.setProcessDefinitionId(getProcessDefinitionId());
        childExecution.setProcessId(getExecution().getProcessId());
        childExecution.setProcessVersion(getProcessVersion());
        childExecution.setStartTime(LocalDateTime.now());
        childExecution.setStatus(Execution.Status.PLANNED);
        childExecution.setCurrentNodeId(outgoingId);
        childExecution.setVariables(getVariables());
        return childExecution;
    }

    public static ExecutionContext create(Engine engine, Execution execution, ProcessNode processNode) {
        ExecutionContext context = new ExecutionContext();
        context.setProcessDefinitionId(processNode.getId());
        context.setEngine(engine);
        context.setVariables(execution.getVariables());
        context.setExecution(execution);
        context.setExecutionId(execution.getId());
        context.setInstanceId(UUID.randomUUID());
        context.setParentExecutionId(execution.getParentId());
        context.setParentInstanceId(null);
        context.setCurrentNodeId(execution.getCurrentNodeId() != null ? execution.getCurrentNodeId() : processNode.getStartNode().getId());
        context.setProcessVersion(execution.getProcessVersion());
        context.setStatus(execution.getStatus());
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

        if (getEngine() == null || getProcessDefinitionId() == null) {
            throw new EngineException("Engine or processId is not set in ExecutionContext.");
        }

        Node node = engine.getProcess(getProcessDefinitionId(), getProcessVersion())
                .orElseThrow(() -> new EngineException("Process not found for id: " + processDefinitionId))
                .getProcessNode()
                .getNode(currentNodeId);

        return Optional.ofNullable(node);
    }


    public void setCompleted() {
        if (execution == null) {
            throw new IllegalStateException("Execution is not set in ExecutionContext.");
        }
        this.isCompleted = true;
        this.status = Execution.Status.COMPLETED;
        execution.setStatus(Execution.Status.COMPLETED);
        execution.setEndTime(LocalDateTime.now());
    }

    public void moveTo(String nodeId) {
        if (nodeId == null || nodeId.isEmpty()) {
            throw new IllegalArgumentException("Node ID cannot be null or empty");
        }
        this.currentNodeId = nodeId;
    }

    public boolean runnable() {
        return !isCompleted() && isStarted() && getStatus() == Execution.Status.RUNNING;
    }
}
