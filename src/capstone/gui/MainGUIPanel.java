/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import capstone.CapException;
import capstone.gui.UsefulReviewSelect.ChangeVotesListener;
import capstone.testsuite.TestSuite;
import capstone.yelpmodel.ARFFWriter;
import capstone.yelpmodel.AttributeWriter;
import capstone.yelpmodel.Business;
import capstone.yelpmodel.Model;
import capstone.yelpmodel.NanModel;
import capstone.yelpmodel.NanWriter;
import capstone.yelpmodel.Review;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class MainGUIPanel extends javax.swing.JPanel {

    /**
     * Creates new form MainGUIPanel
     */
    public MainGUIPanel() {
        initComponents();
        
        businessSelectView.addListSelectionListener(businessListUpdate);
        usefulReviewSelection.addListener(helpfulLabelUpdate);
    }
    private ChangeVotesListener helpfulLabelUpdate = new ChangeVotesListener() {
            public void votesChange(long newVotes) {
                helpfulVotesLabel.setText("Reviews with 'helpful' votes >= " + newVotes);
            }
        };
    private ListSelectionListener businessListUpdate = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent lse) {
                try {
                    businessSelected(lse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

    private CapstoneApplication app;
    MainGUIPanel(CapstoneApplication app) {
        super();
        this.app = app;
        initComponents();
        businessSelectView.addListSelectionListener(businessListUpdate);
        usefulReviewSelection.addListener(helpfulLabelUpdate);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainTabs = new javax.swing.JTabbedPane();
        businessTab = new javax.swing.JPanel();
        businessCategorySelection = new javax.swing.JComboBox();
        jSplitPane1 = new javax.swing.JSplitPane();
        businessSelectView = new capstone.gui.DataSetView();
        jSONDetailView1 = new capstone.gui.JSONDetailView();
        jPanel7 = new javax.swing.JPanel();
        nextButton = new javax.swing.JButton();
        reviewTab = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        uselessReviewSelection = new capstone.gui.DataSetView();
        btnNanTestExport = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        helpfulVotesLabel = new javax.swing.JLabel();
        usefulReviewSelection = new capstone.gui.UsefulReviewSelect();
        btnNanTrainingExport = new javax.swing.JButton();
        jSONDetailView2 = new capstone.gui.JSONDetailView();
        jPanel10 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        testTab = new javax.swing.JPanel();
        testSuiteGUISelect1 = new capstone.gui.TestSuiteGUISelect();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        trainingSetResults = new capstone.gui.ResultTable();
        jPanel9 = new javax.swing.JPanel();
        testSetResults = new capstone.gui.ResultTable();
        jPanel11 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        exportButtonTestWRFF = new javax.swing.JButton();
        exportButtonTrainingWRFF = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(1, 1));

        mainTabs.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        businessTab.setLayout(new java.awt.BorderLayout());

        businessCategorySelection.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        businessCategorySelection.setMinimumSize(new java.awt.Dimension(64, 64));
        businessCategorySelection.setPreferredSize(new java.awt.Dimension(64, 64));
        businessCategorySelection.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                businessCategorySelectionEvent(evt);
            }
        });
        businessCategorySelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                businessCategorySelectionActionPerformed(evt);
            }
        });
        businessTab.add(businessCategorySelection, java.awt.BorderLayout.NORTH);

        jSplitPane1.setDividerLocation(230);
        jSplitPane1.setDividerSize(8);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        businessSelectView.setSortKey("review_count");
        businessSelectView.setSorted(true);
        jSplitPane1.setLeftComponent(businessSelectView);

        jSONDetailView1.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        jSplitPane1.setRightComponent(jSONDetailView1);

        businessTab.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jPanel7.setMaximumSize(new java.awt.Dimension(64, 64));
        jPanel7.setPreferredSize(new java.awt.Dimension(64, 64));

        nextButton.setBackground(new java.awt.Color(51, 153, 0));
        nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/capstone/gui/go-next.png"))); // NOI18N
        nextButton.setText("Select Review Sets");
        nextButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(541, Short.MAX_VALUE)
                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nextButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        businessTab.add(jPanel7, java.awt.BorderLayout.SOUTH);

        mainTabs.addTab("Select Business", businessTab);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jSplitPane2.setDividerLocation(320);
        jSplitPane2.setDividerSize(8);
        jSplitPane2.setResizeWeight(0.5);
        jSplitPane2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jSplitPane2ComponentResized(evt);
            }
        });

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Reviews (voted helpful)");
        jLabel1.setMinimumSize(new java.awt.Dimension(32, 32));
        jLabel1.setOpaque(true);
        jPanel4.add(jLabel1, java.awt.BorderLayout.NORTH);
        jLabel1.getAccessibleContext().setAccessibleName("Reviews voted Helpful");

        uselessReviewSelection.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        uselessReviewSelection.setKeys(new String[] {"text"});
        jPanel4.add(uselessReviewSelection, java.awt.BorderLayout.CENTER);

        btnNanTestExport.setText("Export Test Data File (Unvoted)");
        btnNanTestExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNanTestExportActionPerformed(evt);
            }
        });
        jPanel4.add(btnNanTestExport, java.awt.BorderLayout.PAGE_END);

        jSplitPane2.setLeftComponent(jPanel4);

        jPanel5.setLayout(new java.awt.BorderLayout());

        helpfulVotesLabel.setFont(new java.awt.Font("DejaVu Sans", 1, 13)); // NOI18N
        helpfulVotesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        helpfulVotesLabel.setText("Reviews (NOT voted helpful)");
        helpfulVotesLabel.setMinimumSize(new java.awt.Dimension(32, 32));
        helpfulVotesLabel.setOpaque(true);
        jPanel5.add(helpfulVotesLabel, java.awt.BorderLayout.NORTH);

        usefulReviewSelection.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel5.add(usefulReviewSelection, java.awt.BorderLayout.CENTER);

        btnNanTrainingExport.setText("Export Training Data File (Voted useful)");
        btnNanTrainingExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNanTrainingExportActionPerformed(evt);
            }
        });
        jPanel5.add(btnNanTrainingExport, java.awt.BorderLayout.PAGE_END);

        jSplitPane2.setRightComponent(jPanel5);

        jPanel6.add(jSplitPane2, java.awt.BorderLayout.CENTER);

        jSONDetailView2.setBackground(new java.awt.Color(91, 91, 95));
        jSONDetailView2.setPreferredSize(new java.awt.Dimension(96, 96));
        jPanel6.add(jSONDetailView2, java.awt.BorderLayout.PAGE_START);

        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton5.setBackground(new java.awt.Color(51, 153, 0));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/capstone/gui/go-next.png"))); // NOI18N
        jButton5.setText("Examine Text Features");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton5);

        jPanel6.add(jPanel10, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout reviewTabLayout = new javax.swing.GroupLayout(reviewTab);
        reviewTab.setLayout(reviewTabLayout);
        reviewTabLayout.setHorizontalGroup(
            reviewTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reviewTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
                .addContainerGap())
        );
        reviewTabLayout.setVerticalGroup(
            reviewTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reviewTabLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabs.addTab("Business Reviews", reviewTab);

        testTab.setLayout(new java.awt.BorderLayout());
        testTab.add(testSuiteGUISelect1, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trainingSetResults, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trainingSetResults, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Training set (voted helpful)", jPanel8);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(testSetResults, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(testSetResults, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Test set (not voted helpful)", jPanel9);

        testTab.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButton2.setBackground(new java.awt.Color(51, 153, 0));
        jButton2.setText("Run tests");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton2);

        exportButtonTestWRFF.setBackground(new java.awt.Color(0, 153, 153));
        exportButtonTestWRFF.setText("Export Test ARFF file...");
        exportButtonTestWRFF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonTestWRFFActionPerformed(evt);
            }
        });
        jPanel11.add(exportButtonTestWRFF);

        exportButtonTrainingWRFF.setBackground(new java.awt.Color(0, 153, 153));
        exportButtonTrainingWRFF.setText("Export Training ARFF file...");
        exportButtonTrainingWRFF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonTrainingWRFFActionPerformed(evt);
            }
        });
        jPanel11.add(exportButtonTrainingWRFF);

        testTab.add(jPanel11, java.awt.BorderLayout.PAGE_END);

        mainTabs.addTab("Select Review Tests", testTab);

        add(mainTabs);
    }// </editor-fold>//GEN-END:initComponents

    private void businessCategorySelectionEvent(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_businessCategorySelectionEvent
        if (evt.getItem() == null) return;
        
        try {
            Business bus = model.getBusinesses();
            bus = bus.trimByCategory(new String[] { (String)evt.getItem() });
            businessSelectView.setModel(bus);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_businessCategorySelectionEvent

    private void businessCategorySelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_businessCategorySelectionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_businessCategorySelectionActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        int thisTab = mainTabs.getSelectedIndex();
        if (thisTab + 1 < mainTabs.getTabCount()) thisTab++;
        mainTabs.setSelectedIndex(thisTab);
    }//GEN-LAST:event_nextButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        TestSuite genSuite = this.testSuiteGUISelect1.generateTestSuite();
        if (genSuite == null) return;
        
        Review testSet = (Review) uselessReviewSelection.getModel();
        Review trainSet = usefulReviewSelection.getUsefulModel();
        
        testSetResults.generateModel(genSuite, testSet);
        trainingSetResults.generateModel(genSuite, trainSet);
        
    }//GEN-LAST:event_jButton2ActionPerformed


    private void jSplitPane2ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jSplitPane2ComponentResized
        jSplitPane2.setDividerLocation(0.5);
    }//GEN-LAST:event_jSplitPane2ComponentResized

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        nextButtonActionPerformed(evt);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void exportButtonTestWRFFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonTestWRFFActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Export Test WRFF file");
        jfc.setSelectedFile(new File("test.arff"));
        jfc.setFileFilter(new FileNameExtensionFilter("ARFF File", "arff"));
        
        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            FileOutputStream fos = null;

            try {
                ARFFWriter writer = new ARFFWriter(f.getName(), testSetResults.getModel());
                writer.addAttribute(new AttributeWriter("class", "{1,0}") {
                    @Override
                    public String getAttributeFor(JSONObject obj) {
                        return "0";
                    }
                });
                fos = new FileOutputStream(f);
                writer.writeToStream(fos);
                fos.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
            } finally {
            }
            
        }
        
    }//GEN-LAST:event_exportButtonTestWRFFActionPerformed

    private void exportButtonTrainingWRFFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonTrainingWRFFActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Export Training WRFF file");
        jfc.setSelectedFile(new File("training.arff"));
        jfc.setFileFilter(new FileNameExtensionFilter("ARFF File", "arff"));
        
        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            FileOutputStream fos = null;

            try {
                ARFFWriter writer = new ARFFWriter(f.getName(), trainingSetResults.getModel());
                writer.addAttribute(new AttributeWriter("class", "{1,0}") {
                    @Override
                    public String getAttributeFor(JSONObject obj) {
                        return "1";
                    }
                });
                fos = new FileOutputStream(f);
                writer.writeToStream(fos);
                fos.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
            } finally {
            }
        }
    }//GEN-LAST:event_exportButtonTrainingWRFFActionPerformed

    private void btnNanTestExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNanTestExportActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Export Test NanModel file");
        jfc.setSelectedFile(new File("test.txt"));
        jfc.setFileFilter(new FileNameExtensionFilter("NanModel file", "txt"));
        
        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                NanWriter nw = new NanWriter(uselessReviewSelection.getModel());
                FileOutputStream fos = new FileOutputStream(jfc.getSelectedFile());
                nw.writeToStream("@Test review dataset", fos);
                fos.close();
            }
            
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error has occurred in trying to export this file:\n" + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnNanTestExportActionPerformed

    private void btnNanTrainingExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNanTrainingExportActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Export Training NanModel file");
        jfc.setSelectedFile(new File("training.txt"));
        jfc.setFileFilter(new FileNameExtensionFilter("NanModel file", "txt"));
        
        if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                NanWriter nw = new NanWriter(uselessReviewSelection.getModel());
                FileOutputStream fos = new FileOutputStream(jfc.getSelectedFile());
                nw.writeToStream("@Training review dataset", fos);
                fos.close();
            }
            
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An error has occurred in trying to export this file:\n" + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnNanTrainingExportActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNanTestExport;
    private javax.swing.JButton btnNanTrainingExport;
    private javax.swing.JComboBox businessCategorySelection;
    private capstone.gui.DataSetView businessSelectView;
    private javax.swing.JPanel businessTab;
    private javax.swing.JButton exportButtonTestWRFF;
    private javax.swing.JButton exportButtonTrainingWRFF;
    private javax.swing.JLabel helpfulVotesLabel;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private capstone.gui.JSONDetailView jSONDetailView1;
    private capstone.gui.JSONDetailView jSONDetailView2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane mainTabs;
    private javax.swing.JButton nextButton;
    private javax.swing.JPanel reviewTab;
    private capstone.gui.ResultTable testSetResults;
    private capstone.gui.TestSuiteGUISelect testSuiteGUISelect1;
    private javax.swing.JPanel testTab;
    private capstone.gui.ResultTable trainingSetResults;
    private capstone.gui.UsefulReviewSelect usefulReviewSelection;
    private capstone.gui.DataSetView uselessReviewSelection;
    // End of variables declaration//GEN-END:variables

    
    private Model model = null;
    void modelIsLoaded(Model model) {
        this.model = model;
        if (model != null) {
            
            System.out.println(model);
            if (model instanceof NanModel) {
                mainTabs.remove(businessTab);
                try {
                    Review useless = model.getReviews();
                    Review useful = model.getReviews();
                    
                    uselessReviewSelection.setModel(useless);
                    usefulReviewSelection.setModel(useful);
                } catch (Exception e) {
                }
            } else {
                if (mainTabs.indexOfTabComponent(businessTab) == -1)
                    mainTabs.insertTab("Select Business", null, businessTab, "", 0);
                businessCategorySelection.setModel(app.getAllBusinessCategories());
            }
            
            mainTabs.setSelectedIndex(0);
        }
    }
    
    public void businessSelected(ListSelectionEvent lse) throws CapException {

        uselessReviewSelection.setModel(null);
        usefulReviewSelection.setModel(null);
        JSONObject obj;
        
        if (model instanceof NanModel) {
            Review useless = model.getReviews();
            Review useful = model.getReviews();

            uselessReviewSelection.setModel(useless);
            usefulReviewSelection.setModel(useful);
        } else {
            if ((obj = businessSelectView.getSelected()) == null) return;
            
            String businessID = (String)(obj.get("business_id"));
            Review rSet = model.getReviews().getReviewsForBusiness(businessID);

            uselessReviewSelection.setModel(rSet.trimByVotes("useful", -1));
            usefulReviewSelection.setModel(rSet);

            String ignoreKeys[] = new String[] {
                "type", "state", "open", "neighborhoods", "latitude", "longitude"
            };

            jSONDetailView1.addIgnoreKeys(ignoreKeys);
            jSONDetailView1.setModel(obj);
            jSONDetailView2.addIgnoreKeys(ignoreKeys);
            jSONDetailView2.setModel(obj);
        }
        

    }
    
}
