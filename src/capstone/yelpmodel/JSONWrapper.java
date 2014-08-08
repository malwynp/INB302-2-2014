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
    
    public static final int MAX_RECORDS = capstone.Capstone.MAX_RECORDS;
    
    JSONArray json = null;
    
    public JSONWrapper(String src) {
        json = (JSONArray)JSONValue.parse(src);
    }

    public JSONWrapper(File f) {
        
        try {
        
            String src = "[";

            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            int count = 0;
            while (br.ready() && count < MAX_RECORDS) {
                String line = br.readLine();
                src += line + ",\n";
                count++;
            }
            
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
