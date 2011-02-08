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
public class Test extends BaseClinicalObject{

    private long collectionTime = BaseObject.minDate;
    private String value ="";
    private String units ="";

    public Test (String id){
        super(id);
    }

    public Test(String id, long collectionTime, ArrayList<CodedValue>type,
            ArrayList<CodedValue> description, String value, String units) {
        super(id, type, description);
        this.collectionTime = collectionTime;
        this.value = value;  //TODO Convert this to handle Coded values
        this.units = units;
    }

    public long getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(long collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getValueString() {

        if (!"".equals(units)){
            return value+" "+units;
        }else{
            return value;
        }
    }


}
