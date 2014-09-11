package capstone.yelpmodel;

import capstone.CapException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class JSONWrapper implements Serializable {
    public static final long serialVersionUID = -1L;
    
    protected boolean ignoreRecordLimit = false;
    
    JSONArray json = null;
    
    public JSONWrapper(String src) {
        preload();
        json = (JSONArray)JSONValue.parse(src);
        postload();
    }
    
    public JSONWrapper(JSONObject[] arr) {
        preload();
        json = new JSONArray();
        for (JSONObject o : arr) {
            json.add(o);
        }
        postload();
    }

    public JSONWrapper(File f) {
        preload();
        
        try {
        
            String src = "[";

            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            int count = 0;
            while (br.ready() && (count < capstone.Capstone.MAX_RECORDS || (ignoreRecordLimit))) {
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
        
        postload();
    }
    
    public int size() {
        return json != null ? json.size() : -1;
    }
    
    public JSONObject get(int index) {
        return (JSONObject)json.get(index);
    }
    
    public void preload() {
    }
    public void postload() {
    }
    
    public JSONObject[] getArray() {
        return (JSONObject[]) json.toArray(new JSONObject[json.size()]);
    }
    
    public JSONObject[] getSortedArray(String key, boolean order)
        throws CapException
    {
        List<JSONObject> list = new ArrayList();
        
        for (Object o : json) {
            list.add((JSONObject)o);
        }

        // GOD DAMN MERGE SORT DO NOT TOUCH AUGH SO CODEY //
        for (int i = 1; i <= list.size() / 2 + 1; i *= 2)
            for (int j = i; j < list.size(); j += 2 * i)
                merge(list, j - i, j, Math.min(j + i, list.size()), key, order);
        
        return list.toArray(new JSONObject[list.size()]);
    }
    
    // ITERATIVE MERGE SORT AUUUUGH RUN AWAY RUN AWAY //
    protected static final void merge
        (List<JSONObject> list, int start, int middle, int end, String key, boolean order) 

        throws CapException
    {
        
        if (list.isEmpty()) 
            throw new CapException("Attempting to sort an empty list");

        if (!list.get(0).containsKey(key)) 
            throw new CapException("List data does not contain search key '" + key + "'");

        if (!(list.get(0).get(key) instanceof Number))
            throw new CapException("List key '" + key + "' does not inherit Number, it is a "
                    + list.get(0).get(key).getClass().getSimpleName()
                    + ", "
                    + list.get(0).get(key).toString()
                    + "."
            );

        List<JSONObject> buffer = new ArrayList<>();
        for (int q = 0; q < end - start; q++) buffer.add(null);
            
        int l = 0, r = 0, i = 0;
        
        while (l < middle - start && r < end - middle) {
            JSONObject objA = list.get(start + l), objB = list.get(middle + r);

            if (objA.get(key) == null || objB.get(key) == null)
                throw new CapException("List data key points to null values");
            
            boolean isLong, swap; // Go to hell Java
            isLong = objA.get(key) instanceof Long;
            
            if (isLong) {
                long aVal = (long)objA.get(key);
                long bVal = (long)objB.get(key);
                swap = order ? (aVal > bVal) : (bVal > aVal);
            } else {
                double aVal = (double)objA.get(key);
                double bVal = (double)objB.get(key);
                swap = order ? (aVal > bVal) : (bVal > aVal);
            }
            
            buffer.set(i++, swap ? list.get(start + l++) : list.get(middle + r++));
        }
        
        while (r < end - middle) buffer.set(i++, list.get(middle + r++));
        
        while (l < middle - start) buffer.set(i++, list.get(start + l++));
        
        for (i = 0; i < buffer.size(); i++) {
            list.set(i + start, buffer.get(i));
        }
    }

}
