/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

import capstone.CapException;
import capstone.model.Review;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class S4_WordCountTest extends ReviewTest {

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
        
        return (double)toks.length;
    }
    
}
