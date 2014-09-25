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
        
        //Retreive specific JSON set
        JSONObject record = review.get(index);
        //Retreive review text out of specified JSON set
        String s = (String) record.get("text");
        
        //Replace possible html space replacements with the standard
        String words[] = s.split("[ \t\n.,;]+");
        
        //Initialise variables
        double numComplex = 0;
        
        //For each word in the words array
        for (String ss : words) {
            
            //Re-assign ss to a variable
            String word = ss;
            
            //Create regex pattern for checking syllables in a word
            Pattern pattern = Pattern.compile("[aeiouy]+");
            //Check regex pattern against string
            Matcher matcher = pattern.matcher(word);
            
            //Foreach match to the pattern against the word 
            while (matcher.find()){
                numComplex++;
            }
        }
        
        return (numComplex / (double) words.length);
    }
    
}
