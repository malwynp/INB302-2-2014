/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.model;

import capstone.gui.DataSetView;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author mark
 */
public class NanModel implements Model {
    
    public static final String REVIEW_BEGIN = "A New Review Begins--";
    public static final String REVIEW_END = "A Review End--";
    public static final String REVIEW_TEXT = "Review Text--";
    
    private static final String[] jsonAttributes = {
        "wholeVotes", "voteRatio", "stars", "author"
    };
    
    private final Business businesses;
    private final Review reviews;
    
    public NanModel(File src) throws Exception {

        long count = 0;
        
        try (FileInputStream fis = new FileInputStream(src);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)) {
            
            List<JSONObject> arr = new ArrayList<>();
            
            while (br.ready()) {
                
                String str = br.readLine();
                if (str.equalsIgnoreCase(REVIEW_BEGIN)) {
                    
                    JSONObject obj = new JSONObject();
                    for (int i = 0; i < jsonAttributes.length; i++) {
                        String s = jsonAttributes[i];
                        str = br.readLine();
                        System.out.println(s + "=" + str);
                        if (str.contains("/")) { // May confuse JSON parser
                            obj.put(s, str);
                        } else if (i == jsonAttributes.length-1) { // Author
                            obj.put(s, str);
                        } else {
                            obj.put(s, JSONValue.parse(str));
                        }
                    }
                    
                    while (br.ready()) {
                        str = br.readLine();
                        if (str.equalsIgnoreCase(REVIEW_TEXT)) break;
                    }
                    
                    List<String> reviewTextList = new ArrayList<>();
                    while (br.ready()) {
                        str = br.readLine();
                        if (str.equalsIgnoreCase(REVIEW_END)) break;
                        reviewTextList.add(str);
                    }
                    
                    String reviewText = "";
                    for (String s : reviewTextList) {
                        reviewText = reviewText + s + "\n";
                    }
                    
                    String wholeVotes = (String)(obj.get(jsonAttributes[0]));
                    String[] toks = wholeVotes.split("[/]");
                    int goodVotes = Integer.parseInt(toks[0]);
                    int allVotes = Integer.parseInt(toks[1]);
                    obj.put("goodVotes", goodVotes);
                    obj.put("allVotes", allVotes);
                    
                    JSONObject votes = new JSONObject();
                    votes.put("helpful", goodVotes);
                    votes.put("useful", goodVotes);
                    obj.put("votes", votes);
                    
                    obj.put("text", reviewText);
                    obj.put("originalOrder", count);
                    arr.add(obj);
                    
                    count++;
                }
                
            }
            
            reviews = new Review(arr.toArray(new JSONObject[arr.size()]));
            reviews.setHasBusinessData(false);
            businesses = new Business(new JSONObject[0]);
        }
        
    }

    @Override
    public Business getBusinesses() {
        return businesses;
    }

    @Override
    public Review getReviews() {
        return reviews;
    }
    
    public static void main(String args[]) throws Exception {
        
        File f = new File("5DIII.txtOriginalProcessReviews.txt");
        Model m = new NanModel(f);
        JFrame w = new JFrame("Derp");
        w.setPreferredSize(new Dimension(640, 640));
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DataSetView dsv = new DataSetView();
        dsv.setModel(m.getReviews());
        dsv.setKeys(jsonAttributes);
        w.setContentPane(dsv);
        w.pack();
        w.setVisible(true);
        
    }
    
}
