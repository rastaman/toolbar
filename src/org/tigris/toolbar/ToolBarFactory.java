/*
 * ToolBarFactory.java
 *
 * Created on 24 May 2003, 18:45
 */

package org.tigris.toolbar;

import java.awt.Component;
import javax.swing.Action;
import javax.swing.JToolBar;
import org.tigris.toolbutton.PopupToolBoxButton;

/**
 *
 * @author Bob Tarling
 */
public class ToolBarFactory {
    
    /** Creates a new instance of ToolBarFactory */
    private ToolBarFactory() {
    }

    public static JToolBar createToolBar(Object actions[]) {
        JToolBar tb = new ToolBar();
        addActionsToToolBar(tb, actions);
        return tb;
    }
    
    public static JToolBar createToolBar(boolean rollover, Object actions[]) {
        JToolBar tb = new ToolBar();
        if (rollover) {
            tb.putClientProperty("JToolBar.isRollover",  Boolean.TRUE);
        } else {
            tb.putClientProperty("JToolBar.isRollover",  Boolean.FALSE);
        }
        addActionsToToolBar(tb, actions);
        return tb;
    }
    
    public static JToolBar createToolBar(boolean rollover, Object actions[], boolean floatable) {
        JToolBar tb = new ToolBar();
        if (rollover) {
            tb.putClientProperty("JToolBar.isRollover",  Boolean.TRUE);
        } else {
            tb.putClientProperty("JToolBar.isRollover",  Boolean.FALSE);
        }
        tb.setFloatable(floatable);
        addActionsToToolBar(tb, actions);
        return tb;
    }
    
    public static JToolBar createToolBar(String name, Object actions[]) {
        JToolBar tb = new ToolBar();
        tb.setName(name);
        addActionsToToolBar(tb, actions);
        return tb;
    }
    
    public static JToolBar createToolBar(String name, Object actions[], boolean floatable) {
        JToolBar tb = new ToolBar(name);
        tb.setName(name);
        tb.setFloatable(floatable);
        addActionsToToolBar(tb, actions);
        return tb;
    }
    
    public static JToolBar createToolBar(boolean rollover, String name, Object actions[], boolean floatable) {
        JToolBar tb = new ToolBar();
        if (rollover) {
            tb.putClientProperty("JToolBar.isRollover",  Boolean.TRUE);
        } else {
            tb.putClientProperty("JToolBar.isRollover",  Boolean.FALSE);
        }
        tb.setName(name);
        tb.setFloatable(floatable);
        addActionsToToolBar(tb, actions);
        return tb;
    }
    
    /**
     * <p>Initialize the toolbar with buttons required for a specific diagram</p>
     * @param toolBar The toolbar to which to add the buttons.
     */
    private static void addActionsToToolBar(JToolBar toolBar, Object actions[]) {
        
        for (int i=0; i < actions.length; ++i) {
            Object o = actions[i];
            if (o == null) {
                toolBar.addSeparator();
            } else if (o instanceof Action) {
                toolBar.add((Action)o);
            } else if (o instanceof Object[]) {
                Object[] subActions = (Object[])o;
                toolBar.add(buildPopupToolBoxButton(subActions));
            } else if (o instanceof Component) {
                toolBar.add((Component)o);
            }
        }
    }

    private static PopupToolBoxButton buildPopupToolBoxButton(Object[] actions) {
        PopupToolBoxButton toolBox = null;
        for (int i=0; i < actions.length; ++i) {
            if (actions[i] instanceof Action) {
                Action a = (Action)actions[i];
                if (toolBox == null) {
                    toolBox = new PopupToolBoxButton(a, 0, 1);
                }
                toolBox.add(a);
            } else if (actions[i] instanceof Object[]) {
                Object[] actionRow = (Object[])actions[i];
                for (int j=0; j < actionRow.length; ++j) {
                    Action a = (Action)actionRow[j];
                    if (toolBox == null) {
                        int cols = actionRow.length;
                        toolBox = new PopupToolBoxButton(a, 0, cols);
                    }
                    toolBox.add(a);
                }
            }
        }
        return toolBox;
    }
}
