/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ohd.pophealth.json.clinicalmodel;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.JsonMappingException;
import org.ohd.pophealth.json.JsonMapper;

/**
 *
 * @author ohdohd
 */
public class BaseObject {
    @JsonIgnore
    public static long minDate = -9223372036854775808L;
    @JsonIgnore
    public static long maxDate = 9223372036854775807L;

    private String id;

    public BaseObject(String id) {
        this.id = id;
    }

    protected String getCategory() {
        return "base";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toJson(boolean prettyPrint) throws JsonMappingException,
            JsonGenerationException, IOException {
        return JsonMapper.toJson(this, prettyPrint);
    }
}
