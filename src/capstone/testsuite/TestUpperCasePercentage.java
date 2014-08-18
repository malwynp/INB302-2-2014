/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

import capstone.yelpmodel.Review;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class TestUpperCasePercentage implements ReviewTest {

    public double getScore(Review review, int index) {
        
        JSONObject subject = review.get(index);
        if (subject == null) return 0;
        
        int capped = 0;
        
        String text = (String) subject.get("text");
        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) capped++;
        }
        
        return ((double)capped / (double)text.length()) * 100;
        
    }
    
}
