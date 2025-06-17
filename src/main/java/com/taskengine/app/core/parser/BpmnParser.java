package com.taskengine.app.core.parser;

import com.taskengine.app.TDefinitions;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;

public class BpmnParser {


    // TODO Try manually parse BPMN instead of JAXB API to avoid dependent on JAXB API
    public void parse(InputStream is) throws BpmnParseException{

       try {

           JAXBContext jaxbContext = JAXBContext.newInstance(TDefinitions.class);
           Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
           JAXBElement<TDefinitions> definitions = (JAXBElement<TDefinitions>) unmarshaller.unmarshal(is);
           System.out.println(definitions);
       } catch (Exception e) {
           throw new BpmnParseException("Error parsing BPMN XML", e);
       }

    }
}
