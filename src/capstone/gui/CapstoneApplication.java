/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

//Methods for loading model information to the GUI
import capstone.gui.YelpModelLoader.YelpModelLoadListener;

//Methods for loading in models
import capstone.yelpmodel.Model;
import capstone.yelpmodel.NanModel;
import capstone.yelpmodel.YelpModel;

//Methods for controling height and width of a abstract window toolkit component
import java.awt.Dimension;

//Methods for user interaction with abstract window toolkit components
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Methods for system input & output through datastreams
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//<!-- redundent

//Methods for creating GUI
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Overarching class to create the applications display window
 * 
 */
public class CapstoneApplication extends JFrame implements ActionListener, YelpModelLoadListener {

    private MenuBar menuBar;
    private MainGUIPanel panel;
    private Model model;
    
    /**
     * Create main application window, containing blank panel with a menu bar to
     * select model to load in
     * 
     */
    public CapstoneApplication() {
        //Window title
        super("Capstone Review Processing and Analysis");
        initGUI();
        setVisible(true);
    }
    
    private void initGUI() {
        //Window favicon
        setIconImage(new ImageIcon(getClass().getResource("server.png")).getImage());
        
        //Set window size
        setPreferredSize(new Dimension(1024, 768));
        //Define current size is max window size for both vert and hoz
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Menu options
        menuBar = new MenuBar(this);
        JRadioButtonMenuItem[] bg = menuBar.addRadioMenu("_Application", "Look and Feel", new String[] {
            "GTK Look and Feel", "Native Look and Feel", "Metal Look and Feel"
        });
        menuBar.addMenuItem("_Application", "_Quit");
        menuBar.addMenuItem("_Model", "_Load Nan Model");
        menuBar.addMenuItem("_Model", "_Load Yelp Model");
//        menuBar.addMenuItem("_Model", "_Load Custom JSON Model");
        menuBar.addMenuItem("_Model", "_Load Serialised Model");
        menuBar.addMenuItem("_Model", "_Save Serialised Model");
        menuBar.addMenuItem("_Model", "_Close Model");
        
        if (UIManager.getLookAndFeel().getName().contains("GTK")) {
            bg[0].setSelected(true);
        }
        if (UIManager.getLookAndFeel().equals(UIManager.getSystemLookAndFeelClassName())) {
            bg[1].setSelected(true);
        }
        if (UIManager.getLookAndFeel().getName().contains("Metal")) {
            bg[2].setSelected(true);
        }
        
        setJMenuBar(menuBar);

        panel = new MainGUIPanel(this);
        setContentPane(panel);
        
        panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        panel.setVisible(false);
        
        pack();
    }
    
    /**
     * Sets look and feel before creating application window
     * 
     * @param args array containing parameters from the compiler
     */
    private static CapstoneApplication instance = null;
    public static void main(String args[]) {
        instance = new CapstoneApplication();
    }

    private CapstoneApplication(Model model) {
        //Window title
        super("Capstone Review Processing and Analysis");
        initGUI();
        modelIsLoaded(model);
        setVisible(true);
    }
    
    /**
     * 
     * 
     * @param ae stores information about an action event, more specifically, the
     * name of which option the user selects from the model menu
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        //convert string to lowercase, in prep for comparison
        String cmd = ae.getActionCommand().toLowerCase();
        
        switch (cmd) {
            default: break;
                
            case "gtk look and feel":
                try {
                    for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()) {
                        if (lafi.getClassName().contains("GTK")) {
                            UIManager.setLookAndFeel(lafi.getClassName());
                            break;
                        }
                    }
                    refreshLookAndFeel(this);
                } catch (Exception e) {
                }
                break;
            case "native look and feel":
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    refreshLookAndFeel(this);
                } catch (Exception e) {
                }
                break;
            case "metal look and feel":
                try {
                    for (LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()) {
                        if (lafi.getClassName().contains("Metal")) {
                            UIManager.setLookAndFeel(lafi.getClassName());
                            break;
                        }
                    }
                    refreshLookAndFeel(this);
                } catch (Exception e) {
                }
                break;
                
            case "quit":
                System.exit(0);
                break;
            case "load nan model": {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Select Nan data set");
                
                //If the specified file is selected by the user is a known format
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File f = jfc.getSelectedFile();
                    //try loading file into specified format 
                    try {
                        Model m = new NanModel(f);
                        modelIsLoaded(m);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,
                                "An error occured in loading this file:\n" + ex.toString());
                    }
                }
            } break;

            case "load yelp model": {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Select Yelp model directory");
                //Allow only full directories to be uploaded
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.setAcceptAllFileFilterUsed(false);
                
                //If specified file selected by the user is a known format
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File dir = jfc.getSelectedFile();
                    System.out.println(dir.toString());
                    //Load in yelp model files
                    File bus = new File(dir.toString() + "/yelp_training_set_business.json");
                    File rev = new File(dir.toString() + "/yelp_training_set_review.json");
                    //If the models failed to load in
                    if (!bus.exists() || !rev.exists()) {
                        JOptionPane.showMessageDialog(null, "This does not appear to be a correctly formatted Yelp model directory.");
                    } else {
                        YelpModelLoader yml = new YelpModelLoader(dir, this);
                        //<!-- Haven't come across this yet so im going to leave one of these and hopefully get back to it before u see it
                        yml.start();
                    }
                }
                break;
            }

            case "load custom json model": {//<!-- Am i going to be commenting this or are you?
                
                break;
            }

            case "close model": {
                modelIsLoaded(null);
                break;
            }

            case "save serialised model": {
                
                if (model == null) {
                    JOptionPane.showMessageDialog(null, "No model currently loaded!");
                } else {
                    JFileChooser jfc = new JFileChooser();
                    jfc.setDialogTitle("Save serialised model");
                    //Only allow '.ser' to be saved
                    jfc.setFileFilter(new FileNameExtensionFilter("Serialised Model File", "ser"));
                    
                    //If specified file selected by the user is a known format
                    if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        FileOutputStream fos;
                        ObjectOutputStream oos;
                        //Try writting to serialisation file
                        try {
                            fos = new FileOutputStream(jfc.getSelectedFile());
                            oos = new ObjectOutputStream(fos);
                            oos.writeObject(model);
                            oos.close();
                            fos.close();
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "A fatal error occured in attempting to serialise this model:\n" + e.getLocalizedMessage());
                        }
                    }
                }
                
                break;
            }

            case "load serialised model": {
                if (model != null)
                    //<!-- Should this be added to the other classes that load models
                    if (JOptionPane.showConfirmDialog(null, "There is already a model loaded. Close without saving?") != JOptionPane.OK_OPTION)
                        break;
                
                    JFileChooser jfc = new JFileChooser();
                    jfc.setDialogTitle("Load serialised model");
                    //Only allow '.ser' to be saved
                    jfc.setFileFilter(new FileNameExtensionFilter("Serialised Model File", "ser"));
                    
                    //If specified file selected by the user is a known format
                    if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        FileInputStream fis;
                        ObjectInputStream ois;
                        try {//Try load in specified model
                            fis = new FileInputStream(jfc.getSelectedFile());
                            ois = new ObjectInputStream(fis);
                            Model m = (Model)(ois.readObject());
                            ois.close();
                            fis.close();
                            modelIsLoaded(m);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "A fatal error occured in attempting to load this serialised model:\n" + e.getLocalizedMessage());
                        }
                    }
                break;
            }
        }
    }
    
    /**
     * Changes the main panel to be visible, this allows the user to explore the data set and run test
     * <!-- panel.Visible refrences the main panel that was set to false before right?
     * 
     * @param model Contains information on dataset <!-- reads from object so it has the dataset? or the info on the input type?
     */
    public void modelIsLoaded(Model model) {
        this.model = model;
        panel.setVisible(model != null);
        panel.modelIsLoaded(model);

        if (model != null) {
            if (model instanceof YelpModel) ((YelpModel)model).forget();
        }
    }
    
    /**
     * Creates drop down list containing all businesses options
     * 
     * @return array of businesses
     */
    public ComboBoxModel getAllBusinessCategories() {
        if (model == null || model instanceof NanModel || model.getBusinesses() == null) return null;
        String[] arr = model.getBusinesses().getAllBusinessCategories();
        ComboBoxModel cbm = new DefaultComboBoxModel(arr);
        
        return cbm;
    }

    public static CapstoneApplication refreshLookAndFeel(CapstoneApplication app) {
        if (app.model == null) {
            app.dispose();
            instance = new CapstoneApplication();
        } else {
            app.dispose();
            instance = new CapstoneApplication(app.model);
        }
        return instance;
    }

}
