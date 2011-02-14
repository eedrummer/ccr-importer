/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.api;

/**
 *
 * @author ohdohd
 */
public class Configuration {
    private String umlsConfLocation = "org/ohd/umls/umls_db.cfg";
    private String lvgConfLocation = "org/ohd/umls/lvg_db.cfg";
    private String ccrVocabLocation = "org/ohd/pophealth/ccr/importer/ccrvocabulary.json";
    private String ccrXSDLocation = "org/ohd/CCRV1.xsd";


    public String getCcrVocabLocation() {
        return ccrVocabLocation;
    }

    public void setCcrVocabLocation(String ccrVocabLocation) {
        this.ccrVocabLocation = ccrVocabLocation;
    }

    public String getCcrXSDLocation() {
        return ccrXSDLocation;
    }

    public void setCcrXSDLocation(String ccrXSDLocation) {
        this.ccrXSDLocation = ccrXSDLocation;
    }

    public String getLvgConfLocation() {
        return lvgConfLocation;
    }

    public void setLvgConfLocation(String lvgConfLocation) {
        this.lvgConfLocation = lvgConfLocation;
    }

    public String getUmlsConfLocation() {
        return umlsConfLocation;
    }

    public void setUmlsConfLocation(String umlsConfLocation) {
        this.umlsConfLocation = umlsConfLocation;
    }
}
