/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package capstone.gui;

import capstone.yelpmodel.YelpModel;
import capstone.yelpmodel.YelpModel.YelpModelLoaderUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

/**
 *
 * @author mark
 */
public class YelpModelLoader extends Thread implements YelpModelLoaderUI {
    
    public interface YelpModelLoadListener {
        public void modelIsLoaded(YelpModel model);
    }
    
    private YelpModel model;
    private JFrame loaderView;
    private YelpModelLoadListener app;
    private JList<String> log;
    private DefaultListModel<String> logModel;
    private JProgressBar progress;
    
    public YelpModelLoader(YelpModelLoadListener app) {
        this.app = app;
        
        loaderView = new JFrame("Loading Yelp data set...");
//        loaderView.setPreferredSize(new Dimension(640, 256));
        loaderView.setMinimumSize(new Dimension(640, 128));

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
        
        loaderView.setType(Window.Type.UTILITY);
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
    
    public void run() {
        
        loaderView.setVisible(true);
        
        File f = new File("capstone_yelp_2p31.ser");

        try {
            update("Reading from serialised object file...");
            model = null;
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            model = (YelpModel) (ois.readObject());
        } catch (Exception e) {
            e.printStackTrace();
            update("Reading from JSON files...");
            model = new YelpModel("/home/mark/Downloads/yelp/", this);

            try {
                update("Saving serialised data set...");

                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                model.forget();
                oos.writeObject(model);
                oos.close();
                fos.close();
            } catch (IOException ex) {
            }
        }
        
        finally {
            app.modelIsLoaded(model);
            loaderView.dispose();
        }
    }

    public void update(String txt) {
        logModel.addElement(txt);
        loaderView.pack();
        log.repaint();
    }
    
}
