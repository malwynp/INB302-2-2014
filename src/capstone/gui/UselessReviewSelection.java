/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

import capstone.gui.UsefulReviewSelect.ChangeVotesListener;
import capstone.model.Review;
import javax.swing.JOptionPane;

/**
 *
 * @author mark
 */
public class UselessReviewSelection extends DataSetView implements ChangeVotesListener {
        
    private Review rootModel = null;
    public void setRootModel(Review o) {
        rootModel = o;
    }
    public Review getRootModel() {
        return rootModel;
    }
    public Review getModel() {
        return (Review)(super.getModel());
    }
    
    @Override
    public void votesChange(UsefulReviewSelect sel, long newVotes) {
        if (getRootModel() == null) return;
        
        try {
            setModel(getRootModel().cull(sel.getUsefulModel()));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error has occured in the GUI:\n" + e.getLocalizedMessage());
        }
    }
    
}
