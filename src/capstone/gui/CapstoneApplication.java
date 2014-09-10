/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author mark
 */
public class CapstoneApplication extends JFrame implements ActionListener {

    private MenuBar menuBar;
    private MainGUIPanel panel;
    
    public CapstoneApplication() {
        super("Capstone Yelp Review Categorisation");
        
        setPreferredSize(new Dimension(1024, 768));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new MenuBar(this);
        setJMenuBar(menuBar);

        panel = new MainGUIPanel(this);
        setContentPane(panel);
        pack();
        
        setVisible(true);
    }
    
    public static void main(String args[]) {
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
    
}
