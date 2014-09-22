/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone;

import capstone.testsuite.ReviewTest;
import capstone.testsuite.S2_UpperCasePercentageTest;
import capstone.testsuite.S4_WordCountTest;
import capstone.testsuite.TestResult;
import capstone.testsuite.TestSuite;
import capstone.yelpmodel.Business;
import capstone.yelpmodel.Review;
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
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 *
 * @author mark
 */
public class Capstone {
    
    public static final int MAX_RECORDS = 500000;
    public static final int OUTPUT_RECORD_FREQUENCY = 10000;
    public static final boolean GUI_DEBUG = false;

    protected static YelpModel model;
    
    public static void main(String[] args) throws Exception {

        try {
            for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels())
                if (lafi.getName().contains("GTK"))
                    UIManager.setLookAndFeel(lafi.getClassName());
        } catch (Exception e) {
        }
        
        File f = new File("capstone_yelp_2p31.ser");

        try {
            System.out.println("Reading from serialized object file...");
            model = null;
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            model = (YelpModel) (ois.readObject());
            System.out.println("...done.\n");
        } catch (Exception e) {
            System.out.println("Reading from file...");
            model = new YelpModel("/home/mark/Downloads/yelp/");
            System.out.println("...done.\n");

            System.out.println("Saving serialized object file...");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(model);
            oos.close();
            fos.close();
            System.out.println("...done.\n");
        }
        
        System.out.println(model);
        DefaultListModel<String> listModel = new DefaultListModel<>();

        Review set = model.getReviews();
        Business bus = model.getBusinesses();

        System.out.println("Original set size: " +set.size());

        System.out.println("Trimming businesses by category: " + "Diners");
        bus = bus.trimByCategory(new String[]{"diners"});
        System.out.println("Trimming reviews by business set:");
        set = set.trimByBusinessSet(bus);
        System.out.println("Set size: " +set.size());

        System.out.println("Trimming by useful votes:");
        set = set.trimByVotes("useful", 1L);
        System.out.println("set size: " +set.size());
        
        TestSuite suite = new TestSuite(new ReviewTest[] {
            new S2_UpperCasePercentageTest(70, 100), 
            new S4_WordCountTest(10, 500),
//            new TestInverter(new TestInverter(new S4_WordCountTest(10, 500))),
        });
//        suite = TestSuite.getDefaultSuite();
        
        TestResult tr = suite.testAndStoreAllRecords(set);
        
        System.out.println(tr);
        
        System.exit(0);
        
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
