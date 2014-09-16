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
public abstract class ReviewTest {
    
    public static final int PROBABILITY_SCORE_MODEL = 1;
    public static final int PERCENTAGE_SCORE_MODEL = 2;
    public static final int ABSOLUTE_SCORE_MODEL = 3;
    
    double minimum = 0, maximum = 100;
    public ReviewTest(double minimum, double maximum) {
        setMinimum(minimum);
        setMaximum(maximum);
    }
    public ReviewTest() {
    }
    
    public abstract double getScore(Review review, int index) throws CapException;

    public static String niceClassName(String str) {
        return niceClassName(str, true);
    }
    
    public static final String niceClassName(String str, boolean withPrefix) {
        // Nice-ify S#_ prefix, if any
        if (str.contains("_")) {
            String prefix = str.substring(0, str.indexOf("_"));
            str = str.substring(str.indexOf("_") + 1);
            str = (withPrefix) ? (prefix + "\t" + str) : str;
        }
        
        // Remove *Test suffix, if any
        if (str.endsWith("Test")) {
            str = str.substring(0, str.length() - 4);
        }
        
        return str;
    }
    
    public String toString() {
        String str = niceClassName(this.getClass().getSimpleName());
        
        // Append range
        str += " (" + minimum + " < n < " + maximum + ")\t";
        
        return str;
    }
    
    public boolean getScorePassFail(Review review, int index) throws CapException {
        double score = getScore(review, index);
        return passes(score);
    }
    
    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }
    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }
    
    public double getMinimum() {
        return minimum;
    }
    
    public double getMaximum() {
        return maximum;
    }
    
    public boolean passes(double score) {
        return (score >= minimum) && (score < maximum);
    }
    
    public boolean fails(double score) {
        return !passes(score);
    }
        
}
