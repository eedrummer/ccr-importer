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
public class DateItem extends Item {

    private long[] date;

    public DateItem() {
    }

    public DateItem(long[] date) {
        this.date = date;
    }

    public long[] getDate() {
        return date;
    }

    public void setDate(long[] date) {
        this.date = date;
    }

    public void setDate(Long[] date){
        this.date = new long[date.length];
        for(int i=0;i<this.date.length;i++){
            this.date[i] = date[i].longValue();
        }
    }

    private static JsonFactory jf = new JsonFactory();

    @Override
    public String toJSON(boolean prettyPrint) throws JsonMappingException,
            JsonGenerationException, IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = jf.createJsonGenerator(sw);
        if (prettyPrint) {
            jg.useDefaultPrettyPrinter();
        }
        jg.writeStartArray();
        if (date != null && date.length > 0) {
            for (long l : date) {
                jg.writeNumber(l);
            }
        }
        jg.writeEndArray();
        jg.flush();
        jg.close();
        return sw.toString();
    }
}
