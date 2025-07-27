package com.taskengine.app.core.service.engine;

import com.taskengine.app.core.data.entity.*;
import com.taskengine.app.core.data.entity.Process;
import com.taskengine.app.core.data.om.Node;
import com.taskengine.app.core.data.om.ProcessNode;
import com.taskengine.app.core.data.repository.ExecutionRepository;
import com.taskengine.app.core.data.repository.FlowRepository;
import com.taskengine.app.core.data.repository.ProcessRepository;
import com.taskengine.app.core.data.repository.TaskRepository;
import com.taskengine.app.core.provider.Parser;
import com.taskengine.app.core.provider.ParserException;
import com.taskengine.app.core.service.HandlerRegistry;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @author nekinci
 * <p>
 * <p>
 * This class represents the core engine of the task management system.
 */
public class Engine {


    private final Map<String, Process> processes = new HashMap<>();
    private final Parser parser;
    private final ExecutionRepository executionRepository;
    private final FlowRepository flowRepository;
    private final ProcessRepository processRepository;
    private final Map<String, Process> lastVersionProcesses = new HashMap<>();
    private final TaskRepository taskRepository;
    private Status status;
    private final HandlerRegistry handlerRegistry;

    public Engine(Parser parser,
                  ExecutionRepository executionRepository,
                  FlowRepository flowRepository,
                  ProcessRepository processRepository,
                  TaskRepository taskRepository,
                  HandlerRegistry handlerRegistry) {
        this.parser = parser;
        this.executionRepository = executionRepository;
        this.flowRepository = flowRepository;
        this.processRepository = processRepository;
        this.taskRepository = taskRepository;
        this.handlerRegistry = handlerRegistry;
    }

    public void start() {
        this.status = Status.RUNNING;
    }

    public void stop() {
        this.status = Status.STOPPED;
    }

    private void checkIsRunning() {
        if (!isRunning()) {
            throw new IllegalStateException("Engine is not running");
        }
    }

    public boolean isRunning() {
        return status == Status.RUNNING;
    }

    public void uploadFlow(byte[] buf) throws ParserException {
        uploadFlow(null, buf);
    }

    public void uploadFlow(String name, byte[] buf) {

        checkIsRunning();
        List<ProcessNode> processNodeList = parser.parse(new ByteArrayInputStream(buf));
        Flow flow = saveFlow(name, buf);

        for (ProcessNode processNode : processNodeList) {
            Process process = saveProcess(processNode, flow);
            process.setProcessNode(processNode);
            processes.put(process.getKey(), process);
        }


    }

    private Process saveProcess(ProcessNode processNode, Flow flow) {
        Process process = processRepository.findLatestVersionByDefinitionId(processNode.getId())
                .orElse(new Process(UUID.randomUUID(), processNode.getId(), 0L, flow.getId(), processNode));
        process.setId(UUID.randomUUID());
        process.setVersion(Long.valueOf(process.getVersion() + 1));
        processRepository.save(process);
        processes.put(process.getKey(), process);
        return process;
    }

    private Flow saveFlow(String name, byte[] buf) {
        Flow flow = new Flow();
        flow.setId(UUID.randomUUID());
        flow.setName(name != null ? name : "Flow-" + UUID.randomUUID());
        flow.setFlowKind(FlowKind.BPMN_20);
        flow.setContent(buf);
        this.flowRepository.save(flow);
        return flow;
    }

    public void startProcess(String processDefinitionId, Map<String, Object> variables) {
        checkIsRunning();
        Process process = getProcess(processDefinitionId, -1L)
                .orElseThrow(() -> new IllegalArgumentException("Process with definition ID " + processDefinitionId + " does not exist"));

        ProcessNode processNode = process.getProcessNode();
        Execution execution = getExecution(variables, process, processNode);
        executionRepository.save(execution);
        ExecutionContext context = ExecutionContext.create(this, execution, processNode);
        runSync(context);
    }

    public void claimTask(UUID taskId, String username) {
        checkIsRunning();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " does not exist"));

        if (task.getAssignee() != null) {
            throw new IllegalStateException("Task is already claimed by " + task.getAssignee());
        }

        task.setAssignee(username);
        task.setStatus(Task.TaskStatus.CLAIMED);
        taskRepository.save(task);
    }

    public void completeTask(UUID taskId, Map<String, Object> variables) {
        checkIsRunning();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " does not exist"));

        UUID executionId = task.getExecutionId();
        Execution execution = executionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Execution with ID " + executionId + " does not exist"));

        Process process = getProcess(execution.getProcessDefinitionId(), execution.getProcessVersion())
                .orElseThrow(() -> new IllegalArgumentException("Process with definition ID " + execution.getProcessDefinitionId() + " does not exist"));

        ProcessNode processNode = process.getProcessNode();
        ExecutionContext context = ExecutionContext.create(this, execution, processNode);
        context.setCurrentNodeId(task.getTaskDefinitionId());
        context.setTaskId(taskId);
        context.setTaskVariables(variables);
        context.setStatus(Execution.Status.TASK_COMPLETED);
        runStep(context);
    }


    private Execution getExecution(Map<String, Object> variables, Process process, ProcessNode processNode) {
        Execution execution = new Execution();
        execution.setId(UUID.randomUUID());
        execution.setInstanceId(UUID.randomUUID());
        execution.setProcessDefinitionId(process.getDefinitionId());
        execution.setProcessId(process.getId());
        execution.setStartTime(LocalDateTime.now());
        execution.setStatus(Execution.Status.RUNNING);
        execution.setCurrentNodeId(processNode.getStartNode().getId());
        execution.setVariables(variables);
        return execution;
    }


    private void runSync(ExecutionContext context) {
        if (!context.isStarted()) {
            throw new IllegalStateException("Execution context is not started");
        }

        if (context.isCompleted()) {
            throw new IllegalStateException("Execution context is already completed");
        }

        while (context.runnable()) {
            runStep(context);
        }

        persistContext(context);
    }


    private void runStep(ExecutionContext context) {
        if (!context.isStarted()) {
            throw new IllegalStateException("Execution context is not started");
        }

        if (context.isCompleted()) {
            throw new IllegalStateException("Execution context is already completed");
        }

        Node currentNode = context.getCurrentNode()
                .orElseThrow(() -> new IllegalStateException("Current node is not set in execution context"));

        handlerRegistry.dispatch(currentNode, context);
        persistContext(context);
    }

    private void persistContext(ExecutionContext context) {
        Execution execution = context.getExecution();
        executionRepository.save(execution);
    }


    public Optional<Process> getProcess(String processDefinitionId, Long version) {

        Process process = processes.get(processDefinitionId + "@" + version);
        if (process != null) {
            return Optional.of(process);
        }

        process = processRepository.findLatestVersionByDefinitionId(processDefinitionId)
                .orElseThrow(() -> new EngineException("Process with definition ID " + processDefinitionId + " does not exist"));

        Process finalProcess = process;
        Flow flow = flowRepository.findById(process.getFlowId())
                .orElseThrow(() -> new EngineException("Flow with ID " + finalProcess.getFlowId() + " does not exist"));

        InputStream is = new ByteArrayInputStream(flow.getContent());
        ProcessNode processNode = parser.parse(is)
                .stream()
                .filter(p -> p.getId().equals(processDefinitionId))
                .findFirst()
                .orElseThrow(() -> new EngineException("ProcessOM with ID " + processDefinitionId + " does not exist"));
        process.setProcessNode(processNode);
        processes.put(process.getKey(), process);
        lastVersionProcesses.put(process.getDefinitionId(), process);
        return Optional.of(process);

    }

    public void startProcess(String processDefinitionId) {
        startProcess(processDefinitionId, new HashMap<>());
    }

}
