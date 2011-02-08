/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.measuremodel;

import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 *
 * @author swaldren
 */
@JsonPropertyOrder({"codingSystem", "version", "values"})
public class CodedValue {

    private String codingSystem;
    private String version;
    private ArrayList<String> values;

    public CodedValue() {
        this.values = new ArrayList<String>();
    }

    public CodedValue(String codingSystem, String version, ArrayList<String> values) {
        this.codingSystem = codingSystem;
        this.version = version;
        this.values = values;
    }

    public String getCodingSystem() {
        return codingSystem;
    }

    public void setCodingSystem(String codingSystem) {
        this.codingSystem = codingSystem;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void addValue(String v){
        values.add(v);
    }

    /**
     * Checks equality between two <code>CodedValue</code> objects which means:
     * <ol>
     *  <li>The Coding Systems are the same and</li>
     *  <li>The Version of the Coding Systems are the same and</li>
     *  <li>Both code lists are not empty and
     *  <li>That there is at least one code value in common</li>
     * </ol>
     *
     * @param obj The <code>CodedValue</code> object to compare to
     * @return true only if they are the same c
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CodedValue other = (CodedValue) obj;
        if ((this.codingSystem == null) ? (other.codingSystem != null) : !this.codingSystem.equals(other.codingSystem)) {
            return false;
        }
        if ((this.version == null) ? (other.version != null) : !this.version.equals(other.version)) {
            return false;
        }
        if (this.values.isEmpty() || other.values.isEmpty()) {
            return false;
        }
        for (String t : this.values){
            if (other.values.contains(t)){
                return true;
            }
        }
        return false;
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.codingSystem != null ? this.codingSystem.hashCode() : 0);
        hash = 13 * hash + (this.version != null ? this.version.hashCode() : 0);
        hash = 13 * hash + (this.values != null ? this.values.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.codingSystem);
        sb.append(" [");
        sb.append(this.version);
        sb.append("] ");
        sb.append("{");
        for (int x=0; x<this.values.size();x++){
            sb.append(this.values.get(x));
            if (x < this.values.size()-1){
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }

}
