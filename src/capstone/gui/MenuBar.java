/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author mark
 */
public class MenuBar extends JMenuBar {
    
    CapstoneApplication app;
    
    public MenuBar(CapstoneApplication app) {
        super();
        this.app = app;
    }
    
    public void addMenuItem(String menuStr, String action) {
        int menuMem = -1, actMem = -1;
        Character memM = null, memA = null;
        if (menuStr.contains("_")) {
            menuMem = menuStr.indexOf("_");
            menuStr = menuStr.replace("_", "");
            memM = menuStr.charAt(menuMem);
        }
        if (action.contains("_")) {
            actMem = action.indexOf("_");
            action = action.replace("_", "");
            memA = action.charAt(actMem);
        }
        
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
        
        if (memM != null) menu.setMnemonic(memM);
        if (memA != null) menuItem.setMnemonic(memA);
        
        menuItem.addActionListener(app);
    }
    
}
