/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.yelpmodel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class Business extends JSONWrapper {
    
    public Business(String src) {
        super(src);
    }
    public Business(File f) {
        super(f);
    }
    public Business(JSONObject arr[]) {
        super(arr);
    }
    
    public JSONObject getBusinessById(String business_id) {
        if (businessIndex.containsKey(business_id)) {
            return (JSONObject) json.get(businessIndex.get(business_id));
        }
        return null;
    }
    
    public static String niceString(JSONObject o) {
        if (o == null) return null;
        String str = "[Business:" + o.get("business_id") + "]";
        str += "\n\t(review_count = " + o.get("review_count") + ")";
        str += "\n\t(stars = " + o.get("stars") + ")";
        str += "\n\t(type = " + o.get("type") + ")";
        str += "\n\t(name = " + o.get("name") + ")";
        str += "\n\t(categories = " + o.get("categories") + ")";
        str += "\n----------\n";
        return str;
    }
    
    public Business trimByCategory(String categories[]) {
        List<JSONObject> list = new ArrayList<>();
        
        for (Object o : json) {
            JSONObject obj = (JSONObject) o;
            boolean addToList = false;
            for (String c : categories) {
                JSONArray bCat = (JSONArray)obj.get("categories");
                for (Object bc : bCat) {
                    if (((String) bc).equalsIgnoreCase(c)) {
                        addToList = true;
                    }
                }
            }
            if (addToList) {
                list.add(obj);
            }
        }
        
        return new Business(list.toArray(new JSONObject[list.size()]));
    }
    
    protected HashMap<String, Integer> businessIndex;
    @Override
    public void postload() {
        businessIndex = new HashMap<>();
        
        int index = 0;
        for (Object obj : json) {
            JSONObject o = (JSONObject)obj;
            String busId = (String) o.get("business_id");
            businessIndex.put(busId, index);
            index++;
        }
    }
    
    @Override
    public void preload() {
        ignoreRecordLimit = true;
    }
    
}
