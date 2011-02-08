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
public class DateRangeItem extends Item {

    private ArrayList<Range> dates;

    public DateRangeItem() {
        this.dates = new ArrayList<Range>();
    }

    public DateRangeItem(long start, long end) {
        this();
        this.dates.add(new Range(start, end));
    }

    public void addRange(long start, long end) {
        this.dates.add(new Range(start, end));
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
          for (Range r : this.dates){
            jg.writeStartObject();
                jg.writeFieldName("start");
                jg.writeNumber(r.start);
                jg.writeFieldName("end");
                jg.writeNumber(r.end);
            jg.writeEndObject();
          }
        jg.writeEndArray();
        jg.flush();
        jg.close();
        return sw.toString();
    }

    public class Range {

        private long start;
        private long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }
    }
}
