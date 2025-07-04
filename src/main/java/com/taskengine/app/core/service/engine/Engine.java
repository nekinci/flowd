package com.taskengine.app.core.service.engine;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.core.data.entity.Flow;
import com.taskengine.app.core.data.entity.FlowKind;
import com.taskengine.app.core.data.entity.Process;
import com.taskengine.app.core.data.om.ProcessOM;
import com.taskengine.app.core.data.repository.ExecutionRepository;
import com.taskengine.app.core.data.repository.FlowRepository;
import com.taskengine.app.core.data.repository.ProcessRepository;
import com.taskengine.app.core.provider.Parser;
import com.taskengine.app.core.provider.ParserException;
import com.taskengine.app.core.service.EventBus;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.*;


/**
 *
 * @author nekinci
 *
 *
 * This class represents the core engine of the task management system.
 *
 */
public class Engine {


    private final Map<String, Process> processes = new HashMap<>();
    private final Parser parser;
    private final ExecutionRepository executionRepository;
    private final FlowRepository flowRepository;
    private final ProcessRepository processRepository;
    private Status status;

    public Engine(Parser parser,
                  ExecutionRepository executionRepository,
                  FlowRepository flowRepository,
                  ProcessRepository processRepository) {
        this.parser = parser;
        this.executionRepository = executionRepository;
        this.flowRepository = flowRepository;
        this.processRepository = processRepository;
    }

    public void start() {
        this.status = Status.RUNNING;
    }

    public void stop() {
        this.status = Status.STOPPED;
    }

    public boolean isRunning() {
        return status == Status.RUNNING;
    }

    public void uploadFlow(byte[] buf) throws ParserException {
        uploadFlow(null, buf);
    }
    public void uploadFlow(String name, byte[] buf)  {

        if (!isRunning()) {
            throw new EngineException("Engine is not running");
        }

        List<ProcessOM> processOMList = parser.parse(new ByteArrayInputStream(buf));
        Flow flow = saveFlow(name, buf);

        for (ProcessOM processOM: processOMList) {
            Process process = saveProcess(processOM, flow);
            process.setProcessOM(processOM);
            processes.put(process.getKey(), process);
        }


    }

    private Process saveProcess(ProcessOM processOM, Flow flow) {
        Process process = processRepository.findLatestVersionByDefinitionId(processOM.getId())
                .orElse(new Process(UUID.randomUUID(), processOM.getId(), 0L, flow.getId(), processOM));
        process.setId(UUID.randomUUID());
        process.setVersion(process.getVersion() + 1);
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

        if (!isRunning()) {
            throw new IllegalStateException("Engine is not running");
        }

        Process process = getProcess(processDefinitionId)
                .orElseThrow(() -> new IllegalArgumentException("Process with definition ID " + processDefinitionId + " does not exist"));

        ProcessOM processOM = process.getProcessOM();

        Execution execution = new Execution();
        execution.setId(UUID.randomUUID());
        execution.setInstanceId(UUID.randomUUID());
        execution.setProcessDefinitionId(process.getDefinitionId());
        execution.setProcessId(process.getId());
        execution.setStartTime(LocalDateTime.now());
        execution.setStatus(Execution.Status.RUNNING);
        execution.setCurrentNodeId(processOM.getStartNode().getId());
        execution.setVariables(variables);
        executionRepository.save(execution);
    }

    public void advanceStep(ExecutionContext context) {

    }



    public Optional<Process> getProcess(String processDefinitionId) {
        return Optional.ofNullable(processes.getOrDefault(
                processDefinitionId,
                processRepository.findLatestVersionByDefinitionId(processDefinitionId)
                        .orElseThrow(() -> new IllegalArgumentException("Process with definition ID " + processDefinitionId + " does not exist"))
        ));
    }

    public void startProcess(String processDefinitionId) {
        startProcess(processDefinitionId, new HashMap<>());
    }

}
