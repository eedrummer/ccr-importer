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
 * @author swaldren
 */
public class BaseClinicalObject extends BaseObject{

    private ArrayList<CodedValue> type;
    private ArrayList<CodedValue> description;

    public BaseClinicalObject(String id) {
        super(id);
        type = new ArrayList<CodedValue>();
        description = new ArrayList<CodedValue>();
    }

    @Override
    protected String getCategory(){
        return "clinicalbase";
    }

    public BaseClinicalObject(String id, ArrayList<CodedValue> type, ArrayList<CodedValue> description) {
        super(id);
        this.type = type;
        this.description = description;
    }


    public ArrayList<CodedValue> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<CodedValue> description) {
        this.description = description;
    }

    public void addDescription(CodedValue description){
        this.description.add(description);
    }

    public void addDescription(Collection<CodedValue> description){
        this.description.addAll(description);
    }


    public ArrayList<CodedValue> getType() {
        return type;
    }

    public void setType(ArrayList<CodedValue> type) {
        this.type = type;
    }

    public void addType(CodedValue type){
        this.type.add(type);
    }

    public void addType(Collection<CodedValue> type){
        this.type.addAll(type);
    }

}
