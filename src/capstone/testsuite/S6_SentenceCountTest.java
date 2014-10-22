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
public class S6_SentenceCountTest extends ReviewTest {
    
     public double getScore(Review review, int index) throws CapException {
        if (review == null || !review.contains(index))
           throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");

        //Initialise variables
        double sentenceCounter = 1;
        
        //Retreive specific JSON set
        JSONObject record = review.get(index);
        //Retreive review text out of specified JSON set
        String text = (String) record.get("text");
        
        //Replace possible sentence endings with the standard
        text = text.replace("!", ".");
        text = text.replace("?", ".");
        
        //Create regex pattern for checking for one or more periods.
        Pattern pattern = Pattern.compile("[.]+");
        //Check regex pattern against string
        Matcher matcher = pattern.matcher(text);
        
        //For each match of the pattern within the string
        while (matcher.find()){
            sentenceCounter++;
        }
        
        return sentenceCounter;
    }
}
