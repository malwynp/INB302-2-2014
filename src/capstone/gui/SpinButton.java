/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

//Methods for arranging and resizing containers
import java.awt.BorderLayout;

//Methods for controling height and width of a abstract window toolkit component
import java.awt.Dimension;

//<!-- todo
import java.awt.GridLayout;

//<!-- todo
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//<!-- todo
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

//Methods for creating GUI
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

/**
 * overarching class containing methods related to the useful votes filter buttons
 * 
 */
public class SpinButton extends JPanel implements ActionListener {
    
    final private JFormattedTextField field;
    private int min = 0, max = 10, value = 1, increment = 1;
    final private PropertyChangeSupport pcs;
    final private JPanel bPane;
    final private JButton up, down;
    
    /**
     * <!-- 
     * @param pcl 
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        if (pcs == null) return;
        pcs.addPropertyChangeListener(pcl);
    }
    
    /**
     * <!--
     * @param pcl 
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        if (pcs == null) return;
        pcs.removePropertyChangeListener(pcl);
    }
    
    /**
     * Creates buttons for filter useful votes
     */
    public SpinButton() {
        pcs = new PropertyChangeSupport(this);
        
        setLayout(new BorderLayout());
        field = new JFormattedTextField();
        field.setValue(value);
        add(field, BorderLayout.CENTER);
        
        field.addActionListener(new ActionListener() {
            /**
             * <!-- to do
             */
            @Override
            public void actionPerformed(ActionEvent ae) {
                int d = Integer.parseInt(field.getText());
                setValue(d);
                field.setText("" + d);
            }
        });
        
        bPane = new JPanel();
        bPane.setLayout(new GridLayout(0, 1));
        
        up = new JButton("\u21e7");
        down = new JButton("\u21e9");
        
        bPane.add(up);
        bPane.add(down);
        
        add(bPane, BorderLayout.EAST);
        
        up.addActionListener(this);
        down.addActionListener(this);
        
        setMinimumSize(new Dimension(32, 32));
        setPreferredSize(new Dimension(96, 32));
        bPane.setPreferredSize(new Dimension(32, 48));
        
    }
    
    /**
     * Sets range between two input int's 
     * @param minimum
     * @param maximum 
     */
    public void setRange(int minimum, int maximum) {
        if (minimum < maximum) {
            min = minimum;
            max = maximum;
        } else {
            max = minimum;
            min = maximum;
        }
    }
    
    public int getMinimum() {
        return min;
    }
    public int getMaximum() {
        return max;
    }
    
    public void setIncrement(int i) {
        increment = i;
    }
    public int getIncrement() {
        return increment;
    }
    
    /**
     * Set int to the current field as long as its within the min and max values
     * @param v 
     */
    public void setValue(int v) {
        int oldv = value;
        
        if (v > getMaximum()) v = getMaximum();
        if (v < getMinimum()) v = getMinimum();
        value = v;
        field.setText("" + v);
        
        pcs.firePropertyChange("value", oldv, v);
    }
    public int getValue() {
        return value;
    }
    
    /**
     * Onclick of the button add or minus the the increment value to the current value
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == up) setValue(getValue() + getIncrement());
        if (ae.getSource() == down) setValue(getValue() - getIncrement());
        repaint();
    }
    
}
