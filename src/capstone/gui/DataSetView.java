/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import capstone.CapException;
import capstone.yelpmodel.Business;
import capstone.yelpmodel.JSONWrapper;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class DataSetView extends JPanel {
    
    private JList<JSONObject> list;
    private DefaultListModel<JSONObject> listModel;
    private JScrollPane jsp;
    
    private String sortKey;
    private boolean sortOrder;
    private boolean sorted;
    
    public String getSortKey() {
        return sortKey;
    }
    public void setSortKey(String key) {
        sortKey = key;
    }
    
    public boolean isAscending() {
        return sortOrder;
    }
    public void setAscending(boolean order) {
        sortOrder = order;
    }
    
    public boolean isSorted() {
        return sorted;
    }
    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }
    
    public DataSetView() {
        Border inner, outer;
        inner = BorderFactory.createEmptyBorder(8,8,8,8);
        outer = BorderFactory.createTitledBorder("Data Set");
        setBorder(BorderFactory.createCompoundBorder(inner, outer));
        
        listModel = new DefaultListModel<JSONObject>();
        list = new JList(listModel);
        String keys[] = new String[] {
          "name", "review_count"  
        };
        list.setCellRenderer(new JSONFriendlyListCellRenderer(keys));
        
        jsp = new JScrollPane(list);
        
        setLayout(new BorderLayout());
        add(jsp, BorderLayout.CENTER);
    }
    
    void setModel(JSONWrapper wrapper) throws CapException {
        if (wrapper == null) {
            listModel = new DefaultListModel<>();
            list.setModel(listModel);
        } else {
            listModel = new DefaultListModel<>();
            JSONObject[] array;
            
            array = sorted ? wrapper.getSortedArray(sortKey, sortOrder) : wrapper.getArray();
            
            for (JSONObject o : array)
                listModel.addElement(o);
            list.setModel(listModel);

            Border inner, outer;
            inner = BorderFactory.createEmptyBorder(8,8,8,8);
            outer = BorderFactory.createTitledBorder("Data Set: (" + wrapper.size() + " elements)");
            setBorder(BorderFactory.createCompoundBorder(inner, outer));
        }

    }
    
}
