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
public class S4_WordCountTest extends ReviewTest {

    public double getScore(Review review, int index) throws CapException {
        if (review == null || !review.contains(index))
            throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");

        JSONObject record = review.get(index);
        String text = (String) record.get("text");
        text = text.replace("<br/>", "");
        text = text.replace("<br />", "");
        text = text.replace("<p>", "");
        text = text.replace("</p>", "");
        
        String toks[] = text.split(" \t\n");
        
        return (double)toks.length;
    }
    
}
