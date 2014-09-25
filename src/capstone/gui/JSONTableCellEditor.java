/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
class JSONTableCellEditor extends AbstractCellEditor implements TableCellEditor {
    
    private JLabel label;
    
    public JSONTableCellEditor() {
        label = new JLabel();
    }

    @Override
    public Object getCellEditorValue() {
        return label.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean isSelected, int row, int column) {
        if (column == 0) { // header
            label.setFont(label.getFont().deriveFont(Font.BOLD));
            label.setText((String)o);
        } else {
            
            if (o instanceof Number) {
                label.setForeground(Color.GREEN);
                label.setText((String)o);
            } else if (o instanceof String) {
                label.setForeground(Color.LIGHT_GRAY);
                label.setText((String)o);
            } else if (o instanceof JSONArray) {
                label.setForeground(Color.CYAN);
                String str = "";
                JSONArray arr = ((JSONArray)o);
                for (int i = 0; i < arr.size(); i++)
                    str += ((String)arr.get(i)) + ((i < arr.size() - 1) ? ", " : "");
                label.setText(str);
            } else if (o instanceof JSONObject) {
                label.setForeground(Color.MAGENTA);
                String str = "";
                JSONObject ob = ((JSONObject)o);
                for (Object obo : ob.keySet()) {
                    String ok = (String)obo;
                    str += ok + " = " + (String)ob.get(ok) + "\n";
                }
                label.setText(str);
            }
            
        }
        
        if (isSelected) {
            label.setOpaque(true);
            label.setBackground(Color.BLUE);
        } else {
            label.setOpaque(false);
        }
        
        return label;
    }
    
}