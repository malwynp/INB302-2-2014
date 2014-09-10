/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import capstone.yelpmodel.YelpModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author mark
 */
public class CapstoneApplication extends JFrame implements ActionListener {

    private MenuBar menuBar;
    private MainGUIPanel panel;
    private YelpModel model;
    
    public CapstoneApplication() {
        super("Capstone Yelp Review Categorisation");
        
        setPreferredSize(new Dimension(1024, 768));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new MenuBar(this);
        menuBar.addMenuItem("_Application", "_Quit");
        
        setJMenuBar(menuBar);

        panel = new MainGUIPanel(this);
        setContentPane(panel);
        
        panel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        panel.setVisible(false);
        
        pack();
        
        YelpModelLoader yml = new YelpModelLoader(this);
        yml.start();
        
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
        }
    }

    void modelIsLoaded(YelpModel model) {
        this.model = model;
        panel.setVisible(true);
        panel.modelIsLoaded(model);
    }
    
    public ComboBoxModel getAllBusinessCategories() {
        if (model == null || model.getBusinesses() == null) return null;
        String[] arr = model.getBusinesses().getAllBusinessCategories();
        ComboBoxModel cbm = new DefaultComboBoxModel(arr);
        return cbm;
    }
    
}
