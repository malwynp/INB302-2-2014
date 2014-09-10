/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
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
        pane.setLayout(new GridLayout(1, 0));
        
        JLabel number = new JLabel("" + index);
        pane.add(number);
        
        if (keys != null) {
            for (String k : keys) {
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
        
        return pane;
        
    }
    
}
