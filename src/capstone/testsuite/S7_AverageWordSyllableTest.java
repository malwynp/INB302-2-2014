/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

import capstone.CapException;
import capstone.model.JSONWrapper;
import capstone.model.Review;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class S7_AverageWordSyllableTest extends ReviewTest {

    public double getScore(JSONWrapper review, int index) throws CapException {
        if (review == null || (index < 0 || index >= review.size()))
           throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");

        //S4
        double numWords = new S4_WordCountTest().getScore(review,index);
        
        //S5
        double numComplex = new S5_ComplexWordCountTest().getScore(review,index);
        
        return (numComplex / numWords);
    }
    
}
