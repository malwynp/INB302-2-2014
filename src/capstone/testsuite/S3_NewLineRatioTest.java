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

    public S3_NewLineRatioTest(double minimum, double maximum) {
        super(minimum, maximum);
    }

    public double getScore(Review review, int index) throws CapException {
        if (review == null || !review.contains(index))
            throw new CapException("Bad data passed in " + this.getClass().getSimpleName() + ".getScore(" + review + ", " + index + ")");

        JSONObject record = review.get(index);
        String text = (String) record.get("text");
        
        text = text.replace("<br/>", "\n");
        text = text.replace("<br />", "\n");
        text = text.replace("<p>", "\n");
        
        int charCount = text.length();
        int newLineCount = 0;
        for (int i = 0; i < charCount; i++)
            if (text.charAt(i) == '\n')
                newLineCount++;
        
        return (double)newLineCount / (double)charCount * 100D;
    }
    
}
