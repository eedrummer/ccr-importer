/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.measuremodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swaldren
 */
public class Measure {
    private final static Logger LOG = Logger.getLogger(Measure.class.getName());

    public static enum TYPE { DateItem, ValueDateItem, DateRangeItem , BooleanItem};
    public static enum CAT { Condition, Result, Characteristic, Encounter, 
            VitalSign, Medication, Immunization, PhysicalExam, Procedure, Allergy,
            Communication, Order, Goal, NegativeRationale, Unknown};
    private static final HashMap<String, Measure.CAT> catMap;

    static{
        catMap = new HashMap<String, Measure.CAT>();
        catMap.put("procedure", CAT.Procedure);
        catMap.put("substance_allergy", CAT.Allergy);
        catMap.put("medication_allergy", CAT.Allergy);
        catMap.put("diagnosis_condition_problem", CAT.Condition);
        catMap.put("laboratory_test", CAT.Result);
        catMap.put("result", CAT.Result);
        catMap.put("encounter", CAT.Encounter);
        catMap.put("vital_sign", CAT.VitalSign);
        catMap.put("medication", CAT.Medication);
        catMap.put("medication_administered", CAT.Medication);
        catMap.put("immunization", CAT.Immunization);
        catMap.put("physical_exam", CAT.PhysicalExam);
        catMap.put("patient_characteristic", CAT.Characteristic);
        catMap.put("characteristic", CAT.Characteristic);
        catMap.put("communication", CAT.Communication);
        catMap.put("care_goal", CAT.Goal);
        catMap.put("negation_rationale", CAT.NegativeRationale);
        catMap.put("unknown", CAT.Unknown);
    }

    private String name;
    private String description;
    private CAT category;
    private TYPE itemType;
    private ArrayList<CodedValue> codes;

    public Measure(String name, String description,
            CAT category, TYPE itemType,
            ArrayList<CodedValue> codes) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.itemType = itemType;
        this.codes = codes;
    }

    public Measure() {
        this.codes = new ArrayList<CodedValue>();
    }

    public ArrayList<CodedValue> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<CodedValue> codes) {
        this.codes = codes;
    }

    public void addCode(CodedValue cv){
        codes.add(cv);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public CAT getCategory() {
        return category;
    }

    public void setCategory(CAT category) {
        this.category = category;
    }

    public TYPE getItemType() {
        return itemType;
    }

    public void setItemType(TYPE itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CAT getCAT(String standard_category){
        CAT c = Measure.catMap.get(standard_category);
        if (c == null){
            LOG.log(Level.WARNING, "Standard category [{0}] is not a known category", standard_category);
            return CAT.Unknown;
        }else{
            return c;
        }
    }

    public static String getCAT(Measure.CAT c){
        for (String s : catMap.keySet()){
            if (catMap.get(s).equals(c)) return s;
        }
        // This should never be reached
        return "unknown";
    }

}
