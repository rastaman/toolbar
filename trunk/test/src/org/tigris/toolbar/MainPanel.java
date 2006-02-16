/*
 * MainPanel.java
 *
 * Created on 10 May 2003, 19:44
 */

package org.tigris.toolbar;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * The main test application panel made of of various sub panels
 *
 * @author Bob Tarling
 */
public class MainPanel extends JPanel {
    
    private static MainPanel instance = new MainPanel();
    
    /**
     * Get's the single instance of MainPanel.
     */
    public static MainPanel getInstance() {
        return instance;
    }
    
    /** Creates a new instance of MainPanel */
    private MainPanel() {
        JPanel infoPanels = new JPanel(new GridLayout());
        infoPanels.add(CommandQueuePanel.getInstance());
        infoPanels.add(LogPanel.getInstance());
        
        setLayout(new GridLayout());
        add(CanvasPanel.getInstance());
        add(infoPanels);
    }
}

