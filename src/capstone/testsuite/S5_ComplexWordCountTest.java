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
public class S5_ComplexWordCountTest extends ReviewTest {

    public double getScore(JSONWrapper review, int index) throws CapException {
        if (review == null || (index < 0 || index >= review.size()))
            throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");
        
        //Retreive specific JSON set
        JSONObject record = review.get(index);
        //Retreive review text out of specified JSON set
        String text = (String) record.get("text");
        
        //Split on spaces or other possible tags that split words 
        String words[] = text.split("[ \t\n.,;]+");
        
        //Intialise variables
        int numComplex = 1;
        
        //For each word in the words array
        for (String ss : words) {
            
            //Re-assign ss to a variable
            String word = ss;
            
            //Create regex pattern for checking syllables in a word
            Pattern pattern = Pattern.compile("[aeiouy]+");
            //Check regex pattern against string
            Matcher matcher = pattern.matcher(word);
            
            //Initialise variables
            int count = 1;
            
            //Foreach match to the pattern against the word 
            while (matcher.find()){
                count++;
            }
            
            //If the word contains three or more instances of the pattern
            if(count >= 3){
              numComplex++;
            }
            
        }
                
        return numComplex;
    }
    
}
