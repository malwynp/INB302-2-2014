/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.model;

import capstone.testsuite.ReviewTest;
import capstone.testsuite.TestResult;
import capstone.testsuite.TestResult.ResultRecord;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public final class ARFFWriter {

    private TestResult model;
    private String relation;
    private List<AttributeWriter> attribs;
    
    public ARFFWriter(String relation, TestResult model, AttributeWriter[] attr) {
        this.model = model;
        attribs = new ArrayList<>();
        for (AttributeWriter aw : attr) attribs.add(aw);
    }
    public ARFFWriter(String relation, final TestResult model) {
        this.model = model;
        attribs = new ArrayList<>();
    /*    addAttribute(new AttributeWriter("text", "String") {
            @Override
            public String getAttributeFor(JSONObject obj) {
                String str = (String)obj.get("text");
                str = str.replace("\n", "<br/>");
                str = str.replace("\"", "'");
                return "\"" + str + "\"";
            }
        }); */
        for (final ReviewTest rt : model.getTests()) {
            addAttribute(new AttributeWriter(ReviewTest.niceClassName(rt.getClass().getSimpleName(), false), "Numeric") {

                @Override
                public String getAttributeFor(JSONObject obj) {
                    for (ResultRecord rec : model.getRecord(obj))
                        if (rec.getTest().equals(rt))
                            return "" + rec.getDouble();
                    
                    return "?";
                }
            });
        }
    }

    public void addAttribute(AttributeWriter aw) {
        if (aw == null || attribs.contains(aw)) return;
        attribs.add(aw);
    }
    public void removeAttribute(AttributeWriter aw) {
        if (aw == null || !attribs.contains(aw)) return;
        attribs.remove(aw);
    }

    public void setRelation(String r) {
        relation = r;
    }
    public String getRelation() {
        return relation;
    }

    public void setModel(TestResult model) {
        this.model = model;
    }
    public TestResult getModel() {
        return model;
    }
    
    public String comment (String cmt) {
        return "% " + cmt + "\n";
    }
    public String attribute(String attrib, String typeStr) {
        return "@ATTRIBUTE\t" + attrib + "\t" + typeStr + "\n";
    }
    public String nominal(String vars[]) {
        String str = "{";
        for (int i = 0; i < vars.length; i++)
            str += vars[i] + ((i == vars.length - 1) ? "" : ",");
        return str + "}";
    }
    
    public String getHeader() {
        String str = "";
        
        str += comment("Capstone 2014 User Review Analysis");
        str += "@RELATION\t" + getRelation() + "\n";
        
        str += "\n\n" + getAttributes();
        
        return str;
    }
    public String getData() {
        String str = "@DATA\n";
        
        for (JSONObject o : model.getRecordObjects()) {
            str += getItemHeader(o);
            str += getItemData(o);
            str += getItemFooter(o);
        }
        
        return str;
    }
    
    public String getItemHeader(JSONObject obj) {
        String str = comment("A New Review Begins--");
        return "";
    }
    public String getItemFooter(JSONObject obj) {
        String str = comment("A Review End--");
        return "";
    }
    public String getItemData(JSONObject obj) {
        String str = "";
        
        for (int i = 0; i < attribs.size(); i++) {
            AttributeWriter aw = attribs.get(i);
            str += aw.getAttributeFor(obj) + ((i < attribs.size() - 1) ? "," : "");
        }
        str += "\n";
        
        return str;
    }
    
    public String getAttributes() {
        String str = "";
        
        for (AttributeWriter aw : attribs) {
            str += aw.getAttributeDefinition();
        }
        
        return str;
    }
    
    public void writeToStream(OutputStream os) throws Exception {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        
        osw.write(getHeader() + "\n");
        osw.write(getData() + "\n");
        
        osw.close();
    }
    
}
