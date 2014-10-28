/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

//Method for displaying custom exception message
import capstone.CapException;
import capstone.model.JSONWrapper;

//Methods for loading in yelp data
import capstone.model.Review;

//<!-- to do
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * 
 */
public class TestSuite {
    
    private List<ReviewTest> tests = new ArrayList<>();
    
    public TestSuite() {
    }
    
    /**
     * Take array of test and puts them into tests <!-- to do
     * @param rts 
     */
    public TestSuite(ReviewTest[] rts) {
        for (ReviewTest rt : rts) {
            if (rt == null) continue;
            tests.add(rt);
        }
    }
    
    /**
     * Completes the series of tests on a specific review
     * @param reviews <!-- to do
     * @param index
     * @return
     * @throws CapException 
     */
    public double testRecord(Review reviews, int index) throws CapException {
        double score = 0;
        
        for (ReviewTest rt : tests) {
            score += rt.getScore(reviews, index);
        }
        
        return score;
    }
    
    /**
     * Test a specific review with a specific test <!--
     * @param reviews
     * @param index
     * @param test
     * @return
     * @throws CapException 
     */
    public double testRecord(JSONWrapper reviews, int index, ReviewTest test) throws CapException {
        double score = test.getScore(reviews, index);
        for (ReviewTestListener rtl : getListeners(test))
            rtl.classify(reviews, index, this, test, score);

        return score;
    }
    
    //<!-- to do
    private HashMap<ReviewTest, List<ReviewTestListener>> listeners = new HashMap<>();
    public void addListener(ReviewTest test, ReviewTestListener l) {
        if (test == null || l == null) return;
        if (listeners.get(test) == null) {
            listeners.put(test, new ArrayList<ReviewTestListener>());
        }
        listeners.get(test).add(l);
    }
    
    //<!-- to do
    public void removeListener(ReviewTest test, ReviewTestListener l) {
        if (test == null || l == null) return;
        if (listeners.get(test) == null) return;
        if (!listeners.get(test).contains(l)) return;
        listeners.get(test).remove(l);
    }
    
    //<!--
    public ReviewTestListener[] getListeners(ReviewTest test) {
        if (listeners.get(test) == null || listeners.get(test).isEmpty())
            return new ReviewTestListener[]{};
        
        return listeners.get(test).toArray(new ReviewTestListener[listeners.get(test).size()]);
    }
    
    /**
     * Runs test suite on each review in the dataset
     * @param reviews <!--
     * @return
     * @throws CapException 
     */
    public double[] testAllRecords(Review reviews) throws CapException {
        double[] results = new double[reviews.size()];
        
        for (int i = 0; i < reviews.size(); i++) {
            results[i] = testRecord(reviews, i);
        }
        
        return results;
    }
    
    /**
     * <!-- to do
     * @param reviews
     * @return
     * @throws CapException 
     */
    public TestResult testAndStoreAllRecords(JSONWrapper reviews) throws CapException {
        TestResult results = new TestResult();
        
        for (int i = 0; i < reviews.size(); i++) {
            for (ReviewTest rt : tests) {
                boolean classify = true;
                double score = testRecord(reviews, i, rt);
                
                for (ReviewTestListener rtl : getListeners(rt))
                    classify = rtl.classify(reviews, i, this, rt, score);

                results.store(reviews.get(i), rt, score, classify);
            }
        }
        
        return results;
    }
    
    /**
     * Default test suite containing all available test suites
     * @return 
     */
    public static final TestSuite getDefaultSuite() {
        return new TestSuite( new ReviewTest[] {
           new S2_UpperCasePercentageTest(), 
           new S3_NewLineRatioTest(), 
           new S4_WordCountTest(), 
           new S5_ComplexWordCountTest(), 
           new S6_SentenceCountTest(), 
           new S7_AverageWordSyllableTest(), 
           new S8_AverageWordsPerSentenceTest(),
           new S9_FleschReadingEaseTest(),
        //   new S10_FleschKinkaidGradeLevelTest(0,0),
        //   new S11_FogIndexTest(0,0),
        //   new S12_SmogTest(0,0),
        });
    }

    @Override
    public String toString() {
        String str = "Test suite:\n";
        
        for (ReviewTest rt : tests) {
            str += "\t" + ReviewTest.niceClassName(rt.getClass().getSimpleName()) + "\n";
        }
        
        return str;
    }
    
    public static interface ReviewTestListener {
        public boolean classify(JSONWrapper reviews, int index, TestSuite suite, ReviewTest test, double score);
    }
    
}
