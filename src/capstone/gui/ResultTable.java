/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

//Method for displaying custom exception message
import capstone.CapException;
import capstone.model.JSONWrapper;

//Methods for loading test suites
import capstone.testsuite.ReviewTest;
import capstone.testsuite.TestResult;
import capstone.testsuite.TestResult.ResultRecord;
import capstone.testsuite.TestSuite;

//Methods for loading in yelp data
import capstone.model.Review;

//Methods for arranging and resizing containers
import java.awt.BorderLayout;

//Methods for controling height and width of a abstract window toolkit component
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

//Methods for creating GUI
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;

//<!-- For JSON objects or serialisation
import org.json.simple.JSONObject;

/**
 * Overarching class for interacting with the results table
 * 
 */
public class ResultTable extends JPanel {
    
    private TestResult model;
    private final JTable table;
    private final JScrollPane jsp;
    
    /**
     * Create results table panel
     */
    public ResultTable() {
        setLayout(new BorderLayout());
        table = new JTable();
        
        add(jsp = new JScrollPane(table), BorderLayout.CENTER);
        setPreferredSize(new Dimension(96, 96));
    }
    
    /**
     * Get current label
     * @return model
     */
    public TestResult getModel() {
        return model;
    }
    
    /**
     * Set results dataset and create room for the results <!--  
     * @param result 
     */
    public void setModel(TestResult result) {
        model = result;
        if (model == null) {
            table.setModel(null);
            return;
        }
        
        table.setModel(new AbstractTableModel() {

            @Override
            public int getRowCount() {
                return model.size();
            }

            @Override
            public int getColumnCount() {
                return model.getTestCount();
            }
            
            @Override
            public String getColumnName(int x) {
                ResultRecord[] record = model.getRecord(0);
                if (record == null || record.length == 0) return null;
                return ReviewTest.niceClassName(record[x].getTest().getClass().getSimpleName(), false);
            }

            @Override
            public Object getValueAt(int y, int x) {
                if (model == null) {
                    System.out.println("Model is null! This should not happen!");
                }
                ResultRecord[] record = model.getRecord(y);
                if (record == null || record.length == 0) {
                    System.out.println("Null record:" + y + "," + x + " from model " + model);
                    return null;
                }
                JSONObject obj = record[0].getObject();
                double rounded = record[x].getDouble();
                rounded = (double)((int)(rounded * 100)) / 100d;
                return rounded;
            }
            
        });
        
        for (int i = 0; i < table.getModel().getColumnCount(); i++)
            table.sizeColumnsToFit(i);

        table.setRowSorter(new TableRowSorter(table.getModel()));

    }
    
    /**
     * Runs the test suite and returns the results into the current model 
     * @param suite
     * @param reviews 
     * @return  
     */
    public TestResult generateModel(TestSuite suite, JSONWrapper reviews) {
        if (suite == null || reviews == null) {
            setModel(null);
            return getModel();
        }
        try {
            TestResult newModel = suite.testAndStoreAllRecords(reviews);
            setModel(newModel);
            return newModel;
        } catch (CapException ex) {
        }
        return getModel();
    }

    public TestResult getGeneratedModel(TestSuite suite, JSONWrapper reviews) {
        try {
            return suite.testAndStoreAllRecords(reviews);
        } catch (CapException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
