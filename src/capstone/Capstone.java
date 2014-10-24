/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone;

//Methods for retreiving test suite results
import capstone.testsuite.TestResult;

//Methods for loading test suites
import capstone.testsuite.TestSuite;

//Methods for loading in yelp data
import capstone.model.Business;
import capstone.model.Review;
import capstone.model.YelpModel;

//Methods for controling height and width of a abstract window toolkit component
import java.awt.Dimension;

//Methods for user interaction with abstract window toolkit components<!-- redundent
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Methods for system input & output through datastreams
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//Methods for creating GUI
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Overarching class for creating the system.
 * 
 */
public class Capstone {
    
    public static final int MAX_RECORDS = 500000;//<!-- redundent
    public static final int OUTPUT_RECORD_FREQUENCY = 10000;//<!-- redundent?
    public static final boolean GUI_DEBUG = false;//<!-- redundent

    protected static YelpModel model;
    
    /**
     * Loads in the GUI..
     * 
     * @param args array containing parameters from the compiler
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        
        //Try load in ___ (GTK) look and feel
        try {
            //Loop through look and feel options
            for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels())
                if (lafi.getName().contains("GTK"))
                    //Set GTK look and feel
                    UIManager.setLookAndFeel(lafi.getClassName());
        } catch (Exception e) {//<!-- redundent
        }
        
        //Create new serilization file
        File f = new File("capstone_yelp_2p31.ser");
        
        //Read in yelp model to serilization file
        try {
            System.out.println("Reading from serialized object file...");
            model = null;
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            model = (YelpModel) (ois.readObject());
            System.out.println("...done.\n");
        } catch (Exception e) {//<!-- redundent
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
        
        //<!-- why print out model here
        System.out.println(model);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        //Retreive review information from the dataset
        Review set = model.getReviews();
        //Retreive bussiness information from the dataset
        Business bus = model.getBusinesses();

        //<!-- aren't the print outs here redundant
        System.out.println("Original set size: " +set.size());
        System.out.println("Trimming businesses by category: " + "Diners");
        bus = bus.trimByCategory(new String[]{"diners"});
        System.out.println("Trimming reviews by business set:");
        set = set.trimByBusinessSet(bus);
        System.out.println("Set size: " +set.size());
        System.out.println("Trimming by useful votes:");
        set = set.trimByVotes("useful", 1L);
        System.out.println("set size: " +set.size());
        
        //Load, run and print results from tests using dataset 
        TestSuite suite = TestSuite.getDefaultSuite();
        TestResult tr = suite.testAndStoreAllRecords(set);
        System.out.println(tr);
        
        System.exit(0);
        
        //<!-- nothing is set to list model, window is quite small, system exits
            //and run before it get here and run system cant find window with 
                //GUI name. Is this test code?
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
