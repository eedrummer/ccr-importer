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
    public class Goal extends BaseClinicalObject{
        private long goalDate = BaseObject.maxDate;
        private String value;
        private String unit;
        public Goal(String id){
            super(id);
        }

        public Goal(String id, ArrayList<CodedValue> type, ArrayList<CodedValue> description,
                long goalDate, String value, String unit) {
            super(id, type, description);
            this.goalDate = goalDate;
            this.value = value;
            this.unit = unit;
        }

        public long getGoalDate() {
            return goalDate;
        }

        public void setGoalDate(long goalDate) {
            this.goalDate = goalDate;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    public String getValueString() {
        if (!"".equals(unit)){
            return value+" "+unit;
        }else{
            return value;
        }
    }
    }
