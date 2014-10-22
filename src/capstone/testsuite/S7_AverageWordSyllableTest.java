/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

import capstone.CapException;
import capstone.yelpmodel.Review;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class S7_AverageWordSyllableTest extends ReviewTest {

    public double getScore(Review review, int index) throws CapException {
        if (review == null || !review.contains(index))
           throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");
        /*
        //S4
        double numWords = S4_WordCountTest.getScore(review,index);
        
        obj.getClass(S4_WordCountTest).getMethod(getScore, review, index);
        
        //S5
        double numComplex = S5_ComplexWordCountTest.getScore(review,index);
        
        return (numComplex / numWords);
        */ return .4;
    }
    
}
