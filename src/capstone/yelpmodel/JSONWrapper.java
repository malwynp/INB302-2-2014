package capstone.yelpmodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JSONWrapper implements Serializable {
    
    JSONArray json = null;
    
    public JSONWrapper(String src) {
        json = (JSONArray)JSONValue.parse(src);
    }
    
    public JSONWrapper(JSONObject[] arr) {
        json = new JSONArray();
        for (JSONObject o : arr) {
            json.add(o);
        }
    }

    public JSONWrapper(File f) {
        
        try {
        
            String src = "[";

            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            int count = 0;
            while (br.ready() && count < capstone.Capstone.MAX_RECORDS) {
                String line = br.readLine();
                src += line + ",\n";
                count++;
                if (count % capstone.Capstone.OUTPUT_RECORD_FREQUENCY == 0)
                    System.out.println("\t[ read: " + count + " ]");
            }
            System.out.print(" (records: " + count + ") ");
            
            src += "]";

            json = (JSONArray)JSONValue.parse(src);
        }
        
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public int size() {
        return json != null ? json.size() : -1;
    }
    
    public JSONObject get(int index) {
        return (JSONObject)json.get(index);
    }

}
