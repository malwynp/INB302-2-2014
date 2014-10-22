/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

//Methods for arranging and resizing containers
import java.awt.BorderLayout;

//Methods for rendering GUI text
import java.awt.Color;
import java.awt.Font;

//Methods for GUI buttons, checkboxes, etc.
import java.awt.Component;

//Methods for interacting with arrays
import java.util.ArrayList;

//Methods for interacting with lists
import java.util.List;

//Methods for creating GUI
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

//<!-- unsure
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Overarching class to <!-- unsure
 * 
 */
public class JSONDetailView extends JPanel {
    
    private final List<String> ignoreKeys = new ArrayList<>();
    private JTable table;
    private JSONObject model;
    private JScrollPane jsp;
    
    /**
     * Displays information on a specific business in a table format
     * <!-- unsure what this method does at it contains creating a table and the basic text colour scheme for the system
     * 
     */
    public JSONDetailView() {
        setLayout(new BorderLayout());
        table = new JTable();
        table.setShowGrid(false);
        table.setCellEditor(new JSONTableCellEditor());
        table.setCellSelectionEnabled(false);
        
        table.setRowHeight(24);
        table.setRowMargin(2);
        table.setFocusable(false);
        
        table.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        
        table.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean selected, boolean focus, int row, int column) {
                JLabel label = new JLabel();
                
                label.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
                
                if (column == 0) { // header
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                    label.setText((String)o);
                } else {
                    
//                    System.out.println("o = " + o.getClass().getSimpleName() + "; " + o.toString());
                    
                    //Colour scheme for text displayed within the system
                    if (o instanceof Number) {
                        label.setForeground(Color.GREEN);
                        label.setText(o.toString());
                    } else if (o instanceof String) {
                        label.setForeground(Color.LIGHT_GRAY);
                        label.setText(o.toString());
                    } else if (o instanceof Boolean) {
                        label.setForeground((Boolean)o ? Color.YELLOW : Color.YELLOW.darker().darker());
                        label.setText(o.toString());
                    } else if (o instanceof JSONArray) {
                        label.setForeground(Color.CYAN);
                        String str = "";
                        JSONArray arr = ((JSONArray)o);
                        for (int i = 0; i < arr.size(); i++)
                            str += ((String)arr.get(i)) + ((i < arr.size() - 1) ? ", " : "");
                        label.setText(str);
                    } else if (o instanceof Object[]) {
                        label.setForeground(Color.CYAN);
                        String str = "";
                        Object[] arr = ((Object[])o);
                        for (int i = 0; i < arr.length; i++)
                            str += (String)(arr[i]) + ((i < arr.length - 1) ? ", " : "");
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
                
                if (selected) {//Highlight selected
                    label.setOpaque(true);
                    label.setBackground(Color.BLUE);
                } else {
                    label.setOpaque(false);
                }

                return label; 
            }
        });
        
        add(jsp = new JScrollPane(table), BorderLayout.CENTER);
    }
    
    //<!-- are IgnoreKey's business that we skip as they are not within the service that we selected
    public void addIgnoreKey(String k) {
        if (k == null || ignoreKeys.contains(k)) return;
        ignoreKeys.add(k);
    }
    public void removeIgnoreKey(String k) {
        if (k == null || !ignoreKeys.contains(k)) return;
        ignoreKeys.remove(k);
    }
    public void addIgnoreKeys(String[] kk) {
        if (kk == null) return;
        for (String k : kk) addIgnoreKey(k);
    }
    public void removeIgnoreKeys(String[] kk) {
        if (kk == null) return;
        for (String k : kk) removeIgnoreKey(k);
    }
    public String[] getIgnoreKeys() {
        return ignoreKeys.toArray(new String[ignoreKeys.size()]);
    }
    
    /**
     * Calculates the number of rows within a data set that aren't on the ignore list
     * 
     * @return total number of rows within the dataset
     */
    public int countRows() {
        if (model == null) return 0;
        int count = 0;
        for (Object o : model.keySet()) {
            if (ignoreKeys.contains((String)o)) continue;
            count++;
        }
        return count;
    }
    
    /**
     * Sets input object to current model
     * <!-- this is right? right?
     * @param obj Stores JSONObject that contains a dataset
     */
    public void setModel(JSONObject obj) {
        this.model = obj;
        table.setModel(new AbstractTableModel() {

            @Override
            public int getRowCount() {
                if (model == null) return 0;
                return countRows();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int y, int x) {
                if (model == null) return null;
                String key = (String)(model.keySet().toArray()[y]);
                if (x == 0) {
                    return key;
                } else if (x == 1) {
                    return model.get(key);
                }
                return null;
            }
            
            @Override
            public String getColumnName(int index) {
                if (index == 0) return "Key";
                if (index == 1) return "Value";
                return "";
            }
            
        });

        table.sizeColumnsToFit(0);

    }
    
    /**
     * Get current model
     * @return current model
     */
    public JSONObject getModel() {
        return model;
    }
    
}
