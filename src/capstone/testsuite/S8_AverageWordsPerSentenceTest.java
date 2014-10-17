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
public class S8_AverageWordsPerSentenceTest extends ReviewTest {

    public double getScore(Review review, int index) throws CapException {
        if (review == null || !review.contains(index))
            throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");
        
        //Retreive specific JSON set
        JSONObject record = review.get(index);
        //Retreive review text out of specified JSON set
        String text = (String) record.get("text");
        
        //Replace possible html space replacements with the standard
        text = text.replace("<br/>", " ");
        text = text.replace("<br />", " ");
        text = text.replace("<p>", " ");
        text = text.replace("</p>", " ");
        text = text.replace("\n", " ");
        text = text.replace("\t", " ");
        
        //Split the review text on each space, thus the array stores each word as an instance
        String toks[] = text.split(" ");
        
        double wordCount = (double)toks.length;
        
        //Initialise variables
        double sentenceCounter = 0;
 
        //Replace possible sentence endings with the standard sentence ending
        text = text.replace("!", ".");
        text = text.replace("?", ".");
        
        //Create regex pattern for checking for one or more periods.
        Pattern pattern = Pattern.compile("[.]+");
        //Check regex pattern against string
        Matcher matcher = pattern.matcher(text);
        
        //For each single or group of periods in the text
        while (matcher.find()){
            sentenceCounter++;
        }
        
        return (wordCount / sentenceCounter);
    }
            
}
