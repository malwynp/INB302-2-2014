/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.yelpmodel;

import capstone.CapException;
import capstone.testsuite.TestSuite;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class Review extends JSONWrapper {

    public Review(String src) {
        super(src);
    }
    public Review(File f) {
        super(f);
    }
    public Review(JSONObject[] arr) {
        super(arr);
    }
    
    public Review getReviewsForBusiness(String business_id) {
        if (business_id == null) return null;
        List<JSONObject> lobj = new ArrayList<>();
        
        for (int i = 0; i < size(); i++) {
            if (get(i).get("business_id").equals(business_id))
                lobj.add(get(i));
        }
        
        return new Review((JSONObject[]) lobj.toArray());
    }
    
    public Review trimByVotes(String voteType, long minimum) {
        if (voteType == null) return null;
        List<JSONObject> lobj = new ArrayList<>();
        
        for (int i = 0; i < size(); i++) {
            JSONObject vobj = ((JSONObject)(get(i).get("votes")));
            
            if (vobj == null || !vobj.containsKey(voteType)) continue;

            if ((long)(vobj.get(voteType)) >= minimum)
                lobj.add(get(i));
        }
        
        return new Review(lobj.toArray(new JSONObject[lobj.size()]));
    }
    
    public int countReviewsForBusiness(String business_id) {
        List<JSONObject> lobj = new ArrayList<>();
        int count = 0;
        
        for (int i = 0; i < size(); i++) {
            String this_business_id = (String)get(i).get("business_id");
            if (this_business_id.equals(business_id)) count++;
        }
        
        return count;
    }
    
    public Review trimByBusinessPopularity(long minimumReviews, Business bdb) {
        List<JSONObject> lobj = new ArrayList<>();
        
        for (int i = 0; i < size(); i++) {
            String business_id = (String) get(i).get("business_id");
            JSONObject business = bdb.getBusinessById(business_id);
            if (business == null) continue;
            if ((long)(business.get("review_count")) >= minimumReviews) {
                lobj.add(get(i));
            }
        }
        
        return new Review(lobj.toArray(new JSONObject[lobj.size()]));
    }
    
    public Review trimByBusinessPopularity(long minimumReviews) {
        List<JSONObject> lobj = new ArrayList<>();
        List<JSONObject> lobj2 = new ArrayList<>();
        HashMap<String, Integer> businesses = new HashMap<>();
        
        for (int i = 0; i < size(); i++) {
            String business_id = (String)get(i).get("business_id");
            if (!businesses.containsKey(business_id)) {
                businesses.put(business_id, 1);
            } else {
                businesses.put(business_id, businesses.get(business_id) + 1);
            }
            lobj.add(get(i));
        }
        
        for (int i = 0; i < lobj.size(); i++) {
            String business_id = (String) lobj.get(i).get("business_id");
            if (businesses.get(business_id) >= minimumReviews) {
                lobj2.add(get(i));
            }
        }
        
        return new Review(lobj2.toArray(new JSONObject[lobj2.size()]));
    }
    
    public Review trimByTestSuite(TestSuite suite) throws CapException {
        List <JSONObject> lobj = new ArrayList<>();
        boolean scores[] = suite.testAllRecordsPassFail(this);
        
        for (int i = 0; i < scores.length; i++) {
            if (scores[i]) lobj.add(get(i));
        }
        
        return new Review(lobj.toArray(new JSONObject[lobj.size()]));
    }

    public static String niceString(JSONObject o) {
        if (o == null) return null;
        String str = "[Review:" + o.get("review_id") + "]";
        str += "\n\t\"" + o.get("text") + "\"";
        str += "\n\t(business_id = " + o.get("business_id") + ")";
        str += "\n\t(user_id = " + o.get("user_id") + ")";
        str += "\n\t(type = " + o.get("type") + ")";
        str += "\n\t(stars = " + o.get("stars") + ")";
        str += "\n\t(votes[useful] = " + ((JSONObject) o.get("votes")).get("useful") + ")";
        str += "\n----------\n";
        
        return str;
    }

    public boolean contains(int index) {
        return index >= 0 && index < size();
    }
}
