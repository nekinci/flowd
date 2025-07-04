package com.taskengine.app.parser;

import com.taskengine.app.*;
import com.taskengine.app.core.data.om.ProcessOM;
import com.taskengine.app.core.provider.Parser;
import com.taskengine.app.core.provider.ParserException;
import com.taskengine.app.parser.converter.Context;
import com.taskengine.app.parser.converter.ConverterService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BpmnParser implements Parser {
    private final ConverterService converterService;

    public BpmnParser(ConverterService converterService) {
        this.converterService = converterService;
    }

    @Override
    public List<ProcessOM> parse(InputStream is) throws ParserException {
       try {
           JAXBContext jaxbContext = JAXBContext.newInstance(TDefinitions.class);
           Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
           JAXBElement<TDefinitions> xml = (JAXBElement<TDefinitions>) unmarshaller.unmarshal(is);
           return convert(xml);
       } catch (Exception e) {
           throw new ParserException("Error parsing BPMN XML", e);
       }
    }


    public List<ProcessOM> convert(JAXBElement<TDefinitions> xml) {
        List<ProcessOM> processOMS = new ArrayList<>();
        for (JAXBElement<? extends TRootElement> rootElement : xml.getValue().getRootElement()) {
            if (rootElement.getValue() instanceof TProcess) {
                TProcess tProcess = (TProcess) rootElement.getValue();
                ProcessOM processOM = transformProcess(tProcess);
                processOMS.add(processOM);
            }
        }

        return processOMS;
    }

    private ProcessOM transformProcess(TProcess tProcess) {

        ProcessOM processOM = new ProcessOM(
                tProcess.getId(),
                tProcess.getName());

        Context context = new Context(processOM);

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
            processOM.addNode(converterService.convert(context, value));
        }


        for (TFlowElement flowElement: sequenceFlows) {
            TSequenceFlow value = (TSequenceFlow) flowElement;
            processOM.addFlow(converterService.convert(context, value));
        }

        return processOM;
    }
}
