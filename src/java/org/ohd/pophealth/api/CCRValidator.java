/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.api;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.astm.ccr.ContinuityOfCareRecord;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author ohdohd
 */
public class CCRValidator {
    private Validator validator;
    private ValidatonErrorHandler eHandler;
    private Configuration config;


    public CCRValidator(Configuration config){
        this.config = config;
        this.setupValidator();
    }

    public String getLastErrors(boolean prettyPrint) {
        try {
            return eHandler.toJson(prettyPrint);
        } catch (JsonMappingException ex) {
            Logger.getLogger(CCRValidator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonGenerationException ex) {
            Logger.getLogger(CCRValidator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CCRValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public ContinuityOfCareRecord validateCCR(String ccrXML) {
        try {
            // TODO Connect true CCR Validator
            // First see if it a valid XML instance
            if (ccrXML == null || "".equals(ccrXML)){
                Logger.getLogger(Evaluator.class.getName()).log(Level.WARNING, "CCR XML String Empty or NULL");
                return null;
            }
            StreamSource src = new StreamSource(new StringReader(ccrXML));
            eHandler.resetErrors();
            validator.validate(src);
            if (!eHandler.hasErrors()){
            try {
                JAXBContext jc = JAXBContext.newInstance("org.astm.ccr");
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                return (ContinuityOfCareRecord) unmarshaller.unmarshal(new StringReader(ccrXML));
            } catch (JAXBException ex) {
                Logger.getLogger(Evaluator.class.getName()).log(Level.WARNING, ex.getLocalizedMessage());
                return null;
            }
            }else{
                // Errors were found in the CCR
                return null;
            }
        } catch (SAXException ex) {
            Logger.getLogger(Evaluator.class.getName()).log(Level.WARNING, ex.getLocalizedMessage());
        } catch (IOException ex) {
            Logger.getLogger(Evaluator.class.getName()).log(Level.WARNING, ex.getLocalizedMessage());
        }
        return null;
    }

//    private Document parseStreamSource(StreamSource source, boolean validating) {
//        try {
//            // Create a builder factory
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            factory.setValidating(validating);
//            // need to be namespace aware for JAXB to be able to unmarshal DOM
//            factory.setNamespaceAware(true);
//            // Create the builder and parse the file
//            Document doc = factory.newDocumentBuilder().parse(new InputSource(source.getReader()));
//            return doc;
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SAXException ex) {
//            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

    private void setupValidator() {
        try {
            SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL xsdURL = this.getClass().getClassLoader().getResource(config.getCcrXSDLocation());
            File xsdFile = null;
            xsdFile = new File(xsdURL.toURI());
            // load a WXS schema, represented by a Schema instance
            Source schemaFile = new StreamSource(xsdFile);
            Schema schema = sf.newSchema(schemaFile);
            // create a Validator instance, which can be used to validate an instance document
            validator = schema.newValidator();
            eHandler = new ValidatonErrorHandler();
            validator.setErrorHandler(eHandler);
        } catch (SAXException ex) {
            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
