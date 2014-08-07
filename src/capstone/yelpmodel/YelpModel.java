/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.yelpmodel;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author mark
 */
public class YelpModel implements Serializable {
    
    Business business;
    Checkin checkin;
    Review review;
    User user;
    
    public YelpModel(String dir) {
        
        File businessFile = new File(dir + "yelp_training_set/yelp_training_set_business.json");
        File checkinFile = new File(dir + "yelp_training_set/yelp_training_set_checkin.json");
        File reviewFile = new File(dir + "yelp_training_set/yelp_training_set_review.json");
        File userFile = new File(dir + "yelp_training_set/yelp_training_set_user.json");
        
        business = new Business(businessFile);
        checkin = new Checkin(checkinFile);
        review = new Review(reviewFile);
        user = new User(userFile);
        
    }
    
    public String toString() {
        String str = "YelpModel:\n";
        
        str += "\tBusinesses:" + business.size() + "\n";
        str += "\tCheckins:" + checkin.size() + "\n";
        str += "\tReviews:" + review.size() + "\n";
        str += "\tUsers:" + user.size() + "\n";
        
        return str;
    }
    
}
