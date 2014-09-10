/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author mark
 */
public class MenuBar extends JMenuBar {
    
    CapstoneApplication app;
    
    public MenuBar(CapstoneApplication app) {
        this.app = app;
        
        addMenuItem("Application", "Quit");
    }
    
    public void addMenuItem(String menuStr, String action) {
        JMenu menu = null;
        for (int i = 0; i < getMenuCount(); i++) {
            if (getMenu(i).getActionCommand().equalsIgnoreCase(menuStr)) {
                menu = getMenu(i);
                break;
            }
        }
        if (menu == null) add(menu = new JMenu(menuStr));
        JMenuItem menuItem = new JMenuItem(action);
        menu.add(menuItem);
        
        menuItem.addActionListener(app);
    }
    
}
