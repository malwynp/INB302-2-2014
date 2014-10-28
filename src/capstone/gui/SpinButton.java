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
 * Overarching class containing methods related to the useful votes filter buttons
 * 
 */
public class SpinButton extends JPanel implements ActionListener {
    
    final private JFormattedTextField field;
    private double min = 0, max = 10, value = 1, increment = 1;
    final private PropertyChangeSupport pcs;
    final private JPanel bPane;
    final private JButton up, down;
    private boolean integerMode = true;
    
    public void setIntegerMode(boolean b) {
        integerMode = b;
    }
    public boolean isInIntegerMode() {
        return integerMode;
    }
    
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
                double d = Double.parseDouble(field.getText());
                setValue(d);
                d = (double)((int)(d * 100)) / 100;
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
        
        setMinimumSize(new Dimension(48, 48));
        setPreferredSize(new Dimension(96, 48));
        bPane.setPreferredSize(new Dimension(48, 64));
        
    }
    
    /**
     * Sets range between two input ints 
     * @param minimum
     * @param maximum 
     */
    public void setRange(double minimum, double maximum) {
        if (minimum < maximum) {
            min = minimum;
            max = maximum;
        } else {
            max = minimum;
            min = maximum;
        }
    }
    
    public double getMinimum() {
        return isInIntegerMode() ? getMinimumInt() : min;
    }
    public double getMaximum() {
        return isInIntegerMode() ? getMaximumInt() : max;
    }
    public int getMinimumInt() {
        return (int)min;
    }
    public int getMaximumInt() {
        return (int)max;
    }
    
    public void setIncrement(double i) {
        increment = i;
    }
    public double getIncrement() {
        return isInIntegerMode() ? getIntIncrement() : increment;
    }
    public int getIntIncrement() {
        return (int)increment;
    }
    
    /**
     * Set int to the current field as long as its within the min and max values
     * @param v 
     */
    public void setValue(double v) {
        double oldv = value;
        
        if (v > getMaximum()) v = getMaximum();
        if (v < getMinimum()) v = getMinimum();
        value = v;
        
        double roundv = (double)((int)(v * 100)) / 100;
        field.setText("" + roundv);
        
        pcs.firePropertyChange("value", oldv, v);
    }
    public double getValue() {
        return isInIntegerMode() ? getIntValue() : value;
    }
    public int getIntValue() {
        return (int)value;
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
