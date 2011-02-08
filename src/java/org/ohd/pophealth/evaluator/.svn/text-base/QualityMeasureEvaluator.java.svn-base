/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ohd.pophealth.evaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ohd.pophealth.json.clinicalmodel.Allergy;
import org.ohd.pophealth.json.measuremodel.CodedValue;
import org.ohd.pophealth.json.measuremodel.Measure;
import org.ohd.pophealth.json.measuremodel.PopHealthPatientRecord;
import org.ohd.pophealth.json.measuremodel.QualityMeasure;
import org.ohd.pophealth.json.clinicalmodel.Record;

import org.ohd.pophealth.json.clinicalmodel.Condition;
import org.ohd.pophealth.json.clinicalmodel.Encounter;
import org.ohd.pophealth.json.clinicalmodel.Goal;
import org.ohd.pophealth.json.clinicalmodel.Medication;
import org.ohd.pophealth.json.clinicalmodel.Order;
import org.ohd.pophealth.json.clinicalmodel.Procedure;
import org.ohd.pophealth.json.clinicalmodel.Result;
import org.ohd.pophealth.json.clinicalmodel.Test;
import org.ohd.pophealth.json.measuremodel.BooleanItem;
import org.ohd.pophealth.json.measuremodel.DateItem;
import org.ohd.pophealth.json.measuremodel.DateRangeItem;
import org.ohd.pophealth.json.measuremodel.Item;
import org.ohd.pophealth.json.measuremodel.ValueDateItem;

/**
 * This class handles the evaluation of a <code>Record</code> against a set of
 * quality measures.
 *
 * @author ohdohd
 */
public class QualityMeasureEvaluator {

    private final static Logger LOG = Logger.getLogger(QualityMeasureEvaluator.class.getName());
    private Record r; //current working Record
    private PopHealthPatientRecord pop;  // current working popHealth result record

    /**
     * Evaluate a record against a set of quality measures
     *
     * @param record The extracted patient data
     * @param qList The set of <code>QualityMeasure</code> items to evaluate against
     * @return a JSON string representing the result of the evaluation as defined
     *          by the <code>QualityMeasure</code> items used.
     */
    public String evaluate(Record record, ArrayList<QualityMeasure> qList) {
        LOG.log(Level.FINEST, "Evaluating {0} measures", qList.size());
        // Set the current working record
        this.r = record;
        // Create a new result object which represent the JSON result
        pop = new PopHealthPatientRecord();
        // Set the information about the patient
        pop.setBirthdate(r.getPatient().getBirthdate());
        // Iterate through each quality measure and evaluate against it
        for (QualityMeasure q : qList) {
            evaluate(pop, q);
        }
        // Reset the working record
        r = null;
        try {
            // TODO set to false for production
            String jsonResult = pop.toJson(true);
            pop = null;
            return jsonResult;
        } catch (JsonMappingException ex) {
            Logger.getLogger(QualityMeasureEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonGenerationException ex) {
            Logger.getLogger(QualityMeasureEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(QualityMeasureEvaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
     * This method evaluates a single quality measure
     */
    private void evaluate(PopHealthPatientRecord pop, QualityMeasure q) {
        // Create a map to house the result items
        LinkedHashMap<String, Item> items = new LinkedHashMap<String, Item>();
        // Work through each measure in the quality measure
        for (Measure m : q.getMeasures()) {
            // Each type of item might (most likely) needs to be handled differently
            // Uses the enum Measure.CAT
            boolean match = false;
            switch (m.getCategory()) {
                case Condition:
                    evaluateCondition(m, items);
                    break;
                case Characteristic:
                    evaluateCondition(m, items);
                    break;
                case Encounter:
                    evaluateEncounter(m, items);
                    break;
                case Result:
                    evaluateResult(m, items);
                    break;
                case VitalSign:
                    // VitalSigns are the same as results
                    evaluateResult(m, items);
                    break;
                case Medication:
                    evaluateMedication(m, items);
                    break;
                case Immunization:
                    // Immunizations are the same as Medications
                    evaluateMedication(m, items);
                    break;
                case PhysicalExam:
                    // Physical Exam items could be either a procedure or result
                    match = evaluateProcedure(m, items);
                    if (!match){
                        items.remove(m.getName());
                        evaluateResult(m, items);
                    }
                    break;
                case Communication:
                    // Communication maybe an encounter or an order
                    match = evaluateEncounter(m, items);
                    if (!match){
                        items.remove(m.getName());
                        evaluateOrder(m, items);
                    }
                    break;
                case Allergy:
                    evaluateAllergy(m, items);
                    break;
                case Procedure:
                    evaluateProcedure(m, items);
                    break;
                case Order:
                    evaluateOrder(m, items);
                    break;
                case Goal:
                    evaluateGoal(m, items);
                    break;
                default:
                    LOG.log(Level.WARNING, "Found Unknown or Unsupported Category Type [{0}]", m.getCategory());
            }
            LOG.log(Level.FINER, "Adding Quality Measure {0} to popHealth record", q.getId());
        }
        // Add the results of the quality measure evaluation to the result object
        pop.addMeasureResult(q.getId(), items);
    }

    // TODO Pull the category specific evaluations out into another class to allow for
    //  multiple implementations in the future.
    private boolean evaluateCondition(Measure m, LinkedHashMap<String, Item> items) {
        LOG.log(Level.FINEST, "Evaluating Measure {0} against conditions", m.getName());
        boolean match = false;
        switch (m.getItemType()) {
            case DateItem:
                DateItem di = new DateItem();
                LinkedList<Long> dL = new LinkedList<Long>();
                for (Condition c : r.getConditions()) {
                    // TODO  Do we need to handle Active vs. Resolved conditions
                    if (codeMatch(m.getCodes(), c.getDescription())) {
                        dL.add(new Long(c.getOnset()));
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in condition {1}", new Object[]{m.getDescription(), c.getId()});
                    }
                }
                if (!dL.isEmpty()) {
                    di.setDate((Long[]) dL.toArray(new Long[0]));
                }
                items.put(m.getName(), di);
                break;
            case DateRangeItem:
                DateRangeItem dri = new DateRangeItem();
                for (Condition c : r.getConditions()) {
                    if (codeMatch(m.getCodes(), c.getDescription())) {
                        dri.addRange(c.getOnset(), c.getResolution());
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in condition {1}", new Object[]{m.getDescription(), c.getId()});
                    }
                }
                items.put(m.getName(), dri);
                break;
            case ValueDateItem:
                LOG.log(Level.WARNING, "Processing measure [{0}] and ValueDateItem is not valid for Conditions", m.getName());
                break;
            case BooleanItem:
                BooleanItem bi = new BooleanItem();
                for (Condition c : r.getConditions()) {
                    if (codeMatch(m.getCodes(), c.getDescription())) {
                        bi.setValue(true);
                        match = true;
                    }
                }
                // If no condition found BooleanItem.isValue defaults to false
                items.put(m.getName(), bi);
                break;
            default:
                LOG.log(Level.WARNING, "Non supported ITEM TYPE [{0}]", m.getItemType());

        }
        return match;
    }

    private boolean evaluateEncounter(Measure m, LinkedHashMap<String, Item> items) {
        LOG.log(Level.FINEST, "Evaluating Measure {0} against encounters", m.getName());
        boolean match = false;
        switch (m.getItemType()) {
            case DateItem:
                DateItem di = new DateItem();
                LinkedList<Long> dL = new LinkedList<Long>();
                for (Encounter e : r.getEncounters()) {

                    if (codeMatch(m.getCodes(), e.getDescription())) {
                        dL.add(new Long(e.getOccured()));
                        match = true;
                    }
                }
                if (!dL.isEmpty()) {
                    di.setDate((Long[]) dL.toArray(new Long[0]));
                }
                items.put(m.getName(), di);
                break;
            case DateRangeItem:
                DateRangeItem dri = new DateRangeItem();
                for (Encounter e : r.getEncounters()) {
                    if (codeMatch(m.getCodes(), e.getDescription())) {
                        dri.addRange(e.getOccured(), e.getEnded());
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in encounter {1}", new Object[]{m.getDescription(), e.getId()});
                    }
                }
                items.put(m.getName(), dri);
                break;
            case ValueDateItem:
                LOG.log(Level.WARNING, "Processing measure [{0}] and ValueDateItem is not valid for Encounters", m.getName());
                break;
            case BooleanItem:
                BooleanItem bi = new BooleanItem();
                for (Encounter e : r.getEncounters()) {
                    if (codeMatch(m.getCodes(), e.getDescription())) {
                        bi.setValue(true);
                        match = true;
                    }
                }
                // If no condition found BooleanItem.isValue defaults to false
                items.put(m.getName(), bi);
                break;
            default:
                LOG.log(Level.WARNING, "Non supported ITEM TYPE [{0}]", m.getItemType());

        }
        return match;
    }

    private boolean evaluateProcedure(Measure m, LinkedHashMap<String, Item> items) {
        // Currently just handles a procedure like an Encounter
        boolean match = false;
        switch (m.getItemType()) {
            case DateItem:
                DateItem di = new DateItem();
                LinkedList<Long> dL = new LinkedList<Long>();
                for (Procedure p : r.getProcedures()) {

                    if (codeMatch(m.getCodes(), p.getDescription())) {
                        dL.add(new Long(p.getOccured()));
                        match = true;
                    }
                }
                if (!dL.isEmpty()) {
                    di.setDate((Long[]) dL.toArray(new Long[0]));
                }
                items.put(m.getName(), di);
                break;
            case DateRangeItem:
                DateRangeItem dri = new DateRangeItem();
                for (Procedure p : r.getProcedures()) {
                    if (codeMatch(m.getCodes(), p.getDescription())) {
                        dri.addRange(p.getOccured(), p.getEnded());
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in encounter {1}", new Object[]{m.getDescription(), p.getId()});
                    }
                }
                items.put(m.getName(), dri);
                break;
            case ValueDateItem:
                LOG.log(Level.WARNING, "Processing measure [{0}] and ValueDateItem is not valid for Procedure", m.getName());
                break;
            case BooleanItem:
                BooleanItem bi = new BooleanItem();
                for (Procedure p : r.getProcedures()) {
                    if (codeMatch(m.getCodes(), p.getDescription())) {
                        bi.setValue(true);
                        match = true;
                    }
                }
                // If no condition found BooleanItem.isValue defaults to false
                items.put(m.getName(), bi);
                break;
            default:
                LOG.log(Level.WARNING, "Non supported ITEM TYPE [{0}]", m.getItemType());

        }
        return match;
    }

    private boolean evaluateResult(Measure m, LinkedHashMap<String, Item> items) {
        boolean match = false;
        switch (m.getItemType()) {
            case DateItem:
                DateItem di = new DateItem();
                LinkedList<Long> dL = new LinkedList<Long>();
                for (Result e : r.getResults()) {

                    if (codeMatch(m.getCodes(), e.getDescription())) {
                        dL.add(new Long(e.getCollectionTime()));
                        match = true;
                    } else {
                        for (Test t : e.getTests()) {
                            if (codeMatch(m.getCodes(), t.getDescription())) {
                                dL.add(new Long(t.getCollectionTime()));
                                match = true;
                            }
                        }
                    }
                }
                if (!dL.isEmpty()) {
                    di.setDate((Long[]) dL.toArray(new Long[0]));
                }
                items.put(m.getName(), di);
                break;
            case DateRangeItem:
                LOG.log(Level.WARNING, "Processing measure [{0}] and DateRangeItem is not valid for Results", m.getName());
                break;
            case ValueDateItem:
                ValueDateItem vdi = new ValueDateItem();
                for (Result e : r.getResults()) {
                    if (codeMatch(m.getCodes(), e.getDescription())) {
                        // Assume only one test and it contains the value
                        if (e.getTests().size() == 1) {
                            vdi.addValueDate(e.getCollectionTime(), e.getTests().get(0).getValueString());
                        }
                        match = true;
                    } else {
                        for (Test t : e.getTests()) {
                            if (codeMatch(m.getCodes(), t.getDescription())) {
                                vdi.addValueDate(t.getCollectionTime(), t.getValueString());
                                match = true;
                            }
                        }
                    }
                }
                items.put(m.getName(), vdi);
                break;
            case BooleanItem:
                BooleanItem bi = new BooleanItem();
                for (Result e : r.getResults()) {
                    if (codeMatch(m.getCodes(), e.getDescription())) {
                        bi.setValue(true);
                        match = true;
                    }
                }
                // If no condition found BooleanItem.isValue defaults to false
                items.put(m.getName(), bi);
                break;
            default:
                LOG.log(Level.WARNING, "Non supported ITEM TYPE [{0}]", m.getItemType());

        }
        return match;
    }

    private boolean evaluateMedication(Measure m, LinkedHashMap<String, Item> items) {
        boolean match = false;
        switch (m.getItemType()) {
            case DateItem:  // Assumption: DateItem is always the start date of the medication
                DateItem di = new DateItem();
                LinkedList<Long> dL = new LinkedList<Long>();
                for (Medication med : r.getMedications()) {
                    if (codeMatch(m.getCodes(), med.getDescription())) {
                        // Assumption: A medication may have been stopped or not
                        dL.add(new Long(med.getStarted()));
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in medication {1}", new Object[]{m.getDescription(), med.getId()});
                    }
                }
                if (!dL.isEmpty()) {
                    di.setDate((Long[]) dL.toArray(new Long[0]));
                }
                items.put(m.getName(), di);
                break;
            case DateRangeItem:
                DateRangeItem dri = new DateRangeItem();
                for (Medication med : r.getMedications()) {
                    if (codeMatch(m.getCodes(), med.getDescription())) {
                        dri.addRange(med.getStarted(), med.getStopped());
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in medication {1}", new Object[]{m.getDescription(), med.getId()});
                    }
                }
                items.put(m.getName(), dri);
                break;
            case ValueDateItem:
                // TODO Will there be any valuedate items for medications?
                LOG.log(Level.WARNING, "Processing measure [{0}] and ValueDateItem is not valid for Medications", m.getName());
                break;
            case BooleanItem:
                BooleanItem bi = new BooleanItem();
                for (Medication med : r.getMedications()) {
                    if (codeMatch(m.getCodes(), med.getDescription())) {
                        bi.setValue(true);
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in medication {1}", new Object[]{m.getDescription(), med.getId()});
                    }
                }
                // If no condition found BooleanItem.isValue defaults to false
                items.put(m.getName(), bi);
                break;
            default:
                LOG.log(Level.WARNING, "Non supported ITEM TYPE [{0}]", m.getItemType());
        }
        return match;
    }

    private boolean evaluateAllergy(Measure m, LinkedHashMap<String, Item> items) {
        boolean match = false;
        switch (m.getItemType()) {
            case DateItem:  // Assumption: DateItem is always the start date of the medication
                DateItem di = new DateItem();
                LinkedList<Long> dL = new LinkedList<Long>();
                for (Allergy alg : r.getAllergies()) {
                    if (codeMatch(m.getCodes(), alg.getDescription())) {
                        // Assumption: A medication may have been stopped or not
                        dL.add(new Long(alg.getOnset()));
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in allergy {1}", new Object[]{m.getDescription(), alg.getId()});
                    }
                }
                if (!dL.isEmpty()) {
                    di.setDate((Long[]) dL.toArray(new Long[0]));
                }
                items.put(m.getName(), di);
                break;
            case DateRangeItem:
                DateRangeItem dri = new DateRangeItem();
                for (Allergy alg : r.getAllergies()) {
                    if (codeMatch(m.getCodes(), alg.getDescription())) {
                        dri.addRange(alg.getOnset(), alg.getResolution());
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in allergy {1}", new Object[]{m.getDescription(), alg.getId()});
                    }
                }
                items.put(m.getName(), dri);
                break;
            case ValueDateItem:
                // TODO Will there be any valuedate items for medications?
                LOG.log(Level.WARNING, "Processing measure [{0}] and ValueDateItem is not valid for Allergies", m.getName());
                break;
            case BooleanItem:
                BooleanItem bi = new BooleanItem();
                for (Allergy alg : r.getAllergies()) {
                    if (codeMatch(m.getCodes(), alg.getDescription())) {
                        bi.setValue(true);
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in allergy {1}", new Object[]{m.getDescription(), alg.getId()});
                    }
                }
                // If no condition found BooleanItem.isValue defaults to false
                items.put(m.getName(), bi);
                break;
            default:
                LOG.log(Level.WARNING, "Non supported ITEM TYPE [{0}]", m.getItemType());
        }
        return match;
    }

    private boolean evaluateOrder(Measure m, LinkedHashMap<String, Item> items) {
        // TODO Finish method - need to check for match in Order.orderrequests
        boolean match = true;
        switch (m.getItemType()) {
            case DateItem:
                DateItem di = new DateItem();
                LinkedList<Long> dL = new LinkedList<Long>();
                for (Order ord : r.getOrders()) {
                    if (codeMatch(m.getCodes(), ord.getDescription())) {
                        // Assumption: A medication may have been stopped or not
                        dL.add(new Long(ord.getOrderDate()));
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in order {1}", new Object[]{m.getDescription(), ord.getId()});
                    }
                }
                if (!dL.isEmpty()) {
                    di.setDate((Long[]) dL.toArray(new Long[0]));
                }
                items.put(m.getName(), di);
                break;
            case DateRangeItem:
                LOG.log(Level.WARNING, "Processing measure [{0}] and DateRangeItem is not valid for Orders", m.getName());
                break;
            case ValueDateItem:
                LOG.log(Level.WARNING, "Processing measure [{0}] and ValueDateItem is not valid for Orders", m.getName());
                break;
            case BooleanItem:
                BooleanItem bi = new BooleanItem();
                for (Order ord : r.getOrders()) {
                    if (codeMatch(m.getCodes(), ord.getDescription())) {
                        bi.setValue(true);
                        match = true;
                        LOG.log(Level.FINEST, "Match Found for {0} in order {1}", new Object[]{m.getDescription(), ord.getId()});
                    }
                }
                // If no condition found BooleanItem.isValue defaults to false
                items.put(m.getName(), bi);
                break;
            default:
                LOG.log(Level.WARNING, "Non supported ITEM TYPE [{0}]", m.getItemType());
        }
        return match;
    }

    private boolean evaluateGoal(Measure m, LinkedHashMap<String, Item> items) {
        boolean match = false;
        switch (m.getItemType()) {
            case DateItem:
                DateItem di = new DateItem();
                LinkedList<Long> dL = new LinkedList<Long>();
                for (Order ord : r.getOrders()) {
                    for (Goal gol : ord.getGoals()) {
                        if (codeMatch(m.getCodes(), gol.getDescription())) {
                            // Assumption: A medication may have been stopped or not
                            dL.add(new Long(gol.getGoalDate()));
                            match = true;
                            LOG.log(Level.FINEST, "Match Found for {0} in goal {1}", new Object[]{m.getDescription(), gol.getId()});
                        }
                    }
                }
                if (!dL.isEmpty()) {
                    di.setDate((Long[]) dL.toArray(new Long[0]));
                }
                items.put(m.getName(), di);
                break;
            case DateRangeItem:
                LOG.log(Level.WARNING, "Processing measure [{0}] and DateRangeItem is not valid for Goal", m.getName());
                break;
            case ValueDateItem:
                ValueDateItem vdi = new ValueDateItem();
                for (Order ord : r.getOrders()) {
                    for (Goal gol : ord.getGoals()) {
                        if (codeMatch(m.getCodes(), gol.getDescription())) {
                            // Assumption: A medication may have been stopped or not
                            vdi.addValueDate(gol.getGoalDate(), gol.getValueString());
                            match = true;
                            LOG.log(Level.FINEST, "Match Found for {0} in goal {1}", new Object[]{m.getDescription(), gol.getId()});
                        }
                        // TODO Add check for match for order description
                    }
                }
                items.put(m.getName(), vdi);
                break;
            case BooleanItem:
                BooleanItem bi = new BooleanItem();
                for (Order ord : r.getOrders()) {
                    for (Goal gol : ord.getGoals()) {
                        if (codeMatch(m.getCodes(), gol.getDescription())) {
                            bi.setValue(true);
                            match = true;
                            LOG.log(Level.FINEST, "Match Found for {0} in goal {1}", new Object[]{m.getDescription(), gol.getId()});
                        }
                    }
                }
                // If no condition found BooleanItem.isValue defaults to false
                items.put(m.getName(), bi);
                break;
            default:
                LOG.log(Level.WARNING, "Non supported ITEM TYPE [{0}]", m.getItemType());
        }
        return match;
    }

    /*
     * Utility method to compare to lists of coded values.  Returns true if any code
     * in one list matches any code in another list
     */
    private boolean codeMatch(ArrayList<CodedValue> mCodes, ArrayList<CodedValue> cCodes) {
        // TODO Check for perfomance improvement
        // Bad Big-O notation algorithm, but currently expecting short lists

        // TODO Need to check for coding system.
        // Do not now becuase no overlap in SNOMED, ICD9, ICD10
        for (CodedValue cm : mCodes) {
            for (String cmv : cm.getValues()) {
                for (CodedValue cc : cCodes) {
                    for (String ccv : cc.getValues()) {
                        if (cmv.equalsIgnoreCase(ccv)) {
                            return true;
                        }
                    }
                }
            }
        }
        // No code match found
        return false;
    }
}
