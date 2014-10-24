/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

//Methods for loading in yelp data
import capstone.model.Model;
import capstone.model.YelpModel;
import capstone.model.YelpModel.YelpModelLoaderUI;

//Methods for arranging and resizing containers
import java.awt.BorderLayout;

//<!-- to do
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;

//Methods for system input & output through datastreams
import java.io.File;

//Methods for creating GUI
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

/**
 *
 */
public class YelpModelLoader extends Thread implements YelpModelLoaderUI {
    
    /**
     * <!-- to do
     */
    public interface YelpModelLoadListener {
        public void modelIsLoaded(Model model);
    }
    
    private YelpModel model;
    private JFrame loaderView;
    private YelpModelLoadListener app;
    private JList<String> log;
    private DefaultListModel<String> logModel;
    private JProgressBar progress;
    
    private File directory = null;
    
    /**
     * Creates dataset loading pop up window
     * @param directory
     * @param app 
     */
    public YelpModelLoader(File directory, YelpModelLoadListener app) {
        this.app = app;
        
        this.directory = directory;
        if (directory == null) directory = new File("/home/mark/Downloads/yelp/");//<!-- redundent
        
        loaderView = new JFrame("Loading Yelp data set...");
//        loaderView.setPreferredSize(new Dimension(640, 256));
        loaderView.setMinimumSize(new Dimension(640, 128));
        
        //set favicon
        loaderView.setIconImage(new ImageIcon(getClass().getResource("server.png")).getImage());

        JPanel pane = new JPanel();
        pane.setLayout(new BorderLayout());
        Border inner, outer;
        outer = BorderFactory.createEmptyBorder(16,16,16,16);
        inner = BorderFactory.createLineBorder(Color.BLACK);
        pane.setBorder(BorderFactory.createCompoundBorder(inner, outer));
        loaderView.setContentPane(pane);
        
        
        logModel = new DefaultListModel<>();
        log = new JList<>(logModel);
        JScrollPane jsp = new JScrollPane(log); 

        log.setPreferredSize(new Dimension(256, 256));
        jsp.setPreferredSize(new Dimension(256, 256));
        pane.add(jsp, BorderLayout.CENTER);
        
        progress = new JProgressBar();
        pane.add(progress, BorderLayout.SOUTH);

        progress.setPreferredSize(new Dimension(32, 32));
        progress.setIndeterminate(true);
        
        loaderView.setType(Window.Type.POPUP);
        pane.setBackground(Color.DARK_GRAY);
        log.setBackground(Color.DARK_GRAY);
        progress.setBackground(Color.DARK_GRAY);
        log.setOpaque(true);
        log.setForeground(Color.WHITE);
        log.setAutoscrolls(true);
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        
        loaderView.pack();
        Point midPoint = new Point(screen.width/2 - loaderView.getWidth()/2, screen.height/2 - loaderView.getHeight()/2);

        loaderView.setLocation(midPoint);
    }
    
    /**
     * reads in dataset
     */
    public void run() {
        
        loaderView.setVisible(true);
        
        update("Reading from JSON files...");
        model = new YelpModel(directory.toString(), this);
        app.modelIsLoaded(model);
        loaderView.dispose();
    }

    /**
     * <!-- not sure what this does?
     * @param txt 
     */
    public void update(String txt) {
        logModel.addElement(txt);
        loaderView.pack();
        log.repaint();
    }
    
}
