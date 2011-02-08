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
public class Allergy extends Condition{

    private ArrayList<CodedValue> reaction;

    public Allergy(String id){
        super(id);
    }

}
