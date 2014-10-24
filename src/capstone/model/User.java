/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.model;

import java.io.File;

/**
 *
 * @author mark
 */
public class User extends JSONWrapper {

    public User(String src) {
        super(src);
    }
    public User(File f) {
        super(f);
    }
    
}
