/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone;

import javax.swing.JOptionPane;

/**
 *
 * @author mark
 */
public class CapException extends Exception {
    
    public CapException(String mesg) {
        super(mesg);
        JOptionPane.showMessageDialog(null, getMessage());
    }
    
}
