/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.yelpmodel;

import capstone.CapException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class NanWriter {
    
    public static final String REVIEW_BEGIN = "A New Review Begins--";
    public static final String REVIEW_TEXT = "Review Text--";
    public static final String REVIEW_END = "A Review End--";
    
    private JSONWrapper model = null;
    public void setModel(JSONWrapper m) {
        this.model = m;
    } 
    public JSONWrapper getModel() {
        return model;
    }
    
    public NanWriter(JSONWrapper data) {
        setModel(data);
    }

    public void writeToStream(String header, FileOutputStream fos) throws Exception {
        if (model == null) throw new CapException("NanWriter model cannot be null when writing");
        
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        
        bw.write(header); bw.newLine();
        
        for (JSONObject obj : model.getArray()) {
            
            bw.write(REVIEW_BEGIN);
            bw.newLine();

            writeReviewMetadata(bw, obj, model);

            bw.write(REVIEW_TEXT);
            bw.newLine();
            
            writeReviewText(bw, obj, model);
            
            bw.write(REVIEW_END);
            bw.newLine();
            
        }
        
        bw.close();
        osw.close();
    }
    
    public void writeReviewText(BufferedWriter bw, JSONObject obj, JSONWrapper model) throws IOException {
        if (bw == null || obj == null || model == null) return;
        String text = (String)obj.get("text");
        String lines[] = text.split("[\n]");
        for (String s : lines) {
            bw.write(s);
            bw.newLine();
        }
    }

    public void writeReviewMetadata(BufferedWriter bw, JSONObject obj, JSONWrapper model) throws IOException {
        if (bw == null || obj == null || model == null) return;
        String voteString = (Integer)(obj.get("goodVotes")) + "/" + (Integer)(obj.get("allVotes"));
        String voteRatio = (obj.get("voteRatio")).toString();
        String stars = (obj.get("stars")).toString();

        if (obj.get("author") == null) {
            System.out.println("FUCKERY:" + obj.get("author"));
        }
        String user = (obj.get("author")).toString();
        
        bw.write(voteString); bw.newLine();
        bw.write(voteRatio); bw.newLine();
        bw.write(stars); bw.newLine();
        bw.write(user); bw.newLine();
    }
}
