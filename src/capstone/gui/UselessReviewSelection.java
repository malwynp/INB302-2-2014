/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

import capstone.CapException;
import capstone.gui.UsefulReviewSelect.ChangeVotesListener;
import capstone.model.JSONWrapper;
import capstone.model.Review;
import javax.swing.JOptionPane;

/**
 *
 * @author mark
 */
public class UselessReviewSelection extends DataSetView implements ChangeVotesListener {
        
    private Review rootModel = null;
    public void setRootModel(Review o) throws CapException {
        rootModel = o;
        setModel(o);
    }
    public Review getRootModel() {
        return rootModel;
    }
    @Override
    public Review getModel() {
        return (Review)(super.getModel());
    }
    
    @Override
    public void votesChange(UsefulReviewSelect sel, double newVotes) {
        if (getRootModel() == null) return;
        
        try {
            setModel(new Review(getRootModel().getArray()).cull(sel.getUsefulModel()));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error has occured in the GUI:\n" + e.getLocalizedMessage());
        }
    }
    
}
