/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone;

import capstone.yelpmodel.YelpModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author mark
 */
public class Capstone {
    
    private static final int MAX_RECORDS = 12800;

    public static void main(String[] args) throws Exception {
        
        YelpModel model;// = new YelpModel("/home/mark/Downloads/yelp/");
        File f = new File("capstone_yelp_db2.ser");
        
     //   FileOutputStream fos = new FileOutputStream(f);
     //   ObjectOutputStream oos = new ObjectOutputStream(fos);
     //   oos.writeObject(model);
     //   oos.close();
     //   fos.close();

        model = null;
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        model = (YelpModel)ois.readObject();
        ois.close();
        fis.close();
        
        System.out.println(model);
                
    }
    
}
