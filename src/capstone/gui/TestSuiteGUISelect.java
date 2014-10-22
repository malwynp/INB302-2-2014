/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

//Methods for loading test suites
import capstone.testsuite.ReviewTest;
import capstone.testsuite.TestSuite;

//Methods for loading in yelp data
import capstone.yelpmodel.Review;

//<!-- to do
import java.awt.Component;
import java.awt.GridLayout;

//Methods for controling height and width of a abstract window toolkit component
import java.awt.Dimension;

//Methods for system input & output through datastreams
import java.io.File;

//<!-- to do
import java.lang.reflect.Constructor;

//Methods for handling web adresses
import java.net.URL;

//<!-- to do
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

//Methods for creating GUI
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//<!-- For JSON objects or serilization
import org.json.simple.JSONObject;

/**
 *
 * 
 */
public class TestSuiteGUISelect extends JPanel {
    
    private JScrollPane sp;
    private JList<Class> list;
    private DefaultListModel<Class> dlm;
    private HashMap<Class, Boolean> selection;
    
    /**
     * 
     */
    public TestSuiteGUISelect() {
        super();
        
        dlm = new DefaultListModel<>();
        list = new JList(dlm);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selection = new HashMap<>();
        
        list.addListSelectionListener(new ListSelectionListener() {
            
            /**
             * <!-- to do
             * @param lse 
             */
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                
                Class c = list.getSelectedValue();
                if (c == null) return;

                if (lse.getValueIsAdjusting()) return;

                if (!selection.containsKey(c)) {
                    selection.put(c, true);
                } else {
                    selection.put(c, !selection.get(c));
                }
            }
            
        });
        
        list.setCellRenderer(new ListCellRenderer() {
            
            /**
             * creates check box for testsuite select<!-- to do
             * @param jlist
             * @param e
             * @param i
             * @param selected
             * @param focused
             * @return 
             */
            @Override
            public Component getListCellRendererComponent(JList jlist, Object e, int i, boolean selected, boolean focused) {
                JPanel pane = new JPanel();
                
                String str = e.toString().substring(e.toString().lastIndexOf(".") + 1);
                
                pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
                str = ReviewTest.niceClassName(str, false);
                JCheckBox cb = new JCheckBox(str);
                cb.setSelected(false);

                Class sv = dlm.get(i);
                if (selection.containsKey(sv)) { 
                    cb.setSelected(selection.get(sv));
                }
                                
                pane.add(cb);
                
                return pane;
            }
        
        });
        
        setLayout(new GridLayout(1,1));
        setBorder(BorderFactory.createTitledBorder("Feature Test Selection"));
        
        sp = new JScrollPane(list);
        add(sp);
        
        // Populate list with tests
        try {
            Class cc[] = getClasses("capstone.testsuite", ReviewTest.class);
            for (Class c : cc) {
                if (!c.getSimpleName().startsWith("S")) continue;//if its not a test
                
                try {
                    ReviewTest test = (ReviewTest) c.newInstance();
                    JSONObject dumRev = new JSONObject();
                    dumRev.put("text", "");
                    Review dummy = new Review(new JSONObject[]{dumRev});
                    test.getScore(dummy, 0);
                    dlm.addElement(c);
                    selection.put(c, Boolean.TRUE);
                } catch (UnsupportedOperationException eu) {
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        list.setModel(dlm);
    }
    
    // Adapted from code:
    // http://dzone.com/snippets/get-all-classes-within-package
    /**
     * Retrieves classes from capstone.testsuite package
     * @param pkg
     * @param filter
     * @return <!-- to do
     * @throws Exception 
     */
    private static Class[] getClasses(String pkg, Class filter) throws Exception {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        String path = pkg.replace(".", "/");
        Enumeration<URL> res = cl.getResources(path);
        List<File> dirs = new ArrayList<>();
        
        while (res.hasMoreElements()) {
            dirs.add(new File(res.nextElement().getFile()));
        }
        
        List<Class> classes = new ArrayList<>();
        for (File f : dirs) {
            classes.addAll(findClasses(f, pkg, filter));
        }
        
        return classes.toArray(new Class[classes.size()]);
    }
    
    /**
     * Finds classes within the capstone.testsuite package
     * @param dir
     * @param pkg
     * @param filter
     * @return 
     */
    private static List<Class> findClasses(File dir, String pkg, Class filter) {
        List<Class> classes = new ArrayList<>();
        if (!dir.exists()) return classes;
        
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                classes.addAll(findClasses(f, pkg + "." + f.getName(), filter));
            } else if (f.getName().endsWith(".class")) {
                try {
                    String claStr = pkg + "." + f.getName().substring(0, f.getName().length() - 6);
                    Class c = Class.forName(claStr);
                    
                    if (filter == null || (filter.isAssignableFrom(c) && !c.equals(filter))) {
                        classes.add(c);
                    } else {
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return classes;
    }
    
    /**
     * Create window box for testsuite selection
     * @param args 
     */
    public static void main(String args[]) {
        JFrame frame = new JFrame("Derp") {
            @Override
            public void dispose() {
                TestSuiteGUISelect tsguis = (TestSuiteGUISelect)getContentPane();
                System.out.println(tsguis.generateTestSuite());
                super.dispose();
                System.exit(0);
            }
        };
        frame.setPreferredSize(new Dimension(256, 256));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(new TestSuiteGUISelect());
        frame.pack();
        frame.setVisible(true);
        
    }
    
    /**
     * Add test suites to ArrayList<!-- is this right?
     * @return Array of test suites
     */
    public TestSuite generateTestSuite() {
        List<ReviewTest> tests = new ArrayList<>();
        
        for (int i = 0; i < dlm.size(); i++) {
            Class c = dlm.get(i);
            if (!selection.containsKey(c)) continue;
            if (!selection.get(c)) continue;
            
            try {
                Constructor<?> ct = c.getConstructor();
                Object obj = ct.newInstance(new Object[] { });
                tests.add((ReviewTest) obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return new TestSuite(tests.toArray(new ReviewTest[tests.size()]));
    }
    
}
