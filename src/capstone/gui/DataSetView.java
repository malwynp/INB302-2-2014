/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

//Method for displaying custom exception message
import capstone.CapException;

//Methods for loading in yelp data
import capstone.yelpmodel.Business;
import capstone.yelpmodel.Review;
import capstone.yelpmodel.JSONWrapper;//<!-- unsure what this does

//Methods for arranging and resizing containers
import java.awt.BorderLayout;

//Methods for creating GUI
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;

//<!-- For JSON objects or serilization
import org.json.simple.JSONObject;

/**
 * Overarching class for interacting with the data set
 * 
 */
public class DataSetView extends JPanel {
    
    private JList<JSONObject> list;
    private DefaultListModel<JSONObject> listModel;
    private JSONWrapper dataModel;
    private JScrollPane jsp;
    
    private String sortKey;//<!-- what is sort key? if Keys[] are businesses with review count
    private boolean sortOrder = true;
    private boolean sorted;
    
    private String[] keys;
    
    /**
     * <!-- This is only used here no where else from what I can see
     * @return 
     */
    public String getSortKey() {
        return sortKey;
    }
    
    /**
     * <!-- What is sort key
     * @param key 
     */
    public void setSortKey(String key) {
        sortKey = key;
    }
    
    /**
     * Used to check if dataset is in ascending order
     * 
     * @return boolean that stores if the dataset is Ascending list 
     */
    public boolean isAscending() {
        return sortOrder;
    }
    
    /**
     * Updates sortOrder 
     * 
     * @param order boolean used to update sortOrder
     */
    public void setAscending(boolean order) {
        sortOrder = order;
    }
    
    /**
     * Checks if the list is sorted
     * 
     * @return boolean value true for sorted list, false for unsorted list
     */
    public boolean isSorted() {
        return sorted;
    }
    
    /**
     * Update sorted boolean to current status
     * 
     * @param sorted boolean that signifies if the list is sorted
     */
    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }
    
    /**
     * Displays dataset inside window
     * 
     */
    public DataSetView() {
        Border inner, outer;
        inner = BorderFactory.createEmptyBorder(8,8,8,8);
        outer = BorderFactory.createTitledBorder("Data Set");
        setBorder(BorderFactory.createCompoundBorder(inner, outer));
        
        listModel = new DefaultListModel<JSONObject>();
        list = new JList(listModel);
        
        keys = new String[] {
          "name", "review_count"  
        };
        list.setCellRenderer(new JSONFriendlyListCellRenderer(keys));
        
        jsp = new JScrollPane(list);
        
        setLayout(new BorderLayout());
        add(jsp, BorderLayout.CENTER);
    }
    
    /**
     * Checks dataset then loads into model
     * 
     * @param wrapper stores dataset
     * @throws CapException 
     */
    public void setModel(JSONWrapper wrapper) throws CapException {
        dataModel = wrapper;
        
        if (wrapper == null) {//Dataset is empty
            listModel = new DefaultListModel<>();
            list.setModel(listModel);
            Border inner, outer;
            inner = BorderFactory.createEmptyBorder(8,8,8,8);
            outer = BorderFactory.createTitledBorder("Data Set: (no elements)");
            setBorder(BorderFactory.createCompoundBorder(inner, outer));

        } else {//Loads in dataset
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
    
    /**
     * Gets value of selected list item
     * 
     * @return null if selected list item is empty
     * @return value of selected list item
     */
    public JSONObject getSelected() {
        if (list.getSelectedValue() == null) return null;
        return list.getSelectedValue();
    }
    
    /**
     * <!-- Unsure
     * @param lsl 
     */
    public void addListSelectionListener(ListSelectionListener lsl) {
        list.addListSelectionListener(lsl);
    }
    
    /**
     * <!-- Unsure
     * @param lsl 
     */
    public void removeListSelectionListener(ListSelectionListener lsl) {
        list.removeListSelectionListener(lsl);
    }
    
    /**
     * Returns array of the dataset
     * 
     * @return array containing dataset information
     */
    public String[] getKeys() {
        return keys;
    }
    
    /**
     * Sets the key array with the inputed array
     * @param keys Array of business names and review counts
     */
    public void setKeys(String[] keys) {
        this.keys = keys;
        list.setCellRenderer(new JSONFriendlyListCellRenderer(keys));
    }

    /**
     * <!-- Is this right?
     * @return dataset being used in the model
     */
    public JSONWrapper getModel() {
        return dataModel;
    }
    
}
