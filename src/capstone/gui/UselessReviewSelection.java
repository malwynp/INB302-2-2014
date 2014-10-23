/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

import capstone.gui.UsefulReviewSelect.ChangeVotesListener;
import capstone.yelpmodel.JSONWrapper;
import javax.swing.JOptionPane;

/**
 *
 * @author mark
 */
public class UselessReviewSelection extends DataSetView implements ChangeVotesListener {
        
    private JSONWrapper rootModel = null;
    public void setRootModel(JSONWrapper o) {
        rootModel = o;
    }
    public JSONWrapper getRootModel() {
        return rootModel;
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
