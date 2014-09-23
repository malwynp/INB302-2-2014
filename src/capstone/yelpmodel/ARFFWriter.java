/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.yelpmodel;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class ARFFWriter {

    private JSONWrapper model;
    private String relation;
    private List<AttributeWriter> attribs;
    
    public ARFFWriter(String relation, JSONWrapper model, AttributeWriter[] attr) {
        this.model = model;
        attribs = new ArrayList<>();
        for (AttributeWriter aw : attr) attribs.add(aw);
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

    public void setModel(JSONWrapper model) {
        this.model = model;
    }
    public JSONWrapper getModel() {
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
        
        return str;
    }
    public String getData() {
        String str = "@DATA\n";
        
        for (JSONObject o : model.getArray()) {
            str += getItemHeader(o);
            str += getItemData(o);
            str += getItemFooter(o);
        }
        
        return str;
    }
    
    public String getItemHeader(JSONObject obj) {
        String str = "A New Review Begins--\n";
        return str;
    }
    public String getItemFooter(JSONObject obj) {
        String str = "";
        return str + "A Review End--\n";
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
