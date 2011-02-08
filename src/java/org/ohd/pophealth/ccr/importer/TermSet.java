/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.ccr.importer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.ohd.pophealth.json.measuremodel.CodedValue;

/**
 *
 * @author ohdohd
 */
@JsonPropertyOrder({"id", "terms", "codes"})
public class TermSet {

    private ArrayList<CodedValue> codes;
    private HashSet<String> terms;
    private String id;

    /**
     * Creates a new TermSet
     * @param id  The identifier for the TermSet which will be used to
     *  retrieve the TermSet in a <code>Vocabulary</code>
     * @param codes Any codes associated with the TermSet
     * @param terms Any terms associated with the TermSet
     */
    @JsonCreator
    public TermSet(@JsonProperty("id") String id,
            @JsonProperty("codes") ArrayList<CodedValue> codes,
            @JsonProperty("terms") HashSet<String> terms) {
        this.id = id;
        this.codes = codes;
        this.terms = new HashSet<String>();
        Iterator<String> it = terms.iterator();
        while (it.hasNext()){
            this.terms.add(it.next().trim());
        }
    }

    /**
     * Creates an empty TermSet
     * @param id The identifier for the TermSet which will be used to
     *  retrieve the TermSet in a <code>Vocabulary</code>
     */
    public TermSet(String id) {
        this.id = id;
        this.terms = new HashSet<String>();
        this.codes = new ArrayList<CodedValue>();
    }

    /**
     * Get the identifier for the TermSet
     * @return The identifier for the TermSet
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the identifier for the TermSet which will be used to
     *  retrieve the TermSet in a <code>Vocabulary</code>
     * @param id The identifier for the TermSet
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Addes a term to the TermSet
     * @param term The term to add
     */
    public void addTerm(String term){
        this.terms.add(term.trim());
    }

    /**
     * Removes a term from the TermSet
     * @param term The term to remove
     * @return <code>true</code> if removed
     */
    public boolean removeTerm (String term){
        return this.terms.remove(term.trim());
    }

    /**
     * Gets an <code>Iterator</code> for the terms in the TermSet
     * @return Iterator containing the terms
     */
    @JsonProperty("terms")
    public Iterator<String> getTermIterator(){
        return this.terms.iterator();
    }

    /**
     * Checks to see if the term is in the TermSet
     * @param term The term to check
     * @return <code>true</code> if in the TermSet - case specific
     */
    public boolean contains(String term){
        return this.terms.contains(term.trim());
    }

    /**
     * Add a codeset to the TermSet
     * @param codedvalue The codeset to add
     */
    public void addCode (CodedValue codedvalue){
        this.codes.add(codedvalue);
    }

    /**
     * Remove a codeset from the TermSet
     * @param codedvalue The codeset to remove
     * @return <code>true</code> if removed
     */
    public boolean removeCode (CodedValue codedvalue){
        return this.codes.remove(codedvalue);
    }

    /**
     * Gets an <code>Iterator</code> for the codesets in the TermSet
     * @return Iterator containing the codesets
     */
    @JsonProperty("codes")
    public Iterator<CodedValue> getCodeIterator(){
        return this.codes.iterator();
    }

    /**
     * Check if a codeset is in the TermSet
     * @param codedvalue The codeset to check for
     * @return <code>true</code> if in the TermSet
     */
    public boolean contains (CodedValue codedvalue){
        return this.codes.contains(codedvalue);
    }
}
