/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.ccr.importer;

/**
 * Simple exception for when a measure category is not supported
 * 
 * @author Steven Waldren <swaldren at openhealthdata.com>
 */
public class NonSupportedCategoryException extends Exception {

    public NonSupportedCategoryException(String s){
        super(s);
    }

    public NonSupportedCategoryException(){
        super();
    }

}
