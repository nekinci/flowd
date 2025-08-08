package com.taskengine.app.core.service.handler;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.core.data.om.ParallelGatewayNode;
import com.taskengine.app.core.data.om.flow.Flow;
import com.taskengine.app.core.data.repository.ExecutionRepository;
import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.core.service.engine.EngineException;
import com.taskengine.app.core.service.engine.ExecutionContext;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ParallelGatewayNodeHandler implements NodeHandler<ParallelGatewayNode> {


    private final ExecutionRepository executionRepository;
    private final static Logger log = Logger.getLogger(ParallelGatewayNodeHandler.class.getName());

    public ParallelGatewayNodeHandler(ExecutionRepository executionRepository) {
        this.executionRepository = executionRepository;
    }



//TODO ensure that the parallel gateway is transactional
    @Override
    public void handle(ParallelGatewayNode node, ExecutionContext context) {

        log.info("[ParallelGatewayNode] Handling Parallel Gateway: " + node.getId());

        if (node.isJoining()) {
            log.info("[ParallelGatewayNode] Joining parallel executions for node: " + node.getId());
            Execution parentExecution = executionRepository.findById(context.getParentExecutionId())
                    .orElseThrow(() -> new EngineException("Parent execution not found for joining parallel gateway: " + node.getId()));
            long version = parentExecution.getVersion();
            parentExecution.setVersion(version + 1);
            context.setStatus(Execution.Status.COMPLETED);

            log.info("[ParallelGatewayNode] Parent execution updated to version: " + parentExecution.getVersion());

            int expected = node.getIncoming().size();

            List<Execution> completedChildren =
                    executionRepository.getCompletedExecutionsByParentId(context.getParentExecutionId())
                            .stream()
                            .filter(execution -> !Objects.equals(context.getExecutionId(), execution.getId()))
                            .collect(Collectors.toList());

            log.info("[ParallelGatewayNode] Completed children count: " + completedChildren.size() + ", expected: " + expected);

            if (completedChildren.size() != expected - 1) {
                log.info("[ParallelGatewayNode] Not all child executions are completed yet. Waiting for more children to complete.");
                executionRepository.saveByVersion(parentExecution, version);
                return; // Wait for all child executions to complete
            }

            parentExecution.setStatus(Execution.Status.PLANNED);
            parentExecution.setCurrentNodeId(node.getOutgoing().get(0).getTargetRef().getId());
            executionRepository.saveByVersion(parentExecution, version);

            //Child execution eventually will be completed on the orchestration side

        } else {

            for (Flow outgoing : node.getOutgoing()) {
                String targetId = outgoing.getTargetRef().getId();
                Execution childExecution = context.createChildExecution(targetId);
                executionRepository.save(childExecution);
                log.info("[ParallelGatewayNode] Created child node: " + targetId);
            }
            context.setStatus(Execution.Status.WAITING);
        }
    }

    @Override
    public Class<ParallelGatewayNode> getNodeType() {
        return ParallelGatewayNode.class;
    }
}
