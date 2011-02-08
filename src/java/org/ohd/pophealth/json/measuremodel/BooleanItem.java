/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.measuremodel;

import java.io.IOException;
import java.io.StringWriter;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;

/**
 *
 * @author ohdohd
 */
public class BooleanItem extends Item{

    private boolean value = false;

    public BooleanItem(){}
    public BooleanItem(boolean value){
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    private static JsonFactory jf = new JsonFactory();
    @Override
    public String toJSON(boolean prettyprint) throws JsonMappingException,
            JsonGenerationException, IOException{
       StringWriter sw = new StringWriter();
        JsonGenerator jg = jf.createJsonGenerator(sw);
        if (prettyprint) {
            jg.useDefaultPrettyPrinter();
        }
        jg.writeBoolean(value);
        jg.flush();
        jg.close();
        return sw.toString();
    }

}
