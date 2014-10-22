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
public class S3_NewLineRatioTest extends ReviewTest {

    public double getScore(Review review, int index) throws CapException {
        if (review == null || !review.contains(index))
            throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");
        
        //Retreive specific JSON set 
        JSONObject record = review.get(index);
        //Retreive review text out of specified JSON set
        String text = (String) record.get("text");
        
        //Replace html new line tags to standard new line tag
        text = text.replace("<br/>", "\n");
        text = text.replace("<br />", "\n");
        text = text.replace("<p>", "\n");
        
        //Initialise variables
        int charCount = text.length();
        String toks[] = text.split("\n");
        int newLineCount = toks.length;
        
        return ((double)newLineCount / (double)charCount) * 100;
    }
    
}
