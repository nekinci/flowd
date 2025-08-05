package com.taskengine.app.spring.config;

import com.taskengine.app.application.TaskService;
import com.taskengine.app.application.TaskServiceImpl;
import com.taskengine.app.core.data.om.InvokerType;
import com.taskengine.app.core.data.om.Node;
import com.taskengine.app.core.data.repository.ExecutionRepository;
import com.taskengine.app.core.data.repository.FlowRepository;
import com.taskengine.app.core.data.repository.ProcessRepository;
import com.taskengine.app.core.data.repository.TaskRepository;
import com.taskengine.app.core.expression.EvaluatorFactory;
import com.taskengine.app.core.invoker.*;
import com.taskengine.app.core.provider.Parser;
import com.taskengine.app.core.service.HandlerRegistry;
import com.taskengine.app.core.service.NodeHandler;
import com.taskengine.app.core.service.engine.Engine;
import com.taskengine.app.parser.BpmnParser;
import com.taskengine.app.parser.converter.Converter;
import com.taskengine.app.parser.converter.ConverterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SpringBeanExtensionConfiguration {



    @Bean
    public ConverterService converterService(List<Converter<?,?>> converterServices) {
        return new ConverterService(converterServices);
    }

    @Bean
    public Parser parser(ConverterService converterService) {
        return new BpmnParser(converterService);
    }

    @Bean
    public Engine engine(Parser parser,
                         ExecutionRepository executionRepository,
                         FlowRepository flowRepository,
                         ProcessRepository processRepository,
                         TaskRepository taskRepository,
                         HandlerRegistry registry) {
        return new Engine(parser,
                executionRepository, flowRepository, processRepository, taskRepository, registry);
    }

    @Bean
    public TaskService taskService(Engine engine) {
        return new TaskServiceImpl(engine);
    }

    @Bean
    public ActionRegistry actionRegistry() {
        var registry = new ActionRegistry();
        registry.registerAction("EchoAction", new EchoAction());
        return registry;
    }



    @Bean
    public Map<InvokerType, ServiceTaskInvoker> invokerMap(ActionRegistry actionRegistry) {
        return Map.of(
                InvokerType.ACTION, new ActionInvoker(actionRegistry)
        );
    }



    @Bean
    public ServiceTaskInvokerRegistry serviceTaskInvokerRegistry(Map<InvokerType, ServiceTaskInvoker> invokerMap) {
        return new ServiceTaskInvokerRegistry(invokerMap);
    }


    @Bean
    public HandlerRegistry handlerRegistry(List<NodeHandler<? extends Node>> handlers) {
        Map<Class<? extends Node>, NodeHandler<? extends Node>> handlerMap = new HashMap();

        for (NodeHandler<? extends Node> handler : handlers) {
            handlerMap.put(handler.getNodeType(), handler);
        }

        return new HandlerRegistry(handlerMap);
    }

    @Bean
    public EvaluatorFactory evaluatorFactory() {
        return new EvaluatorFactory();
    }
}
