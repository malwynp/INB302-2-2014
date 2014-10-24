/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

//Methods for rendering GUI text
import java.awt.Color;

//Methods for GUI buttons, checkboxes, etc.
import java.awt.Component;

//Methods for controling height and width of a abstract window toolkit component
import java.awt.Dimension;

//Methods for arranging components
import java.awt.FlowLayout;

//Methods for creating GUI
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

//<!-- Unsure
import org.json.simple.JSONObject;

/**
 * Overarching class <!-- unsure
 * 
 */
public class JSONFriendlyListCellRenderer implements ListCellRenderer<JSONObject> {

    private String keys[] = null;
    
    /**
     * <!-- Unsure
     * 
     * @param keys 
     */
    public JSONFriendlyListCellRenderer(String keys[]) {
        this.keys = keys;
    }
    
    /**
     * <!-- Unsure
     * @param jlist
     * @param o
     * @param index
     * @param selected
     * @param focused
     * @return 
     */
    @Override
    public Component getListCellRendererComponent(JList<? extends JSONObject> jlist, JSONObject o, int index, boolean selected, boolean focused) {
        
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel number = new JLabel("" + index);
        pane.add(number);
        
        number.setPreferredSize(new Dimension(16, 16));
        
        number.setPreferredSize(new Dimension(32, 16));
        number.setForeground(Color.CYAN);
        
        if (keys != null) {
            for (String k : keys) {
                Object var;
                
                if (k.contains(".")) {
                    String[] path = k.split("[.]");
                    var = o;
                    for (String p : path) {
                        var = ((JSONObject)var).get(p);
                    }
                } else {
                    var = o.get(k);
                }
                
                if (var == null) continue;
                
                JLabel cell = null;
                if (var instanceof Number) {
                    cell = new JLabel("" + var);
                    cell.setForeground(Color.GREEN);
                } else {
                    cell = new JLabel((String)var);
                }
                cell.setOpaque(selected);
                if (selected) cell.setBackground(Color.decode("#005555"));
                pane.add(cell);
            }
        }

        pane.setOpaque(selected);
        if (selected) pane.setBackground(Color.decode("#005555"));

        return pane;
        
    }
    
}
