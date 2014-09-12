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
    public double testRecord(Review reviews, int index, ReviewTest test) throws CapException {
        return test.getScore(reviews, index);
    }
    public boolean testRecordPassFail(Review reviews, int index) throws CapException {
        
        for (ReviewTest rt : tests) {
            if (!rt.getScorePassFail(reviews, index))
                return false;
        }
        
        return true;
    }
    public boolean testRecordPassFail(Review reviews, int index, ReviewTest test) throws CapException {
        return test.getScorePassFail(reviews, index);
    }
    
    public double[] testAllRecords(Review reviews) throws CapException {
        double[] results = new double[reviews.size()];
        
        for (int i = 0; i < reviews.size(); i++) {
            results[i] = testRecord(reviews, i);
        }
        
        return results;
    }
    
    public TestResult testAndStoreAllRecords(Review reviews) throws CapException {
        TestResult results = new TestResult();
        
        for (int i = 0; i < reviews.size(); i++) {
            for (ReviewTest rt : tests) {
                results.store(reviews.get(i), rt, testRecord(reviews, i, rt), testRecordPassFail(reviews, i, rt));
            }
        }
        
        return results;
    }
    
    public static final TestSuite getDefaultSuite() {
        return new TestSuite( new ReviewTest[] {
           new S2_UpperCasePercentageTest(0, 15), 
           new S3_NewLineRatioTest(0, 10), 
           new S4_WordCountTest(5, 250), 
           new S5_ComplexWordCountTest(5, 250), 
           new S6_SentenceCountTest(1, 50), 
           new S7_AverageWordSyllableTest(1,3), 
           new S8_AverageWordsPerSentenceTest(3,10),
           new S9_FleschReadingEaseTest(0,0),
        //   new S10_FleschKinkaidGradeLevelTest(0,0),
        //   new S11_FogIndexTest(0,0),
        //   new S12_SmogTest(0,0),
        });
    }

    public boolean[] testAllRecordsPassFail(Review reviews) throws CapException {
        boolean[] results = new boolean[reviews.size()];
        
        for (int i = 0; i < reviews.size(); i++) {
            results[i] = testRecordPassFail(reviews, i);
        }
        
        return results;
    }
    
}
