/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.yelpmodel;

import java.io.File;

/**
 *
 * @author mark
 */
public class Review extends JSONWrapper {

    public Review(String src) {
        super(src);
    }
    public Review(File f) {
        super(f);
    }

    
}
