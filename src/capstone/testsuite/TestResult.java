/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class TestResult implements Serializable {
    
    protected class ResultRecord {
        JSONObject obj;
        ReviewTest test;
        double dVal;
        boolean bVal;
        
        public ResultRecord(JSONObject obj, ReviewTest test, double dVal, boolean bVal) {
            this.obj = obj;
            this.test = test;
            this.dVal = dVal;
            this.bVal = bVal;
        }
        
        public String toString() {
            String str = "";
            
            double scale = 0.001;
            
            str += test.toString() + " ";
            str += "Result: " + Math.round(dVal / scale) * scale + ", Pass: " + bVal;
            
            return str;
        }
    }
    
    private HashMap<JSONObject, List<ResultRecord>> results = new HashMap<>();

    public void store(JSONObject obj, ReviewTest test, double d, boolean b) {
        if (obj == null) return;
        if (!results.containsKey(obj)) {
            results.put(obj, new ArrayList<ResultRecord>());
        }
        results.get(obj).add(new ResultRecord(obj, test, d, b));
    }
    
    public String toString() {
        String str = "Test Results: (" + results.size() + ")\n";
        
        int i = 0;
        for (JSONObject obj : results.keySet()) {
            i++;
            String text = (String) obj.get("text");
            if (text.contains("\n")) text = text.substring(0, text.indexOf("\n")) + "...";
            if (text.length() > 80 - 3) text = text.substring(0, 80 - 3) + "...";
            
            str += i + "/" + results.size();
            if (obj.containsKey("business_name")) {
                str += "\tRE: " + obj.get("business_name");
            } else {
                str += "\tRE: [" + obj.get("business_id") + "]";
            }
            str += "\n\t\"" + text + "\"\n";
            str += "\n\tVoted 'useful' by users: " + ((JSONObject)obj.get("votes")).get("useful") + "\n";
            ResultRecord[] rec = results.get(obj).toArray(new ResultRecord[results.get(obj).size()]);
            for (ResultRecord r : rec) {
                str += "\t\t" + r.toString() + "\n";
            }
            str += "\n";
        }
        return str;
    }
    
}
