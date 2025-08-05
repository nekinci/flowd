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
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;


@SpringBootApplication
public class AppApplication {


	private final Engine engine;

    public AppApplication(Engine engine) {
        this.engine = engine;
    }


	public static class Ctx {

		static {
			System.out.println("Ctx static block executed");
		}


		static void print() {
			System.out.println("Ctx print method executed");
		}
		@HostAccess.Export
		public void print(String message) {
			System.out.println("Context message: " + message);
		}
	}


    @PostConstruct
	public void test() throws IOException, ParserException {
		InputStream inputStream = ResourceUtils.getURL("classpath:bpmn/diagram2.bpmn").openStream();

		engine.start();
		engine.uploadFlow(inputStream.readAllBytes());
		engine.startProcess("Process_2");



	}
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
