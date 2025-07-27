package com.taskengine.app;

import com.taskengine.app.core.provider.ParserException;
import com.taskengine.app.core.service.HandlerRegistry;
import com.taskengine.app.core.service.engine.Engine;
import com.taskengine.app.infra.persistence.repository.PersistentExecutionRepository;
import com.taskengine.app.infra.persistence.repository.PersistentFlowRepository;
import com.taskengine.app.infra.repository.ExecutionRepositoryImpl;
import com.taskengine.app.infra.repository.FlowRepositoryImpl;
import com.taskengine.app.infra.repository.ProcessRepositoryImpl;
import com.taskengine.app.parser.BpmnParser;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;


@SpringBootApplication
public class AppApplication {


	private final Engine engine;

    public AppApplication(Engine engine) {
        this.engine = engine;
    }


    @PostConstruct
	public void test() throws IOException, ParserException {
		InputStream inputStream = ResourceUtils.getURL("classpath:bpmn/diagram.bpmn").openStream();

		engine.start();
		engine.uploadFlow(inputStream.readAllBytes());
		engine.startProcess("Process_1");

	}
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
