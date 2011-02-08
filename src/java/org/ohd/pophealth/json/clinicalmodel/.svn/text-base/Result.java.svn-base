/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.json.clinicalmodel;

import java.util.ArrayList;

/**
 *
 * @author ohdohd
 */
public class Result extends BaseClinicalObject{

    private long collectionTime = BaseObject.minDate;
    private ArrayList<Test> tests;

    public Result(String id){
        super(id);
        tests = new ArrayList<Test>();
    }

    @Override
    protected String getCategory(){
        return "laboratory_test";
    }

    public long getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(long collectionTime) {
        this.collectionTime = collectionTime;
    }

    public ArrayList<Test> getTests() {
        return tests;
    }

    public void setTests(ArrayList<Test> tests) {
        this.tests = tests;
    }

    public void addTest(Test t){
        this.tests.add(t);
    }

}
