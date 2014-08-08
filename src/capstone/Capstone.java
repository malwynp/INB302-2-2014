/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone;

import capstone.yelpmodel.YelpModel;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author mark
 */
public class Capstone {
    
    public static final int MAX_RECORDS = 64;

    public static void main(String[] args) throws Exception {
        
        System.out.println("Herp");
        final YelpModel model = new YelpModel("/home/mark/Downloads/yelp/");
        System.out.println("Derp");
/*        File f = new File("capstone_yelp_db2.ser");

        FileOutputStream fos = new FileOutputStream(f);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(model);
        oos.close();
        fos.close();

        model = null;
        FileInputStream fis = new FileInputStream(f);
        ObjectInputStream ois = new ObjectInputStream(fis);
        model = (YelpModel)ois.readObject();
        ois.close();
        fis.close(); */
        
        System.out.println(model);
        DefaultListModel<String> listModel = new DefaultListModel<String>();

        for (int i = 0; i < MAX_RECORDS; i++) {
           String str = i + ":" + model.getReviews().get(i).get("text").toString();
           str.substring(0, 8);
           str = str.replace("\n", "<br/>");
//           System.out.println("[" + str + "]");
           listModel.addElement(str);
        }
        
        JFrame frame = new JFrame("GUI");
        frame.setPreferredSize(new Dimension(640, 480));
        final JList pane = new JList(listModel);
        pane.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() < 2) return;
                int index = pane.getSelectedIndex();
                if (index >= 0 && index < MAX_RECORDS) {
                    String str = model.getReviews().get(index).toString();
                    str = str.replace(",", ",\n");
                    JOptionPane.showMessageDialog(null, str);
                }
            }
            public void mousePressed(MouseEvent me) {
            }
            public void mouseReleased(MouseEvent me) {
            }
            public void mouseEntered(MouseEvent me) {
            }
            public void mouseExited(MouseEvent me) {
            }
        });
        JScrollPane sp;
        frame.setContentPane(sp = new JScrollPane(pane));
        sp.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        
    //    System.exit(0);
                
    }
    
}
