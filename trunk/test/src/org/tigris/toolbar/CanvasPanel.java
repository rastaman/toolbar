/*
 * ActionCanvas.java
 *
 * Created on 10 May 2003, 18:15
 */

package org.tigris.toolbar;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * An example canvas to demonstrate the previously selected actions
 *
 * @author Bob Tarling
 */
public class CanvasPanel extends JPanel {
    
    private static CanvasPanel instance = new CanvasPanel();
    
    public static CanvasPanel getInstance() {
        return instance;
    }
    
    /** Creates a new instance of ActionCanvas */
    private CanvasPanel() {
        setLayout(new GridLayout(10,10));
        for (int i=0; i < 100; ++i) {
            add(new JButton());
        }
    }
}
