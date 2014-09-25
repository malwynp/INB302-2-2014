/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

import capstone.CapException;
import capstone.yelpmodel.Review;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mark
 */
public class UsefulReviewSelect extends JPanel implements PropertyChangeListener {
    
    private Review reviewModel = null;
    private SpinButton spinner = null;
    private JLabel label = null;
    private DataSetView dsv = null;
    
    public UsefulReviewSelect() {
        super();
        
        setLayout(new BorderLayout());
        spinner = new SpinButton();
        spinner.addPropertyChangeListener(this);
        
        JPanel topPane = new JPanel();
        topPane.setLayout(new BoxLayout(topPane, BoxLayout.LINE_AXIS));
        
        label = new JLabel("Select reviews with helpfulness score of at least: ");
        topPane.add(label);
        topPane.add(spinner);
        
        add(topPane, BorderLayout.NORTH);
        
        dsv = new DataSetView();
        dsv.setKeys(new String[] { "votes.useful", "text" } );
        dsv.setSortKey("votes.useful");
        dsv.setSorted(true);
        add(dsv, BorderLayout.CENTER);
    }
    
    public void setModel(Review model) {
        reviewModel = model;
        try {
            dsv.setModel(getUsefulModel());
        } catch (Exception e) {
        }
    }
    
    public Review getModel() {
        return reviewModel;
    }
    
    public Review getUsefulModel() {
        if (reviewModel == null) return null;
        return reviewModel.trimByVotes("useful", minimumVoteCount());
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        try {
            dsv.setModel(getUsefulModel());
            long newmin = minimumVoteCount();
            for (ChangeVotesListener cvl : listeners)
                cvl.votesChange(newmin);
        } catch (CapException ex) {
        }
    }

    public SpinButton getSpinButton() {
        return spinner;
    }

    public long minimumVoteCount() {
        double minimum = spinner.getValue();
        if (reviewModel == null) return 0;
        
        long minVotes = reviewModel.getMinimumVotesAsLong("useful");
        long maxVotes = reviewModel.getMaximumVotesAsLong("useful");
        
        long mvl = (long) ((maxVotes - minVotes) * minimum);
        
        return mvl;
    }
    
    private List<ChangeVotesListener> listeners = new ArrayList<>();
    public void addListener(ChangeVotesListener cvl) {
        if (cvl == null || listeners.contains(cvl)) return;
        listeners.add(cvl);
    }
    public void removeListener(ChangeVotesListener cvl) {
        if (cvl == null || !listeners.contains(cvl)) return;
        listeners.remove(cvl);
    }
    
    public static interface ChangeVotesListener {
        public void votesChange(long newVotes);
    }

    
}
