/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.yelpmodel;

import java.io.Serializable;

/**
 *
 * @author mark
 */
public interface Model extends Serializable {
    
    public Business getBusinesses();
    public Review getReviews();
    
}
