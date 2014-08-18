/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.yelpmodel;

import java.io.File;
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
    
    public JSONObject getBusinessById(String business_id) {
        
        for (int i = 0; i < size(); i++) {
            if (get(i).get("business_id").equals(business_id)) return get(i);
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

}
