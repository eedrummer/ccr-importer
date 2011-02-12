/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ohd.pophealth.preprocess;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.astm.ccr.Agent;
import org.astm.ccr.AlertType;
import org.astm.ccr.CodeType;
import org.astm.ccr.CodedDescriptionType;
import org.astm.ccr.ContinuityOfCareRecord;
import org.astm.ccr.ContinuityOfCareRecord.Body.Problems;
import org.astm.ccr.EncounterType;
import org.astm.ccr.ProblemType;
import org.astm.ccr.ResultType;
import org.astm.ccr.SocialHistoryType;
import org.astm.ccr.StructuredProductType;
import org.astm.ccr.StructuredProductType.Product;
import org.astm.ccr.TestType;
import org.ohd.umls.Code;
import org.ohd.umls.CodingSystem;
import org.ohd.umls.UMLSConfiguration;
import org.ohd.umls.UMLSInterface;

/**
 *
 * @author ohdohd
 */
public class PreProcessor {

    private static UMLSInterface umls;
    private static List<CodingSystem> availableCS;

    public PreProcessor(InputStream config, String lvgConfLocation) {
        umls = new UMLSInterface(new UMLSConfiguration(config), lvgConfLocation);
        setAvailableCS();
    }

    public PreProcessor(String fileName, String lvgConfLocation) {
        umls = new UMLSInterface(new UMLSConfiguration(fileName), lvgConfLocation);
        setAvailableCS();
    }

    private void setAvailableCS(){
        availableCS = umls.getCodingSystem();
    }

    public ContinuityOfCareRecord preProcess(ContinuityOfCareRecord ccr) {
        ccr = inferCodes(ccr);
        ccr = fixTobaccoHx(ccr);
        ccr = fixEncounters(ccr);
        return ccr;
    }

    public ContinuityOfCareRecord fixTobaccoHx(ContinuityOfCareRecord ccr) {
        if (ccr.getBody().getSocialHistory() != null) {
            ProblemType p = fixTobaccoHx(ccr.getBody().getSocialHistory().getSocialHistoryElement());
            if (p != null) {
                if (ccr.getBody().getProblems() != null) {
                    ccr.getBody().getProblems().getProblem().add(p);
                } else {
                    Problems prblms = new Problems();
                    prblms.getProblem().add(p);
                    ccr.getBody().setProblems(prblms);
                }
            }
        }
        return ccr;
    }
    private static final String[] cptEncounterList = {"99201", "99202", "99203", "99204",
        "99205", "99211", "99212", "99213", "99214", "99215", "99217", "99218", "99219",
        "99220", "99241", "99242", "99243", "99244", "99245", "99341", "99342", "99343",
        "99344", "99345", "99347", "99348", "99349", "99350", "99384", "99385", "99386",
        "99387", "99394", "99395", "99396", "99397", "99401", "99402", "99403", "99404",
        "99411", "99412", "99420", "99429", "99455", "99456"};
    private ArrayList<CodeType> encounterCodes = null;

    private void createEncounterCodes() {
        encounterCodes = new ArrayList<CodeType>();
        for (String c : cptEncounterList) {
            CodeType ct = new CodeType();
            ct.setCodingSystem("CPT");
            ct.setValue(c);
            encounterCodes.add(ct);
        }
    }

    public ContinuityOfCareRecord fixEncounters(ContinuityOfCareRecord ccr) {
        if (encounterCodes == null) {
            createEncounterCodes();
        }
        if (ccr.getBody().getEncounters() != null) {
            for (EncounterType et : ccr.getBody().getEncounters().getEncounter()) {
                if (et.getDescription() == null) {
                    CodedDescriptionType cdt = new CodedDescriptionType();
                    cdt.setText("Fixed Encounter");
                    et.setDescription(cdt);
                }
                for (CodeType ct : encounterCodes) {
                    et.getDescription().getCode().add(ct);
                }
            }
        }
        return ccr;
    }

    public ContinuityOfCareRecord inferCodes(ContinuityOfCareRecord ccr) {
        if (ccr.getBody().getMedications() != null) {
            codeMeds(ccr.getBody().getMedications().getMedication());
        }
        if (ccr.getBody().getImmunizations() != null) {
            codeMeds(ccr.getBody().getImmunizations().getImmunization());
        }
        if (ccr.getBody().getProblems() != null) {
            codeProblems(ccr.getBody().getProblems().getProblem());
        }
        if (ccr.getBody().getAlerts() != null) {
            codeAlerts(ccr.getBody().getAlerts().getAlert());
        }
        if (ccr.getBody().getResults() != null) {
            codeResults(ccr.getBody().getResults().getResult());
        }
        if (ccr.getBody().getVitalSigns() != null) {
            codeResults(ccr.getBody().getVitalSigns().getResult());
        }
        return ccr;
    }

    private void codeMeds(List<StructuredProductType> medications) {
        for (StructuredProductType spt : medications) {
            for (Product p : spt.getProduct()) {
                Logger.getLogger(PreProcessor.class.getName()).log(Level.INFO, "Looking up code for {0}", p.getProductName().getText());
                addCode(p.getProductName(), "rxnorm");
                if (p.getBrandName() != null) {
                    addCode(p.getBrandName(), "rxnorm");
                }
            }
        }
    }

    private void codeAlerts(List<AlertType> alerts) {
        for (AlertType at : alerts) {
            if (at.getDescription() != null) {
                addCode(at.getDescription(), "rxnorm");
            }
            for (Agent ag : at.getAgent()) {
                if (ag.getProducts() != null) {
                    for (StructuredProductType spt : ag.getProducts().getProduct()) {
                        for (Product p : spt.getProduct()) {
                            addCode(p.getProductName(), "rxnorm");
                            if (p.getBrandName() != null) {
                                addCode(p.getBrandName(), "rxnorm");
                            }
                        }
                    }
                }
            }
        }
    }

    private void codeProblems(List<ProblemType> problems) {
        for (ProblemType pt : problems) {
            if (pt.getDescription() != null) {
                addCode(pt.getDescription(), "snomedct");
            }
        }
    }

    private void codeResults(List<ResultType> results) {
        for (ResultType rt : results) {
            if (rt.getDescription() != null) {
                addCode(rt.getDescription(), "lnc");
                addCode(rt.getDescription(), "snomedct");
            }
            for (TestType tt : rt.getTest()) {
                if (tt.getDescription() != null) {
                    addCode(tt.getDescription(), "lnc");
                    addCode(tt.getDescription(), "snomedct");
                }
            }
        }
    }

    private ProblemType fixTobaccoHx(List<SocialHistoryType> socialHistoryElements) {
        for (SocialHistoryType sht : socialHistoryElements) {
            if (sht.getType() != null && sht.getStatus() != null) {
                if (sht.getType().getText().equalsIgnoreCase("smoking")) {
                    if (sht.getStatus().getText().equalsIgnoreCase("current")) {
                        ProblemType tobacAddic = new ProblemType();
                        tobacAddic.setCCRDataObjectID(Long.toString(System.currentTimeMillis()));
                        CodedDescriptionType desc = new CodedDescriptionType();
                        desc.setText("Current Smoker");
                        CodeType smoker = new CodeType();
                        smoker.setCodingSystem("SNOMEDCT");
                        smoker.setValue("160603005");
                        smoker.setVersion("2009");
                        desc.getCode().add(smoker);
                        tobacAddic.setDescription(desc);
                        Logger.getLogger(PreProcessor.class.getName()).log(Level.INFO, "Found Current Smoker");
                        return tobacAddic;
                    } else {
                        ProblemType tobacAddic = new ProblemType();
                        tobacAddic.setCCRDataObjectID(Long.toString(System.currentTimeMillis()));
                        CodedDescriptionType desc = new CodedDescriptionType();
                        desc.setText("Non Smoker");
                        CodeType smoker = new CodeType();
                        smoker.setCodingSystem("SNOMEDCT");
                        smoker.setValue("105539002");
                        smoker.setVersion("2009");
                        desc.getCode().add(smoker);
                        tobacAddic.setDescription(desc);
                        Logger.getLogger(PreProcessor.class.getName()).log(Level.INFO, "Found Non Smoker");
                        return tobacAddic;
                    }
                }
            }
        }
        return null;
    }

    private void addCode(CodedDescriptionType cdt, String vocab) {
        if (cdt.getText() == null || vocab == null) {
            return;
        } else {
            // Could check for known vocab
            CodingSystem cs = findCodingSystem(vocab);
            if (cs == null) {
                Logger.getLogger(PreProcessor.class.getName()).log(Level.WARNING, "Could not find vocab: {0}", vocab);
                return;
            }

            String norm = umls.normalize(cdt.getText());
            List<String> cuis = umls.getCUIs(norm);
            for (String cui : cuis) {
                Code code = umls.getCode(cui, cs);
                if (code != null) {
                    Logger.getLogger(PreProcessor.class.getName()).log(Level.INFO, "Adding Code: {0} [{1}] for [{2}]", new Object[]{code.getTerm(), code.getValue(), cdt.getText()});
                    CodeType ct = new CodeType();
                    ct.setCodingSystem(cs.getId());
                    ct.setVersion(cs.getVersion());
                    ct.setValue(code.getValue());
                    cdt.getCode().add(ct);
                } else {
                    Logger.getLogger(PreProcessor.class.getName()).log(Level.INFO, "No Code Found for: {0}", norm);
                }
            }

        }
    }

    private CodingSystem findCodingSystem(String name) {
        for (CodingSystem cs : availableCS) {
            if (name.equalsIgnoreCase(cs.getId())) {
                return cs;
            }
        }
        return null;
    }
}
