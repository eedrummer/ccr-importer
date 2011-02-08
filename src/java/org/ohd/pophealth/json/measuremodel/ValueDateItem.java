/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ohd.pophealth.json.measuremodel;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;

/**
 *
 * @author ohdohd
 */
public class ValueDateItem extends Item {

    private ArrayList<ValueDate> values;

    public ValueDateItem() {
        values = new ArrayList<ValueDate>();
    }

    public ValueDateItem(long date, String value) {
        this();
        this.values.add(new ValueDate(date, value));
    }

    public void addValueDate(long date, String value){
        this.values.add(new ValueDate(date, value));
    }

    private static JsonFactory jf = new JsonFactory();
    @Override
    public String toJSON(boolean prettyPrint) throws JsonMappingException, JsonGenerationException, IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = jf.createJsonGenerator(sw);
//        if (prettyPrint) {
//            jg.useDefaultPrettyPrinter();
//        }
        jg.writeStartArray();
          for (ValueDate v : this.values){
            jg.writeStartObject();
                jg.writeFieldName("date");
                jg.writeNumber(v.date);
                jg.writeFieldName("value");
                jg.writeString(v.value);
            jg.writeEndObject();
          }
        jg.writeEndArray();
        jg.flush();
        jg.close();
        return sw.toString();
    }

    public class ValueDate {

        private long date;
        private String value;

        public ValueDate(long date, String value){
            this.date = date;
            this.value = value;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
