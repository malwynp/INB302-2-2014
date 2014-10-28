/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.testsuite;

//Methods for system input & output through datastreams
import capstone.CapException;
import capstone.model.JSONWrapper;
import java.io.Serializable;

//<!-- to do
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

//<!-- to do
import org.json.simple.JSONObject;

/**
 *
 */
public class TestResult implements Serializable, Cloneable {

    private TestResult(TestResult b) {
        this.results = new HashMap<>();
        for (JSONObject o : b.getRecordObjects()) {
            ArrayList<ResultRecord> newlst = new ArrayList<>();
            List<ResultRecord> oldlst = b.results.get(o);
            for (ResultRecord r : oldlst) {
                if (r == null) {
                    System.out.println("Null record result!");
                    continue;
                }
                try {
                    ResultRecord newRec = new ResultRecord(r);
                    newlst.add(newRec);
                } catch (Exception e) {
                    System.out.println("Error in duplicating record list");
                }
            }
            if (results.get(o) == null) System.out.println("Warning: newlst is null in TestResult duplication");
            if (newlst.isEmpty()) System.out.println("Warning: newlst is empty in TestResult duplication");
            results.put(o, newlst);
        }
    }

    public TestResult() {
        this.results = new HashMap<>();
    }

    public HashMap<JSONObject, List<ResultRecord>> getAllRecords() {
        return this.results;
    }
    
    public JSONObject[] getRecordObjects() {
        Set keySet = results.keySet();
        JSONObject[] objs = (JSONObject[]) keySet.toArray(new JSONObject[keySet.size()]);
        return objs;
    }
    
    /**
     * Creates an array containing the test suite tests
     * @return array of test suite tests
     */
    public ReviewTest[] getTests() {
        if (size() == 0) return new ReviewTest[] {};
        List<ReviewTest> rt = new ArrayList<>();
        for (ResultRecord rec : getRecord(0)) {
            rt.add(rec.getTest());
        }
        return rt.toArray(new ReviewTest[rt.size()]);
    }

    public long getMatchingIndex(JSONObject obj) {
        long index = 0;
        if (obj == null) return -1;
        for (JSONObject o : getRecordObjects()) {
            if (o == obj || o.equals(obj) || o.get("originalOrder").equals(obj.get("originalOrder"))) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public JSONObject findEquivalentReview(JSONObject needle) {
        if (needle == null) return null;
        for (JSONObject obj : getRecordObjects()) {
            if (obj.get("text").equals(needle.get("text"))) return obj;
        }
        return null;
    }
    
    /**
     * <!-- to do
     */
    public final static class ResultRecord {
        protected JSONObject obj;
        protected ReviewTest test;
        protected double dVal;
        protected boolean bVal;

        private ResultRecord(ResultRecord r) throws Exception {
            obj = (r.obj);
            test = r.test.getClass().newInstance();
            dVal = r.dVal;
            bVal = r.bVal;
        }
        public JSONObject getObject() {
            return obj;
        }
        public boolean getBoolean() {
            return bVal;
        }
        public double getDouble() {
            return dVal;
        }
        public ReviewTest getTest() {
            return test;
        }
        
        public ResultRecord(JSONObject obj, ReviewTest test, double dVal, boolean bVal) {
            this.obj = obj;
            try {
                this.test = test.getClass().newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.dVal = dVal;
            this.bVal = bVal;
        }
        
        @Override
        public String toString() {
            String str = "";
            
            double scale = 0.001;
            
            str += test.toString() + " ";
            str += "Result: " + Math.round(dVal / scale) * scale;
            
            return str;
        }
    }

    private HashMap<JSONObject, List<ResultRecord>> results;
    
    /**
     * <!-- to do
     * @param obj
     * @param test
     * @param d
     * @param b 
     */
    public void store(JSONObject obj, ReviewTest test, double d, boolean b) {
        if (obj == null) return;
        if (!results.containsKey(obj)) {
            results.put(obj, new ArrayList<ResultRecord>());
        }
        results.get(obj).add(new ResultRecord(obj, test, d, b));
    }
    
    @Override
    public String toString() {
        String str = "Test Results: (" + results.size() + ")\n";
        
        int i = 0;
        //Formats test results <!-- to do
        for (JSONObject obj : getRecordObjects()) {
            i++;
            String text = (String) obj.get("text");
            if (text.contains("\n")) text = text.substring(0, text.indexOf("\n")) + "...";
            if (text.length() > 80 - 3) text = text.substring(0, 80 - 3) + "...";
            
            str += "[" + i + "/" + results.size() + "]:";
            str += "\t\"" + text + "\"; ";
            if (!results.containsKey(obj)) {
                obj = findEquivalentReview(obj);
            }
            List<ResultRecord> rec = results.get(obj);
            if (rec == null) {
                System.out.println("Null record set for object:" + obj.get("originalOrder"));
            } else {
                for (ResultRecord r : rec) {
                    if (r == null) continue;
                    str += r.getTest().getClass().getSimpleName() + "=" + r.toString() + ",";
                }
            }
            str += "\n";
        }
        return str;
    }
    
    public int size() {
        return results.size();
    }
    public int getTestCount() {
        if (size() == 0) return 0;
        return getTests().length;
    }
    public ResultRecord[] getRecord(long index) {
        List<ResultRecord> rec = new ArrayList<>();
        JSONObject keys[] = results.keySet().toArray(new JSONObject[results.size()]);
        if (results.get(keys[(int)index]) == null) return new ResultRecord[0];
        
            for (ResultRecord r : results.get(keys[(int)index])) {
                try {
                    rec.add(new ResultRecord(r));
                } catch (Exception e) {
                }
            }

        return rec.toArray(new ResultRecord[rec.size()]);
    }
    public ResultRecord[] getRecord(JSONObject obj) {
        List<ResultRecord> rec = results.get(obj);
        if (rec == null) return null;
        for (ResultRecord r : rec) {
            if (!r.getObject().equals(obj)) return null;
        }
        return rec.toArray(new ResultRecord[rec.size()]);
    }
    
    public TestResult join(TestResult b) throws Exception {
        TestResult a = new TestResult(this);

        boolean test_a = true;
        for (ReviewTest r1 : a.getTests()) {
            boolean test_b = false;
            for (ReviewTest r2 : b.getTests()) {
                if (r2 == r1) test_b = true;
            }
            test_a = !test_b;
        }

        if (a.getTestCount() != b.getTestCount()) 
            throw new CapException("TestResult join requires the same tests be run");
        
        for (JSONObject o : b.getRecordObjects()) {
            a.results.put(o, b.results.get(o));
        }
        
        return a;
    }
    
}
