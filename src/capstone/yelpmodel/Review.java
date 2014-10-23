/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.yelpmodel;

import capstone.CapException;
import capstone.testsuite.TestResult;
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
    
    protected void storeBusinessNames(Business business) {
        for (int i = 0; i < json.size(); i++) {
            JSONObject obj = (JSONObject) json.get(i);
            if (!obj.containsKey("business_id")) continue;
            
            String busId = (String) obj.get("business_id");
            JSONObject bus = business.getBusinessById(busId);
            
            String busName = (String) bus.get("name");
            
            obj.put("business_name", busName);
        }
    }
    
    public Review getReviewsForBusiness(String business_id) {
        if (business_id == null) return null;
        List<JSONObject> lobj = new ArrayList<>();
        
        for (int i = 0; i < size(); i++) {
            if (get(i).get("business_id").equals(business_id))
                lobj.add(get(i));
        }
        
        return new Review(lobj.toArray(new JSONObject[lobj.size()]));
    }
    
    public long getMinimumVotesAsLong(String voteType) {
        long min = 1;
        for (int i = 0; i < json.size(); i++) {
            JSONObject vobj = ((JSONObject)(get(i).get("votes")));
            if (vobj == null || !vobj.containsKey(voteType)) continue;
            
            if ((long)vobj.get(voteType) < min) min = (long)vobj.get(voteType);
        }
        return min;
    }
    public long getMaximumVotesAsLong(String voteType) {
        long max = 1;
        for (int i = 0; i < json.size(); i++) {
            JSONObject vobj = ((JSONObject)(get(i).get("votes")));
            if (vobj == null || !vobj.containsKey(voteType)) continue;
            
            if ((long)vobj.get(voteType) > max) max = (long)vobj.get(voteType);
        }
        return max;
    }
    
    public Review trimByVoteDouble(String voteType, double minimum, double scaleMin, double scaleMax) {
        long minVotesL = (long) ((scaleMax - scaleMin) * minimum + scaleMin);
        return trimByVotes(voteType, minVotesL);
    }
    
    public Review trimByVotes(String voteType, long minimum) {
        if (voteType == null) return null;
        List<JSONObject> lobj = new ArrayList<>();
        
        for (int i = 0; i < size(); i++) {
            JSONObject vobj = ((JSONObject)(get(i).get("votes")));

            if (minimum < 0) {
                if (vobj == null || !vobj.containsKey(voteType) || ((long)(vobj.get(voteType)) == 0)) {
                    lobj.add(get(i));
                }
            } else {
                if (vobj == null || !vobj.containsKey(voteType)) continue;
                if ((long)(vobj.get(voteType)) >= minimum) {
                    lobj.add(get(i));
                }
            }
        }
        
        return new Review(lobj.toArray(new JSONObject[lobj.size()]));
    }

    public Review trimByVotes(String voteType, double minimum) {
        if (voteType == null) return null;
        List<JSONObject> lobj = new ArrayList<>();
        
        for (int i = 0; i < size(); i++) {
            double vr = ((Double)(get(i).get("voteRatio")));

            if (vr >= minimum) lobj.add(get(i));
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
        TestResult results = suite.testAndStoreAllRecords(this);
        
        // !!!
        
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

    public Review trimByBusinessSet(Business business) {
        List <JSONObject> lobj = new ArrayList<>();
        
        for (Object obj : json) {
            JSONObject o = (JSONObject) obj;
            if (business.getBusinessById((String) o.get("business_id")) != null) {
                lobj.add(o);
            }
        }
        
        return new Review(lobj.toArray(new JSONObject[lobj.size()]));
    }
    
    protected HashMap<String, Integer> reviewIndex;
    @Override
    public void postload() {
        reviewIndex = new HashMap<>();
        
        int index = 0;
        for (Object obj : json) {
            JSONObject o = (JSONObject)obj;
            String reviewId = (String)o.get("review_id");
            if (reviewId != null) reviewIndex.put(reviewId, index);
            index++;
        }
    }

    private String usefulKey = "useful";
    public String getUsefulKey() {
        return usefulKey;
    }
    public void setUsefulKey(String key) {
        usefulKey = key;
    }

    private boolean isNanModel = true;
    public boolean hasBusinessData() {
        return isNanModel;
    }
    public void setHasBusinessData(boolean b) {
        isNanModel = b;
    }

}
