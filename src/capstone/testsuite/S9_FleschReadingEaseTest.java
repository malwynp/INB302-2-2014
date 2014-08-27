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
public class S9_FleschReadingEaseTest extends ReviewTest {

    public S9_FleschReadingEaseTest(double minimum, double maximum) {
        super(minimum, maximum);
    }

    @Override
    public double getScore(Review review, int index) throws CapException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
