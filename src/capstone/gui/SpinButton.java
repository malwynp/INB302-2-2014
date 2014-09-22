/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

/**
 *
 * @author mark
 */
public class SpinButton extends JPanel implements ActionListener {
    
    final private JFormattedTextField field;
    private double min = 0, max = 1, value = 0.25, increment = 0.1;
    final private PropertyChangeSupport pcs;
    final private JPanel bPane;
    final private JButton up, down;
    final private long rounding = 100;
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        if (pcs == null) return;
        pcs.addPropertyChangeListener(pcl);
    }
    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        if (pcs == null) return;
        pcs.removePropertyChangeListener(pcl);
    }
    
    public SpinButton() {
        pcs = new PropertyChangeSupport(this);
        
        setLayout(new BorderLayout());
        field = new JFormattedTextField();
        field.setValue(value);
        add(field, BorderLayout.CENTER);
        
        field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                double d = Double.parseDouble(field.getText());
                setValue(d);
                field.setText("" + round(getValue(), rounding));
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
    
    public void setRange(double minimum, double maximum) {
        if (Double.isNaN(min) || Double.isNaN(maximum)) return;
        if (minimum < maximum) {
            min = minimum;
            max = maximum;
        } else {
            max = minimum;
            min = maximum;
        }
    }
    
    public double getMinimum() {
        return min;
    }
    public double getMaximum() {
        return max;
    }
    
    public void setIncrement(double i) {
        if (Double.isNaN(i)) return;
        increment = i;
    }
    public double getIncrement() {
        return increment;
    }
    
    public void setValue(double v) {
        double oldv = value;
        
        if (Double.isNaN(v)) return;
        if (v > getMaximum()) v = getMaximum();
        if (v < getMinimum()) v = getMinimum();
        value = v;
        field.setText("" + round(v, rounding));
        
        pcs.firePropertyChange("value", oldv, v);
    }
    public double getValue() {
        return value;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == up) setValue(getValue() + getIncrement());
        if (ae.getSource() == down) setValue(getValue() - getIncrement());
        repaint();
    }
    
    public double round(double in, long roundTo) {
        return (double)((long)(in * roundTo)) / (double)roundTo;
    }
    
}
