package com.taskengine.app;

import com.taskengine.app.core.parser.BpmnParseException;
import com.taskengine.app.core.parser.BpmnParser;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class AppApplication {


	@PostConstruct
	public void test() throws IOException, BpmnParseException {
		InputStream inputStream = ResourceUtils.getURL("classpath:bpmn/diagram.bpmn").openStream();
		BpmnParser parser = new BpmnParser();
		parser.parse(inputStream);
	}
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
