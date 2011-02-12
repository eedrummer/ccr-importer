/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.api;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.astm.ccr.ContinuityOfCareRecord;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ohd.pophealth.ccr.importer.InCompleteVocabularyException;
import org.ohd.pophealth.ccr.importer.RecordCreator;
import org.ohd.pophealth.ccr.importer.Vocabulary;
import org.ohd.pophealth.evaluator.QualityMeasureEvaluator;
import org.ohd.pophealth.json.MeasureReader;
import org.ohd.pophealth.json.measuremodel.QualityMeasure;
import org.ohd.pophealth.json.clinicalmodel.Record;
import org.ohd.pophealth.preprocess.PreProcessor;

/**
 * This is the main entry class for the popHealth CCR Validator/Importer.  Additional
 * service classes and implementation can be built using this class
 *
 * @author ohdohd
 */
public class Evaluator {
    private final static Logger LOG = Logger.getLogger(Evaluator.class.getName());

    private QualityMeasureEvaluator qme;
    private RecordCreator rc;
    private ArrayList<QualityMeasure> qMeasures;
    private PreProcessor pp;
    private Configuration config;
    boolean preProcess_inferCodes = false;
    boolean preProcess_fixTobacco = false;
    boolean preProcess_fixEncounters = false;
    private CCRValidator validator;

    public Evaluator() {
        this(new Configuration());
    }


    /**
     * Creates a new Evaluator with a set of Quality Measures to use in the
     * evaluations.
     *
     * @param qMeasures  The list of Quality Measures
     */
    public Evaluator(ArrayList<QualityMeasure> qMeasures){
        this();
        this.qMeasures = qMeasures;
    }

    /**
     * Creates a new Evaluator with a set of Quality Measures to use in the
     * evaluations.
     *
     * @param qMeasures  The list of Quality Measures
     */
    public Evaluator(Configuration config, ArrayList<QualityMeasure> qMeasures){
        this(config);
        this.qMeasures = qMeasures;
    }

    /**
     * Generic constructor.  Must call at least one of the <code>addMeasure</code>
     * methods to add quality measures to be evaluated.
     */
    public Evaluator(Configuration config){
        try {
            qme = new QualityMeasureEvaluator();
            Vocabulary v = Vocabulary.fromJson(this.getClass().getClassLoader().getResourceAsStream(config.getCcrVocabLocation()));
            rc = new RecordCreator(v);
            qMeasures = new ArrayList<QualityMeasure>();
            URL umlsConf = this.getClass().getClassLoader().getResource(config.getUmlsConfLocation());
            pp = new PreProcessor(umlsConf.getFile(), config.getLvgConfLocation());
            validator = new CCRValidator(config);
        } catch (InCompleteVocabularyException ex) {
            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonMappingException ex) {
            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonParseException ex) {
            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isPreProcess_fixEncounters() {
        return preProcess_fixEncounters;
    }

    public void setPreProcess_fixEncounters(boolean preProcess_fixEncounters) {
        this.preProcess_fixEncounters = preProcess_fixEncounters;
    }

    public boolean isPreProcess_fixTobacco() {
        return preProcess_fixTobacco;
    }

    public void setPreProcess_fixTobacco(boolean preProcess_fixTobacco) {
        this.preProcess_fixTobacco = preProcess_fixTobacco;
    }

    public boolean isPreProcess_inferCodes() {
        return preProcess_inferCodes;
    }

    public void setPreProcess_inferCodes(boolean preProcess_inferCodes) {
        this.preProcess_inferCodes = preProcess_inferCodes;
    }



    /**
     * Evaluates a CCR XML String against all added Measures and returns
     *  a JSON string result.
     * @param ccrXML  CCR XML
     * @return JSON representation of popHealth result
     */
    public String evaluate(String ccrXML){
        //Validate CCR File
        LOG.finest("Validating CCR");
        ContinuityOfCareRecord ccr = validator.validateCCR(ccrXML);

        // Check to make sure there a valid CCR was created
        // TODO fix when hooked up to real validator
        if (ccr != null){
            LOG.finest("Found a Valid CCR");
            if (preProcess_fixEncounters){
                ccr = pp.fixEncounters(ccr);
            }
            if (preProcess_fixTobacco){
                ccr = pp.fixTobaccoHx(ccr);
            }
            if (preProcess_inferCodes){
                ccr = pp.inferCodes(ccr);
            }
            // Import the CCR into standard json record
            Record r = rc.createRecord(ccr);
            LOG.finest("Evaulating record against quality measures");
            String result = qme.evaluate(r, qMeasures);
            LOG.log(Level.FINEST, "EVALUATION RESULT\n{0}", result);
            return result;
        }else {
            LOG.info("INVALID CCR returning last errors");
            return lastErrors;
        }
    }

    /**
     * Adds a quality measure to the list of quality measures to use in the
     * evaluations.
     *
     * @param qMeasure  The quality measure to add
     */
    public void addMeasure(QualityMeasure qMeasure){
        this.qMeasures.add(qMeasure);
        LOG.log(Level.FINEST, "Quality Measure {0} added", qMeasure.getId());
    }

    /**
     * Adda a quality measure in JSON format to the list of quality measures to
     * use in the evaluations.
     *
     * @param qMeasureJSON  JSON String representing the qualiy measures
     * @throws Exception  Exception thrown if problem parsing JSON string
     */
    public void addMeasure(String qMeasureJSON) throws Exception{
        addMeasure(MeasureReader.extractQualityMeasure(qMeasureJSON));
    }

    String lastErrors = "";  // Simple String to hold the errors for the JAXB validation

    public ContinuityOfCareRecord preProcess(ContinuityOfCareRecord ccr){
        ccr = pp.preProcess(ccr);
        return ccr;
    }
}
