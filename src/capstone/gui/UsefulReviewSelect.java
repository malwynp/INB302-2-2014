/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

//Method for displaying custom exception message
import capstone.CapException;

//Methods for loading in yelp data
import capstone.yelpmodel.NanModel;
import capstone.yelpmodel.Review;

//Methods for arranging and resizing containers
import java.awt.BorderLayout;

//<!-- to do
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

//Methods for interacting with arrays
import java.util.ArrayList;

//Methods for interacting with lists
import java.util.List;

//Methods for creating GUI
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <!-- to do
 * 
 */
public class UsefulReviewSelect extends JPanel implements PropertyChangeListener {
    
    private Review reviewModel = null;
    private SpinButton spinner = null;
    private JLabel label = null;
    private DataSetView dsv = null;
    private boolean nanMode = false;
    
    /**
     * Create useful vote filter panel in GUI
     */
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
    
    /**
     * Set model and set min and max ranges depending on dataset type
     * @param model 
     */
    public void setModel(Review model) {
        reviewModel = model;
        
        if (reviewModel != null) {
            nanMode = !(model.hasBusinessData());
            if (nanMode) {
                spinner.setIntegerMode(false);
                spinner.setRange(0, 1);
                spinner.setIncrement(0.1);
            } else {
                spinner.setIntegerMode(true);
                spinner.setRange(0, (int)(reviewModel.getMaximumVotesAsLong(reviewModel.getUsefulKey())));
                spinner.setIncrement(1);
            }
        }

        try {
            dsv.setModel(getUsefulModel());
        } catch (Exception e) {
        }
    }
    
    public Review getModel() {
        return reviewModel;
    }
    
    /**
     * Retrieve dataset and apply min and max filtering 
     * @return filtered dataset with min and max
     */
    public Review getUsefulModel() {
        if (reviewModel == null) return null;
        if (spinner.isInIntegerMode()) {
            return reviewModel.trimByVotes(reviewModel.getUsefulKey(), minimumVoteCount());
        } else {
            return reviewModel.trimByVotes(reviewModel.getUsefulKey(), spinner.getValue());
        }
    }

    /**
     * <!-- Unsure
     * @param pce 
     */
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        try {
            dsv.setModel(getUsefulModel());
            long newmin = minimumVoteCount();
            for (ChangeVotesListener cvl : listeners)
                cvl.votesChange(this, newmin);
        } catch (CapException ex) {
        }
    }

    public SpinButton getSpinButton() {
        return spinner;
    }

    /**
     * Retrieve the min useful votes counter from the spinner buttons
     * @return 
     */
    public long minimumVoteCount() {
        if (reviewModel == null) return 0;
        long minimum = (long)(spinner.getValue());
        
//        long minVotes = reviewModel.getMinimumVotesAsLong(reviewModel.getUsefulKey());
//        long maxVotes = reviewModel.getMaximumVotesAsLong(reviewModel.getUsefulKey());
        
//        long mvl = (long) ((maxVotes - minVotes) * minimum);
        
        return minimum;
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
        public void votesChange(UsefulReviewSelect sel, long newVotes);
    }

    
}
