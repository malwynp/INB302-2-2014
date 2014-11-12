/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

import capstone.CapException;
import capstone.model.JSONWrapper;

/**
 *
 * @author mark
 */
public class S11_FogIndexTest extends ReviewTest {

    @Override
    public double getScore(JSONWrapper review, int index) throws CapException {
        if (review == null || (index < 0 || index >= review.size()))
            throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");
       
        //S4
        double wordCount = new S4_WordCountTest().getScore(review,index);
        
        //S5
        double numComplex = new S5_ComplexWordCountTest().getScore(review,index);
        
        //S6
        double sentenceCounter = new S6_SentenceCountTest().getScore(review,index);
        
        //Fog index formula
        return (.4 * ((wordCount / sentenceCounter) + (numComplex / wordCount) * 100));
    }
    
}
