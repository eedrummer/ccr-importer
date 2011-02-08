/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.clinicalmodel;

import java.util.ArrayList;
import org.ohd.pophealth.json.measuremodel.CodedValue;

/**
 *
 * @author swaldren
 */
public class Patient {

    private long birthdate;  // milliseconds from epoch
    private String gender;
    private ArrayList<CodedValue> race;
    private ArrayList<CodedValue> ethnicity;

    public Patient() {
        race = new ArrayList<CodedValue>();
        ethnicity = new ArrayList<CodedValue>();
    }

    public Patient(long birthdate, String gender, ArrayList<CodedValue> race, ArrayList<CodedValue> ethnicity) {
        this.birthdate = birthdate;
        this.gender = gender;
        this.race = race;
        this.ethnicity = ethnicity;
    }

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }

    public ArrayList<CodedValue> getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(ArrayList<CodedValue> ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<CodedValue> getRace() {
        return race;
    }

    public void setRace(ArrayList<CodedValue> race) {
        this.race = race;
    }

    public void addRace(CodedValue cv){
        race.add(cv);
    }

    public void addEthnicity(CodedValue cv){
        ethnicity.add(cv);
    }
}
