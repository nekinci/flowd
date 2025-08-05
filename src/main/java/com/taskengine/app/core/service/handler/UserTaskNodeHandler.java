package com.taskengine.app.core.service.handler;

import com.taskengine.app.core.data.entity.Execution;
import com.taskengine.app.core.data.entity.Task;
import com.taskengine.app.core.data.om.UserTaskNode;
import com.taskengine.app.core.data.repository.TaskRepository;
import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.core.service.engine.ExecutionContext;

import java.util.UUID;
import java.util.logging.Logger;

public class UserTaskNodeHandler implements NodeHandler<UserTaskNode> {

    private static final Logger logger = Logger.getLogger(UserTaskNodeHandler.class.getName());

    private final TaskRepository taskRepository;

    public UserTaskNodeHandler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void handle(UserTaskNode node, ExecutionContext context) {
        logger.info("[UserTaskNode] is started...");

        if (context.getStatus() == Execution.Status.TASK_COMPLETED) {
            context.setStatus(Execution.Status.RUNNING);
            logger.info("[UserTaskNode] is resumed");

            Task task = taskRepository.findById(context.getTaskId())
                    .orElseThrow(() -> new IllegalArgumentException("No task found for execution: " + context.getExecution().getId()));
            task.setStatus(Task.TaskStatus.COMPLETED);
            taskRepository.save(task);
            logger.info("[UserTaskNode] Task marked as completed: " + task.getId());

            String id = node.getOutgoing().get(0).getTargetRef().getId();
            context.moveTo(id);
            logger.info("[UserTaskNode] Moved to next node: " + id);

            if (context.getTaskVariables() != null) {
                context.getTaskVariables().forEach(context::setVariable);
            }

            return;
        }

        context.setStatus(Execution.Status.WAITING_ACTION);
        taskRepository.save(new Task(
                UUID.randomUUID(),
                node.getAssignee(),
                node.getGroup(),
                context.getExecution().getId(),
                node.getId(),
                node.getName(),
                "Waiting for user action",
                Task.TaskStatus.UNCLAIMED
        ));
        logger.info("[UserTaskNode] is waiting for user action: " + node.getId());

    }

    @Override
    public Class<UserTaskNode> getNodeType() {
        return UserTaskNode.class;
    }
}
