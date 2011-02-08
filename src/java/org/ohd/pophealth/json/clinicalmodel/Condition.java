/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.clinicalmodel;

import java.util.ArrayList;
import org.ohd.pophealth.json.measuremodel.CodedValue;

/**
 *
 * @author swaldren
 */
public class Condition extends BaseClinicalObject{
    /*
     * This is the date (seconds from epoch) that is
     * the known date that the condition started
     */
    private long onset = BaseObject.minDate;
    /*
     * This is the date (seconds from epoch) that is 
     * the known date that the condition was resolved
     */
    private long resolution = BaseObject.maxDate;
    
    private ArrayList<CodedValue> status;

    public Condition(String id, ArrayList<CodedValue> type, ArrayList<CodedValue> description,
            long onset, ArrayList<CodedValue> status) {
        super(id, type, description);
        this.onset = onset;
        this.status = status;
    }

    @Override
    protected String getCategory(){
        return "diagnosis_condition_problem";
    }

    public Condition(String id) {
        super(id);
        status = new ArrayList<CodedValue>();
    }

    public long getOnset() {
        return onset;
    }

    public void setOnset(long onset) {
        this.onset = onset;
    }

    public long getResolution() {
        return resolution;
    }

    public void setResolution(long resolution) {
        this.resolution = resolution;
    }

    public ArrayList<CodedValue> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<CodedValue> status) {
        this.status = status;
    }

    public void addStatus(CodedValue status){
        this.status.add(status);
    }

}
