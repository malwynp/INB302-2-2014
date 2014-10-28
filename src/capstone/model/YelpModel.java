/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.model;

import java.io.File;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class YelpModel implements Model {
    public static final long serialVersionUID = -1L;
    
    Business business;
    Review review;

    public void forget() {
        loader = null;
    }
    public interface YelpModelLoaderUI {
        public void update(String str);
    }
    
    private YelpModelLoaderUI loader = null;
    public YelpModel(String dir, YelpModelLoaderUI loader) {
        this.loader = loader;
        
        File businessFile = new File(dir + "/yelp_training_set_business.json");
//        File checkinFile = new File(dir + "yelp_training_set_checkin.json");
        File reviewFile = new File(dir + "/yelp_training_set_review.json");
//        File userFile = new File(dir + "yelp_training_set_user.json");
        
        loader.update("Reading businesses...");
        business = new Business(businessFile);

//        loader.update("Reading checkins...");
//        checkin = new Checkin(checkinFile);

        loader.update("Reading reviews...");
        review = new Review(reviewFile);

        loader.update("Syncing reviews to businesses...");
        review.storeBusinessNames(business);

//        loader.update("Reading users...");
//        user = new User(userFile);
    }
    
    public YelpModel(String dir) {
        
        File businessFile = new File(dir + "yelp_training_set/yelp_training_set_business.json");
        File checkinFile = new File(dir + "yelp_training_set/yelp_training_set_checkin.json");
        File reviewFile = new File(dir + "yelp_training_set/yelp_training_set_review.json");
        File userFile = new File(dir + "yelp_training_set/yelp_training_set_user.json");
        
        System.out.println("Reading businesses...");
        business = new Business(businessFile);
        System.out.println("...done.");

        System.out.println("Reading reviews...");
        review = new Review(reviewFile, "originalOrder");
        System.out.println("...done.");

        System.out.println("Syncing reviews to businesses...");
        review.storeBusinessNames(business);
        System.out.println("...done.");
        
    }
    
    public String toString() {
        String str = "YelpModel:\n";
        
        str += "\tReviews:" + review.size() + "\n";
        str += "\tReview fields: " + ((JSONObject)(review.json.get(0))).keySet() + "\n";

        str += "\tBusinesses:" + business.size() + "\n";
        str += "\tBusiness fields: " + ((JSONObject)(business.json.get(0))).keySet() + "\n";
        
        return str;
    }
    
    public Business getBusinesses() {
        return business;
    }
    public Review getReviews() {
        return review;
    }
    
}
 
