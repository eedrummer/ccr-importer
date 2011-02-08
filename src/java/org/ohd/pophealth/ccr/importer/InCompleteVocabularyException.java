/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ohd.pophealth.ccr.importer;

/**
 * This exception is for when a required <code>TermSet</code> is not contained
 * in the current <code>Vocabulary</code>
 * @author ohdohd
 */
public class InCompleteVocabularyException extends Exception {

    public InCompleteVocabularyException(String msg){
        super(msg);
    }
    public InCompleteVocabularyException(){
        super();
    }
}
