package com.taskengine.app.parser;

import com.taskengine.app.*;
import com.taskengine.app.core.data.om.ProcessNode;
import com.taskengine.app.core.provider.Parser;
import com.taskengine.app.core.provider.ParserException;
import com.taskengine.app.parser.converter.Context;
import com.taskengine.app.parser.converter.ConverterService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BpmnParser implements Parser {
    private final ConverterService converterService;

    public BpmnParser(ConverterService converterService) {
        this.converterService = converterService;
    }

    @Override
    public List<ProcessNode> parse(InputStream is) throws ParserException {
       try {
           JAXBContext jaxbContext = JAXBContext.newInstance("com.taskengine.app");
           Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
           unmarshaller.setEventHandler(event -> {
               log.error("Error during unmarshalling: {} {}", event.getMessage(), event);
               return false;
           });
           JAXBElement<TDefinitions> xml = (JAXBElement<TDefinitions>) unmarshaller.unmarshal(new javax.xml.transform.stream.StreamSource(is), TDefinitions.class);
           return convert(xml);
       } catch (Exception e) {
           throw new ParserException("Error parsing BPMN XML", e);
       }
    }


    public List<ProcessNode> convert(JAXBElement<TDefinitions> xml) {
        List<ProcessNode> processNodes = new ArrayList<>();
        for (JAXBElement<? extends TRootElement> rootElement : xml.getValue().getRootElement()) {
            if (rootElement.getValue() instanceof TProcess) {
                TProcess tProcess = (TProcess) rootElement.getValue();
                ProcessNode processNode = transformProcess(tProcess);
                processNodes.add(processNode);
            }
        }

        return processNodes;
    }

    private ProcessNode transformProcess(TProcess tProcess) {

        ProcessNode processNode = new ProcessNode(
                tProcess.getId(),
                tProcess.getName());

        Context context = new Context(processNode);

        Set<TFlowElement> flowNodes = tProcess.getFlowElement()
                .stream().filter(x -> !(x.getValue() instanceof TSequenceFlow))
                .map(JAXBElement::getValue)
                .collect(Collectors.toSet());

        Set<TFlowElement> sequenceFlows = tProcess.getFlowElement().stream()
                .filter(x -> x.getValue() instanceof TSequenceFlow)
                .map(JAXBElement::getValue)
                .collect(Collectors.toSet());

        for (TFlowElement flowElement : flowNodes) {
            TFlowElement value = flowElement;
            processNode.addNode(converterService.convert(context, value));
        }


        for (TFlowElement flowElement: sequenceFlows) {
            TSequenceFlow value = (TSequenceFlow) flowElement;
            processNode.addFlow(converterService.convert(context, value));
        }

        return processNode;
    }
}
