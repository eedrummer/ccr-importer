/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.api;

import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ohd.pophealth.json.JsonMapper;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author ohdohd
 */
public class ValidatonErrorHandler implements ErrorHandler{

    private ArrayList<Error> errors = new ArrayList<Error>();

    public void warning(SAXParseException saxpe) throws SAXException {
        handleError(saxpe);
    }

    public void error(SAXParseException saxpe) throws SAXException {
        handleError(saxpe);
    }

    public void fatalError(SAXParseException saxpe) throws SAXException {
        handleError(saxpe);
    }

    private void handleError(SAXParseException ex){
        Error e = new Error();
        e.setMessage(ex.getLocalizedMessage());
        e.setLineNumber(ex.getLineNumber());
        e.setColumnNumber(ex.getColumnNumber());
        errors.add(e);
    }

    public ArrayList<Error> getErrors() {
        return errors;
    }

    public void resetErrors(){
        errors.clear();
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public String toJson(boolean prettyPrint) throws JsonMappingException,
            JsonGenerationException, IOException {
        if (hasErrors()){
            return JsonMapper.toJson(this, prettyPrint);
        }else{
            return "errors:{}";
        }
    }

    public class Error{
        private String message;
        private int lineNumber;
        private int columnNumber;

        public int getColumnNumber() {
            return columnNumber;
        }

        public void setColumnNumber(int columnNumber) {
            this.columnNumber = columnNumber;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
