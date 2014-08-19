/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

import capstone.CapException;
import capstone.yelpmodel.Review;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mark
 */
public class TestSuite {
    
    private List<ReviewTest> tests = new ArrayList<>();
    
    public TestSuite() {
    }
    
    public TestSuite(ReviewTest[] rts) {
        for (ReviewTest rt : rts) {
            if (rt == null) continue;
            tests.add(rt);
        }
    }
    
    public double testRecord(Review reviews, int index) throws CapException {
        double score = 0;
        
        for (ReviewTest rt : tests) {
            score += rt.getScore(reviews, index);
        }
        
        return score;
    }
    
    public double[] testAllRecords(Review reviews) throws CapException {
        double[] results = new double[reviews.size()];
        
        for (int i = 0; i < reviews.size(); i++) {
            results[i] = testRecord(reviews, i);
        }
        
        return results;
    }
    
    public static final TestSuite getDefaultSuite() {
        return new TestSuite( new ReviewTest[] {
           new S2_UpperCasePercentageTest(), 
           new S3_NewLineRatioTest(), 
           new S4_WordCountTest(), 
           new S5_ComplexWordCountTest(), 
           new S6_SentenceCountTest(), 
           new S7_AverageWordSyllableTest(), 
           new S8_AverageWordsPerSentenceTest(), 
        });
    }
    
}
