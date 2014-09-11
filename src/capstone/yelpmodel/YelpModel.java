/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.yelpmodel;

import java.io.File;
import java.io.Serializable;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class YelpModel implements Serializable {
    public static final long serialVersionUID = -1L;
    
    Business business;
    Checkin checkin;
    Review review;
    User user;

    public void forget() {
        loader = null;
    }
    
    public interface YelpModelLoaderUI {
        public void update(String str);
    }
    
    private YelpModelLoaderUI loader = null;
    public YelpModel(String dir, YelpModelLoaderUI loader) {
        this.loader = loader;
        
        File businessFile = new File(dir + "yelp_training_set/yelp_training_set_business.json");
        File checkinFile = new File(dir + "yelp_training_set/yelp_training_set_checkin.json");
        File reviewFile = new File(dir + "yelp_training_set/yelp_training_set_review.json");
        File userFile = new File(dir + "yelp_training_set/yelp_training_set_user.json");
        
        loader.update("Reading businesses...");
        business = new Business(businessFile);

        loader.update("Reading checkins...");
        checkin = new Checkin(checkinFile);

        loader.update("Reading reviews...");
        review = new Review(reviewFile);

        loader.update("Syncing reviews to businesses...");
        review.storeBusinessNames(business);

        loader.update("Reading users...");
        user = new User(userFile);
    }
    
    public YelpModel(String dir) {
        
        File businessFile = new File(dir + "yelp_training_set/yelp_training_set_business.json");
        File checkinFile = new File(dir + "yelp_training_set/yelp_training_set_checkin.json");
        File reviewFile = new File(dir + "yelp_training_set/yelp_training_set_review.json");
        File userFile = new File(dir + "yelp_training_set/yelp_training_set_user.json");
        
        System.out.println("Reading businesses...");
        business = new Business(businessFile);
        System.out.println("...done.");

        System.out.println("Reading checkins...");
        checkin = new Checkin(checkinFile);
        System.out.println("...done.");

        System.out.println("Reading reviews...");
        review = new Review(reviewFile);
        System.out.println("...done.");

        System.out.println("Syncing reviews to businesses...");
        review.storeBusinessNames(business);
        System.out.println("...done.");

        System.out.println("Reading users...");
        user = new User(userFile);
        System.out.println("...done.");
        
    }
    
    public String toString() {
        String str = "YelpModel:\n";
        
        str += "\tReviews:" + review.size() + "\n";
        str += "\tReview fields: " + ((JSONObject)(review.json.get(0))).keySet() + "\n";

        str += "\tBusinesses:" + business.size() + "\n";
        str += "\tBusiness fields: " + ((JSONObject)(business.json.get(0))).keySet() + "\n";
        
        str += "\tCheckins:" + checkin.size() + "\n";
        str += "\tCheckin fields: " + ((JSONObject)(checkin.json.get(0))).keySet() + "\n";
        
        str += "\tUsers:" + user.size() + "\n";
        str += "\tUser fields: " + ((JSONObject)(user.json.get(0))).keySet() + "\n";
        
        return str;
    }
    
    public Business getBusinesses() {
        return business;
    }
    public User getUsers() {
        return user;
    }
    public Checkin getCheckin() {
        return checkin;
    }
    public Review getReviews() {
        return review;
    }
    
}
 
