/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.ccr.importer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.JsonMappingException;
import org.ohd.pophealth.json.JsonMapper;
import org.ohd.pophealth.json.measuremodel.CodedValue;

/**
 * A Vocabulary contains a set of TermSets
 * @author ohdohd
 */
public class Vocabulary {
    private HashMap<String, TermSet> termsets;  // Store of TermSets

    /**
     * Create an empty Vocabulary
     */
    public Vocabulary (){
        this.termsets = new HashMap<String, TermSet>();
    }

    /**
     * Checks to see if the TermSet is part of the Vocabulary
     * @param id <code>TermSet.id</code>
     * @return <code>true</code> if part of the Vocabulary
     */
    public boolean isValidTermSet(String id){
        return this.termsets.containsKey(id);
    }

    /**
     * Adds a TermSet to the Vocabulary, replaces any TermSet with same <code>TermSet.id</code>
     * @param ts the TermSet to add
     * @return any replaced TermSet
     */
    public TermSet addTermSet(TermSet ts){
        return this.termsets.put(ts.getId(), ts);
    }

    /**
     * Removes the TermSet from the Vocabulary
     * @param id
     * @return
     */
    public TermSet removeTermSet(String id){
        return this.termsets.remove(id);
    }

    /**
     * Returns an <code>Iterator</code> containing the identifiers for all of the
     * TermSets in the Vocabulary
     * @return
     */
    @JsonIgnore
    public Iterator<String> getAvailableTermSets(){
        return this.termsets.keySet().iterator();
    }

    /**
     * Get an <code>Iterator</code> containing the Terms from a specific TermSet
     * @param termSetId The TermSet to use
     * @return
     */
    public Iterator<String> getTerms(String termSetId){
        return this.termsets.get(termSetId).getTermIterator();
    }

    /**
     * Get an <code>Iterator</code> containing the codesets from a specific TermSet
     * @param termSetId The TermSet to use
     * @return
     */
    public Iterator<CodedValue> getCodes(String termSetId){
        return this.termsets.get(termSetId).getCodeIterator();
    }

    /**
     * Get all of the TermSets in the Vocabulary
     * @return
     */
    public HashMap<String, TermSet> getTermSets() {
        return termsets;
    }

    /**
     * Get a specific TermSet by id
     * @param id
     * @return
     */
    public TermSet getTermSet(String id){
        return this.termsets.get(id);
    }

    /**
     * Set the TermSets for the Vocabulary. This replaces any previous TermSets
     * @param termsets
     */
    public void setTermsets(HashMap<String, TermSet> termsets) {
        this.termsets = termsets;
    }

    /**
     * Converts the Vocabulary into a JSON String
     * @param prettyPrint Should the String be indented
     * @return JSON string
     * @throws JsonMappingException
     * @throws JsonGenerationException
     * @throws IOException
     */
    public String toJson(boolean prettyPrint) throws JsonMappingException,
            JsonGenerationException, IOException {
        return JsonMapper.toJson(this, prettyPrint);
    }

    /**
     * Reads a Vocabulary from JSON
     * @param json JSON string representing the Vocabulary
     * @return
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws IOException
     */
    public static Vocabulary fromJson(String json) throws JsonMappingException, JsonParseException, IOException{
        return (Vocabulary) JsonMapper.fromJson(json, Vocabulary.class);
    }

    /**
     * Reads a Vocabulary from JSON
     * @param json InputStream containing JSON string representing the Vocabulary
     * @return
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws IOException
     */
    public static Vocabulary fromJson(InputStream json) throws JsonMappingException, JsonParseException, IOException{
        return (Vocabulary) JsonMapper.fromJson(json, Vocabulary.class);
    }
}
