/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.clinicalmodel;

import java.util.ArrayList;
import java.util.Collection;
import org.ohd.pophealth.json.measuremodel.CodedValue;

/**
 *
 * @author ohdohd
 */
public class Medication extends BaseClinicalObject{
    private long started = BaseObject.minDate;
    private long stopped = BaseObject.maxDate;
    private ArrayList<CodedValue> status;

    public Medication(String id){
        super(id);
        this.status = new ArrayList<CodedValue>();
    }

    public Medication(String id, ArrayList<CodedValue> type, ArrayList<CodedValue> description,
            long started, long stopped, ArrayList<CodedValue> status) {
        super(id, type, description);
        this.started = started;
        this.stopped = stopped;
        this.status = status;
    }

    public long getStarted() {
        return started;
    }

    public void setStarted(long started) {
        this.started = started;
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

    public void addStatus(Collection<CodedValue> status){
        this.status.addAll(status);
    }

    public long getStopped() {
        return stopped;
    }

    public void setStopped(long stopped) {
        this.stopped = stopped;
    }

}
