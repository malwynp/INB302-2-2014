/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import capstone.gui.YelpModelLoader.YelpModelLoadListener;
import capstone.yelpmodel.Model;
import capstone.yelpmodel.YelpModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author mark
 */
public class CapstoneApplication extends JFrame implements ActionListener, YelpModelLoadListener {

    private MenuBar menuBar;
    private MainGUIPanel panel;
    private Model model;
    
    public CapstoneApplication() {
        super("Capstone Yelp Review Categorisation");
        
        setIconImage(new ImageIcon(getClass().getResource("server.png")).getImage());
        
        setPreferredSize(new Dimension(1024, 768));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new MenuBar(this);
        menuBar.addMenuItem("_Model", "_Load Yelp Model");
        menuBar.addMenuItem("_Model", "_Load Custom JSON Model");
        menuBar.addMenuItem("_Model", "_Load Serialised Model");
        menuBar.addMenuItem("_Model", "_Save Serialised Model");
        menuBar.addMenuItem("_Model", "_Close Model");
        menuBar.addMenuItem("_Application", "_Quit");
        
        setJMenuBar(menuBar);

        panel = new MainGUIPanel(this);
        setContentPane(panel);
        
        panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        panel.setVisible(false);
        
        pack();
        
        setVisible(true);
    }
    
    public static void main(String args[]) {
        
        // Set native toolkit if available
        try {
            for (UIManager.LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels())
                if (lafi.getName().contains("GTK"))
                    UIManager.setLookAndFeel(lafi.getClassName());
        } catch (Exception e) {
        }
        
        new CapstoneApplication();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String cmd = ae.getActionCommand().toLowerCase();
        
        switch (cmd) {
            default: break;
                
            case "quit":
                System.exit(0);
                break;

            case "load yelp model": {
                JFileChooser jfc = new JFileChooser();
                jfc.setDialogTitle("Select Yelp model directory");
                jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jfc.setAcceptAllFileFilterUsed(false);
                
                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File dir = jfc.getSelectedFile();
                    System.out.println(dir.toString());
                    File bus = new File(dir.toString() + "/yelp_training_set_business.json");
                    File rev = new File(dir.toString() + "/yelp_training_set_review.json");
                    if (!bus.exists() || !rev.exists()) {
                        JOptionPane.showMessageDialog(null, "This does not appear to be a correctly formatted Yelp model directory.");
                    } else {
                        YelpModelLoader yml = new YelpModelLoader(dir, this);
                        yml.start();
                    }
                }
                break;
            }

            case "load custom json model": {
                
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
                    jfc.setFileFilter(new FileNameExtensionFilter("Serialised Model File", "ser"));
                    if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                        FileOutputStream fos;
                        ObjectOutputStream oos;
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
                    if (JOptionPane.showConfirmDialog(null, "There is already a model loaded. Close without saving?") != JOptionPane.OK_OPTION)
                        break;
                
                    JFileChooser jfc = new JFileChooser();
                    jfc.setDialogTitle("Load serialised model");
                    jfc.setFileFilter(new FileNameExtensionFilter("Serialised Model File", "ser"));
                    if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        FileInputStream fis;
                        ObjectInputStream ois;
                        try {
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

    public void modelIsLoaded(Model model) {
        this.model = model;
        panel.setVisible(model != null);
        panel.modelIsLoaded(model);

        if (model != null) {
            if (model instanceof YelpModel) ((YelpModel)model).forget();
        }
    }
    
    public ComboBoxModel getAllBusinessCategories() {
        if (model == null || model.getBusinesses() == null) return null;
        String[] arr = model.getBusinesses().getAllBusinessCategories();
        ComboBoxModel cbm = new DefaultComboBoxModel(arr);
        
        return cbm;
    }
    
}
