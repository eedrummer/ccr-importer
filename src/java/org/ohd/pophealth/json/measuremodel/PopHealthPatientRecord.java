/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ohd.pophealth.json.measuremodel;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.ohd.pophealth.json.JsonMapper;

/**
 *
 * @author ohdohd
 */
public class PopHealthPatientRecord {

    private long birthdate;
    private ArrayList<MeasureResult> measures;

    public PopHealthPatientRecord(long birthdate) {
        this.birthdate = birthdate;
        measures = new ArrayList<MeasureResult>();
    }

    public PopHealthPatientRecord() {
        measures = new ArrayList<MeasureResult>();
    }

    public void addMeasureResult(String id, LinkedHashMap<String, Item> map) {
        measures.add(new MeasureResult(id, map));
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public ArrayList<MeasureResult> getMeasures() {
        return measures;
    }

    private static class MeasureResult {

        public String id;
        public LinkedHashMap<String, Item> map;

        public MeasureResult() {
        }

        public MeasureResult(String id, LinkedHashMap<String, Item> map) {
            this.id = id;
            this.map = map;
        }
    }

    private static JsonFactory jf = new JsonFactory();
    public String toJson(boolean prettyPrint) throws JsonMappingException,
            JsonGenerationException, IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = jf.createJsonGenerator(sw);
        if (prettyPrint) {
            jg.useDefaultPrettyPrinter();
        }
        jg.writeStartObject();
            jg.writeNumberField("birthdate", birthdate);
            jg.writeObjectFieldStart("measures");
                for (MeasureResult mr : this.measures){
                    jg.writeObjectFieldStart(mr.id);
                        for(String k : mr.map.keySet()){
                            jg.writeFieldName(k);
                            jg.writeRawValue(mr.map.get(k).toJSON(prettyPrint));
//                            jg.writeStartArray();
//                                for(String s : mr.map.get(k)){
//                                    // TODO this needs to be fixed when items fixed in MeasureReader class
//                                    jg.writeRawValue(s);
//                                }
//                            jg.writeEndArray();
                        }
                    jg.writeEndObject();
                }
           jg.writeEndObject();
        jg.writeEndObject();
        jg.flush();
        jg.close();
        return sw.toString();
        //return JsonMapper.toJson(this, prettyPrint);
    }
}
