package capstone.model;

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
        
            json = new JSONArray();
            
            //String src = "[";

            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            int count = 0;
            while (br.ready() && (count < capstone.Capstone.MAX_RECORDS || (ignoreRecordLimit))) {
                String line = br.readLine();
                //src += line + ",\n";
                JSONObject obj = (JSONObject) JSONValue.parse(line);
                json.add(obj);
                
                count++;
                if (count % capstone.Capstone.OUTPUT_RECORD_FREQUENCY == 0)
                    System.out.println("\t[ read: " + count + " ]");
            }
            System.out.print(" (records: " + count + ") ");
            
            //src += "]";

            //json = (JSONArray)JSONValue.parse(src);
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

        JSONObject one = list.get(0);
        Object oneVar = getKeyPath(one, key);
   
        if (oneVar == null) 
            throw new CapException("List data does not contain search key '" + key + "'");

        if (!(oneVar instanceof Number))
            throw new CapException("List key '" + key + "' does not inherit Number, it is a "
                    + oneVar.getClass().getSimpleName()
                    + ", "
                    + oneVar.toString()
                    + "."
            );

        List<JSONObject> buffer = new ArrayList<>();
        for (int q = 0; q < end - start; q++) buffer.add(null);
            
        int l = 0, r = 0, i = 0;
        
        while (l < middle - start && r < end - middle) {
            JSONObject objA = list.get(start + l), objB = list.get(middle + r);

            if (getKeyPath(objA, key) == null || getKeyPath(objB, key) == null)
                throw new CapException("List data key points to null values");
            
            boolean isLong, swap; // Go to hell Java
            isLong = getKeyPath(objA, key) instanceof Long;
            
            if (isLong) {
                long aVal = (long)getKeyPath(objA, key);
                long bVal = (long)getKeyPath(objB, key);
                swap = order ? (aVal > bVal) : (bVal > aVal);
            } else {
                double aVal = (double)((int)(getKeyPath(objA, key)));
                double bVal = (double)((int)(getKeyPath(objB, key)));
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
    
    public static final Object getKeyPath(JSONObject o, String k) {
        if (o == null || k == null) return null;

        if (!k.contains(".")) {
            if (o.containsKey(k)) return o.get(k);
            return null;
        }
        
        Object var;
        
        if (k.contains(".")) {
            String[] path = k.split("[.]");
            var = o;
            for (String p : path) {
                var = ((JSONObject)var).get(p);
            }
        } else {
            var = o.get(k);
        }
        
        return var;
    }

}
