/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.measuremodel;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

/**
 *
 * @author ohdohd
 */
public abstract class Item {

    public abstract String toJSON(boolean prettyprint)throws JsonMappingException,
            JsonGenerationException, IOException;

}
