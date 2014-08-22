/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

import capstone.CapException;
import capstone.yelpmodel.Review;

/**
 *
 * @author mark
 */
public class S8_AverageWordsPerSentenceTest extends ReviewTest {

    public S8_AverageWordsPerSentenceTest(double minimum, double maximum) {
        super(minimum, maximum);
    }

    public double getScore(Review review, int index) throws CapException {
        return (minimum + maximum) / 2;
//        throw new CapException("Not yet implemented: " + this.getClass().getSimpleName());
    }
    
}
