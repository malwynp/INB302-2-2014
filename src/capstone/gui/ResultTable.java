/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.gui;

import capstone.CapException;
import capstone.testsuite.ReviewTest;
import capstone.testsuite.TestResult;
import capstone.testsuite.TestResult.ResultRecord;
import capstone.testsuite.TestSuite;
import capstone.yelpmodel.Review;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import org.json.simple.JSONObject;

/**
 *
 * @author mark
 */
public class ResultTable extends JPanel {
    
    private TestResult model;
    private JTable table;
    private JScrollPane jsp;
    
    public ResultTable() {
        setLayout(new BorderLayout());
        table = new JTable();
        
        add(jsp = new JScrollPane(table), BorderLayout.CENTER);
        setPreferredSize(new Dimension(96, 96));
    }
    
    public TestResult getModel() {
        return model;
    }
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
                return model.getTestCount();// + 1;
            }
            
            @Override
            public String getColumnName(int x) {
                ResultRecord[] record = model.getRecord(0);
                if (record == null || record.length == 0) return null;
//                if (x == 0) return "Review Text";
                return ReviewTest.niceClassName(record[x].getTest().getClass().getSimpleName(), false);
//                return ReviewTest.niceClassName(record[x-1].getTest().getClass().getSimpleName(), false);
            }

            @Override
            public Object getValueAt(int y, int x) {
                ResultRecord[] record = model.getRecord(y);
                if (record == null || record.length == 0) return null;
                JSONObject obj = record[0].getObject();
                double rounded = record[x].getDouble();
                rounded = (double)((int)(rounded * 100)) / 100d;
                return rounded;
//                if (x == 0) {
//                    return obj.get("text");
//                } else {
//                    return record[x - 1].getDouble();
//                }
            }
            
        });
        
        for (int i = 0; i < table.getModel().getColumnCount(); i++)
            table.sizeColumnsToFit(i);
    }
    
    public void generateModel(TestSuite suite, Review reviews) {
        if (suite == null || reviews == null) {
            setModel(null);
            return;
        }
        try {
            setModel(suite.testAndStoreAllRecords(reviews));
        } catch (CapException ex) {
        }
    }
    
}
