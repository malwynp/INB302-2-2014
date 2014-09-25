/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

import capstone.CapException;
import capstone.yelpmodel.Review;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class S2_UpperCasePercentageTest extends ReviewTest {

    public double getScore(Review review, int index) throws CapException {
        if (review == null || !review.contains(index))
            throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");
        
        JSONObject subject = review.get(index);
        if (subject == null) return 0;
        
        int capped = 0, alphabet = 0;
        
        String text = (String) subject.get("text");
        for (int i = 0; i < text.length(); i++) {
            if (Character.isAlphabetic(text.charAt(i))) alphabet++;
            if (Character.isUpperCase(text.charAt(i))) capped++;
        }
        
        return ((double)capped / (double)alphabet) * 100;
        
    }
    
}
