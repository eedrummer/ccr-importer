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
public class Procedure extends Encounter{


    public Procedure(String id){
        super(id);
    }

    public Procedure(String id, ArrayList<CodedValue> type, ArrayList<CodedValue> description, long occurred, ArrayList<String> providers, CodedValue indication) {
        super(id, type, description, occurred, providers, indication);
    }

}
