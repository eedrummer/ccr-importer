/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Helper Class to manage converting POJO to JSON and vice versa
 *
 * @author swaldren
 */
public class JsonMapper {

    private static ObjectMapper m = new ObjectMapper();
    private static JsonFactory jf = new JsonFactory();

    static {
        jf.configure(Feature.ALLOW_COMMENTS, true);
        m.configure(Feature.ALLOW_COMMENTS, true);
    }

    /**
     * Create object of type <T> from the JSON string
     * @param <T> The type to try to map to and return
     * @param jsonAsString the JSON String
     * @param pojoClass The class to map to
     * @return return object of type <T>
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws IOException
     */
    public static <T> Object fromJson(String jsonAsString, Class<T> pojoClass)
            throws JsonMappingException, JsonParseException, IOException {
        return m.readValue(jsonAsString, pojoClass);
    }

    /**
     * Create object of type <T> from JSON in a file
     * @param <T> The type to try to map to and return
     * @param fr The filereader of the file containing the JSON string
     * @param pojoClass The class to map to
     * @return return object of type <T>
     * @throws JsonParseException
     * @throws IOException
     */
    public static <T> Object fromJson(FileReader fr, Class<T> pojoClass)
            throws JsonParseException, IOException
    {
        return m.readValue(fr, pojoClass);
    }

    /**
     * Create object of type <T> from JSON in an InputStream
     * @param <T> The type to try to map to and return
     * @param is The InputStream containing the JSON
     * @param pojoClass The class to map to
     * @return return object of type <T>
     * @throws JsonParseException
     * @throws IOException
     */
    public static <T> Object fromJson(InputStream is, Class<T> pojoClass)
            throws JsonParseException, IOException{
        return m.readValue(is, pojoClass);
    }

    /**
     * Converts the data in the POJO to JSON
     * @param pojo The object to extract
     * @param prettyPrint Should the resulting String be indented
     * @return JSON String
     * @throws JsonMappingException
     * @throws JsonGenerationException
     * @throws IOException
     */
    public static String toJson(Object pojo, boolean prettyPrint)
            throws JsonMappingException, JsonGenerationException, IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator jg = jf.createJsonGenerator(sw);
        if (prettyPrint) {
            jg.useDefaultPrettyPrinter();
        }
        m.writeValue(jg, pojo);
        return sw.toString();
    }

    /**
     * Writes the data in the POJO to a file as JSON
     * @param pojo The object to extract
     * @param fw The filewriter to write to
     * @param prettyPrint Should the resulting file text be indented
     * @throws JsonMappingException
     * @throws JsonGenerationException
     * @throws IOException
     */
    public static void toJson(Object pojo, FileWriter fw, boolean prettyPrint)
            throws JsonMappingException, JsonGenerationException, IOException {
        JsonGenerator jg = jf.createJsonGenerator(fw);
        if (prettyPrint) {
            jg.useDefaultPrettyPrinter();
        }
        m.writeValue(jg, pojo);
    }
}
