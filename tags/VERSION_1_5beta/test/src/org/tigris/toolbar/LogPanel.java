/*
 * ActionTriggerScroll.java
 *
 * Created on 10 May 2003, 18:45
 */

package org.tigris.toolbar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Bob Tarling
 */
public class LogPanel extends JScrollPane {
    
    JPanel actionTriggerList;
    private int count;
    
    private static LogPanel instance = new LogPanel();
    
    public static LogPanel getInstance() {
        return instance;
    }
    
    /** Creates a new instance of ActionTriggerScroll */
    private LogPanel() {
        actionTriggerList = new JPanel(new GridLayout(0, 1));
        JPanel listContainer = new JPanel(new BorderLayout());
        listContainer.add(BorderLayout.NORTH, actionTriggerList);
        this.setViewportView(listContainer);
    }
    
    public void add(String descr) {
        descr = ++count + " " + descr;
        actionTriggerList.add(new JLabel(descr));
        doLayout();
        validate();
        if (getVerticalScrollBar() != null) {
            int maxScroll = getVerticalScrollBar().getMaximum();
            getVerticalScrollBar().setValue(maxScroll + 1);
        }
    }
}
