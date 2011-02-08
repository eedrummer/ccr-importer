/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.clinicalmodel;

import java.util.ArrayList;
import org.ohd.pophealth.json.measuremodel.CodedValue;

/**
 *
 * @author ohdohd
 */
public class Encounter  extends BaseClinicalObject{
    private long occurred = BaseObject.minDate;
    private long ended = BaseObject.maxDate;
    private ArrayList<String> providers;
    private CodedValue indication;

    public Encounter(String id, ArrayList<CodedValue> type, ArrayList<CodedValue> description,
            long occurred, ArrayList<String> providers, CodedValue indication) {
        super(id, type, description);
        this.occurred = occurred;
        this.providers = providers;
        this.indication = indication;
    }

    public Encounter(String id){
        super(id);
        providers = new ArrayList<String>();
    }

    @Override
    protected String getCategory(){
        return "encounter";
    }

    public CodedValue getIndication() {
        return indication;
    }

    public void setIndication(CodedValue indication) {
        this.indication = indication;
    }

    public long getOccured() {
        return occurred;
    }

    public void setOccurred(long occurred) {
        this.occurred = occurred;
    }

    public long getEnded() {
        return ended;
    }

    public void setEnded(long ended) {
        this.ended = ended;
    }

    public ArrayList<String> getProviders() {
        return providers;
    }

    public void addProvider(String a){
        providers.add(a);
    }

    public void setProviders(ArrayList<String> providers) {
        this.providers = providers;
    }


}
