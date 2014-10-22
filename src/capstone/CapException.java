/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone;

import javax.swing.JOptionPane;

/**
 *  Overarching class for custom exceptions
 * 
 * @param 
 */
public class CapException extends Exception {
    
    /**
     * Exception method for creating custom exceptions
     * 
     * @param mesg stores custom exception text
     */
    public CapException(String mesg) {
        super(mesg);
        JOptionPane.showMessageDialog(null, getMessage());
    }
    
}
