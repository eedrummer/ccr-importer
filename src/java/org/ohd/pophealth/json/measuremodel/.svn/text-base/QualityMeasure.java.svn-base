/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.measuremodel;

import java.util.ArrayList;

/**
 *
 * @author ohdohd
 */
public class QualityMeasure {

    private String id;
    private String name;
    private String description;
    private String category;
    private String steward;
    private ArrayList<Measure> measures;

    public QualityMeasure(String id, String name, String description,
            String category, String steward) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.steward = steward;
        measures = new ArrayList<Measure>();
    }

    public QualityMeasure(String id, String name, String description,
            String category, String steward, ArrayList<Measure> measures) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.steward = steward;
        this.measures = measures;
    }

    public QualityMeasure(){
        measures = new ArrayList<Measure>();
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSteward() {
        return steward;
    }

    public void setSteward(String steward) {
        this.steward = steward;
    }

    public ArrayList<Measure> getMeasures() {
        return measures;
    }

    public void setMeasures(ArrayList<Measure> measures) {
        this.measures = measures;
    }

    public void addMeasure(Measure m){
        measures.add(m);
    }


}
