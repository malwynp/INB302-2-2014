/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

//Method for displaying custom exception message
import capstone.CapException;

//Methods for loading in yelp data
import capstone.yelpmodel.Review;

/**
 * Class contains methods related to interacting with the review test suite
 */
public abstract class ReviewTest {
    
    public ReviewTest() {
    }
    
    public abstract double getScore(Review review, int index) throws CapException;

    public static String niceClassName(String str) {
        return niceClassName(str, true);
    }
    
    /**
     * Refines test suite's test names, removes '_' prefix and the inclusion of the word 'test'
     * @param str
     * @param withPrefix
     * @return 
     */
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
        return str;
    }
        
}
