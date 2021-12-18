package net.sf.cotelab.jfxdemo;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * This demonstrates WebView inside a Swing app.
 */
public class JFXInSwingDemo {
	protected static Dimension theDimension = new Dimension(500, 500);

    private static void initAndShowGUI() {
        // This method is invoked on Swing thread
        final JFrame frame = new JFrame("FX");
        JPanel jPanel = new JPanel(new java.awt.BorderLayout());
        final JFXPanel fxPanel = new JFXPanel();
        
        jPanel.setMinimumSize(theDimension);
        jPanel.add(fxPanel, java.awt.BorderLayout.CENTER);
        frame.getRootPane().setContentPane(jPanel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        fxPanel.setMaximumSize(theDimension);
        fxPanel.setMaximumSize(theDimension);
        fxPanel.setPreferredSize(theDimension);
        
        frame.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel, frame);
            }
        });
    }

    private static void initFX(JFXPanel fxPanel, final JFrame frame) {
        // This method is invoked on JavaFX thread
        Scene scene = createScene();
        
        fxPanel.setScene(scene);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame.getRootPane().setMaximumSize(theDimension);
                frame.getRootPane().setMinimumSize(theDimension);
                frame.getRootPane().setPreferredSize(theDimension);
                
                frame.pack();
            }
        });
    }

    private static Scene createScene() {
		
		
    	//webview object creation
    	//web engine object creation
    	WebView myview = new WebView();
    	// setting min height
    	myview.minHeight(1050);
    	// setting preferred width
    	myview.prefWidth(1950);
    	// setting pref height
    	myview.prefHeight(1070);
    	//setting min width
    	myview.minWidth(1050);
    	final WebEngine mywebEngine = myview.getEngine();
    	mywebEngine.load("http://www.google.com");
    	
    	VBox mybox = new VBox(myview);
		 
		 
		Scene myscene = new Scene(mybox, 500, 500);
		
		return myscene;
	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }
}
