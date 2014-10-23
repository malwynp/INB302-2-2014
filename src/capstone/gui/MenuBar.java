/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

//Methods for creating GUI
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

/**
 * Overarching class for interacting with the menuBar
 * <!-- unsure
 */
public class MenuBar extends JMenuBar {
   
    CapstoneApplication app;
    
    /**
     * <!-- Sets app to current app? Unsure.
     * @param app 
     */
    public MenuBar(CapstoneApplication app) {
        super();
        this.app = app;
    }
    
    /**
     * Adds a menu menu item
     * @param menuStr contains name of menu item
     * @param action contains action for when clicked
     */
    public JMenuItem addMenuItem(String menuStr, String action) {
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
        return menuItem;
    }

    public JRadioButtonMenuItem[] addRadioMenu(String rootMenu, String menuGroup, String[] items) {
        int menuMem = -1, actMem = -1;
        Character memM = null, memA = null;
        String menuStr = rootMenu;
        String action = menuGroup;
        
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
        JMenu subMenu = new JMenu(action);
        menu.add(subMenu);
        
        if (memM != null) menu.setMnemonic(memM);
        if (memA != null) subMenu.setMnemonic(memA);
        
        List<JMenuItem> subMenus = new ArrayList<>();
        ButtonGroup b = new ButtonGroup();
        for (String s : items) {
            JRadioButtonMenuItem it = new JRadioButtonMenuItem(s);
            b.add(it);
            it.addActionListener(app);
            subMenu.add(it);
            subMenus.add(it);
        }
        return subMenus.toArray(new JRadioButtonMenuItem[subMenus.size()]);
    }
    
}
