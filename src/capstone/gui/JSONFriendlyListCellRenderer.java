/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class JSONFriendlyListCellRenderer implements ListCellRenderer<JSONObject> {

    private String keys[] = null;
    
    public JSONFriendlyListCellRenderer(String keys[]) {
        this.keys = keys;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends JSONObject> jlist, JSONObject o, int index, boolean selected, boolean focused) {
        
        JPanel pane = new JPanel();
        pane.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel number = new JLabel("" + index);
        pane.add(number);
        
        number.setPreferredSize(new Dimension(32, 16));
        number.setForeground(Color.CYAN);
        
        if (keys != null) {
            for (String k : keys) {
                Object var = o.get(k);

                if (k.contains(".")) {
                    String[] path = k.split(".");
                    for (String p : path) {
                        var = ((JSONObject)var).get(p);
                    }
                }
                
                if (o.get(k) == null) continue;
                
                JLabel cell = null;
                if (o.get(k) instanceof Number) {
                    cell = new JLabel("" + o.get(k));
                    cell.setForeground(Color.GREEN);
                } else {
                    cell = new JLabel((String)o.get(k));
                }
                pane.add(cell);
            }
        }

        pane.setOpaque(selected);
        if (selected) pane.setBackground(Color.decode("#005555"));

        return pane;
        
    }
    
}
