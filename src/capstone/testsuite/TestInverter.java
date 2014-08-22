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
public class TestInverter extends ReviewTest {
    
    private ReviewTest innerTest = null;
    
    public TestInverter(ReviewTest innerTest) {
        super(innerTest.minimum, innerTest.maximum);
        this.innerTest = innerTest;
    }

    @Override
    public double getScore(Review review, int index) throws CapException {
        return innerTest.getScore(review, index);
    }
    
    @Override
    public boolean getScorePassFail(Review review, int index) throws CapException {
        return !innerTest.getScorePassFail(review, index);
    }
    
    @Override
    public boolean passes(double score) {
        return innerTest.fails(score);
    }
    
    @Override
    public boolean fails(double score) {
        return innerTest.passes(score);
    }
    
}
