/*
 * ToolBarFactory.java
 *
 * Created on 24 May 2003, 18:45
 */

package org.tigris.toolbar;

import java.awt.Component;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.tigris.toolbutton.PopupToolBoxButton;

/**
 * A factory class for creating new instances of toolbars
 *
 * @author Bob Tarling
 * @stereotype utility
 */
public class ToolBarFactory {
    
    /** Cannot construct a utility class */
    private ToolBarFactory() {
    }

    /**
     * <p>Create a new toolbar containing buttons and other controls based
     * on the given array.</p>
     * <p>The array elements can be on of the following types.</p>
     * <table>
     * <tr><td><code>null</code></td>
     * <td>Results in a toolbar seperator being created</code></td>
     * <tr><td><code>Action</code></td>
     * <td>Results in a button being created which is listening
     * to the changes on the action and will perform the action
     * when pressed</td></tr>
     * <tr><td><code>Component</code></td>
     * <td>Will place the component on the toolbar</td></tr>
     * </table>
     * @param items the array of elements representing toolbar items
     */
    public static JToolBar createToolBar(Object items[]) {
        return createToolBar(false/*rollover*/, "", items, true/*floatable*/);
    }
    
    /**
     * <p>Create a new toolbar containing buttons and other controls based
     * on the given array with the given rollover effect.</p>
     * @param rollover true if buttons are to be shown with rollover effect
     * @param items the array of elements representing toolbar items
     */
    public static JToolBar createToolBar(boolean rollover, Object items[]) {
        return createToolBar(rollover, items, true/*floatable*/);
    }
    
    /**
     * <p>Create a new toolbar containing buttons and other controls based
     * on the given array with the given rollover effect and float style.</p>
     * @param rollover true if buttons are to be shown with rollover effect
     * @param items the array of elements representing toolbar items
     * @param floatable true if the toolbar can be dragged into a floating
     *                  position
     */
    public static JToolBar createToolBar(boolean rollover, Object items[], boolean floatable) {
        return createToolBar(rollover, "", items, floatable);
    }
    
    /**
     * <p>Create a new toolbar containing buttons and other controls based
     * on the given array with the given rollover effect and float style.</p>
     * @param rollover true if buttons are to be shown with rollover effect
     * @param items the array of elements representing toolbar items
     * @param floatable true if the toolbar can be dragged into a floating
     *                  position
     */
    public static JToolBar createToolBar(String name, Object items[]) {
        return createToolBar(false/*rollover*/, name, items, true/*floatable*/);
    }
    
    /**
     * <p>Create a named toolbar containing buttons and other controls based
     * on the given array with the given rollover effect and float style.</p>
     * @param name the name to place in the titlebar of the toolbar
     * @param items the array of elements representing toolbar items
     * @param floatable true if the toolbar can be dragged into a floating
     *                  position
     */
    public static JToolBar createToolBar(String name, Object items[], boolean floatable) {
        return createToolBar(false/*rollover*/, name, items, floatable);
    }
    
    /**
     * <p>Create a named toolbar containing buttons and other controls based
     * on the given array with the given rollover effect and float style.</p>
     * @param rollover true if buttons are to be shown with rollover effect
     * @param name the name to place in the titlebar of the toolbar
     * @param items the array of elements representing toolbar items
     * @param floatable true if the toolbar can be dragged into a floating
     *                  position
     */
    public static JToolBar createToolBar(boolean rollover, 
                                         String name, 
                                         Object items[], 
                                         boolean floatable) {
        JToolBar tb = new ToolBar(name);
        if (rollover) {
            tb.putClientProperty("JToolBar.isRollover",  Boolean.TRUE);
        } else {
            tb.putClientProperty("JToolBar.isRollover",  Boolean.FALSE);
        }
        tb.setFloatable(floatable);
        addItemsToToolBar(tb, items, rollover);
        return tb;
    }
    
    /**
     * <p>Populate the toolbar with the specific widgets required</p>
     * @param toolBar The toolbar to which to add the buttons.
     * @param items the items on which to base the buttons
     * @param rollover true if rollover effect is required.
     */
    private static void addItemsToToolBar(JToolBar toolBar, Object items[], boolean rollover) {
        
        for (int i=0; i < items.length; ++i) {
            Object o = items[i];
            if (o == null) {
                toolBar.addSeparator();
            } else if (o instanceof Action) {
                Action a = (Action)o;
                JButton button = toolBar.add(a);
            } else if (o instanceof Object[]) {
                Object[] subActions = (Object[])o;
                JButton button = buildPopupToolBoxButton(subActions, rollover);
                button.setBorderPainted(false);
                toolBar.add(button);
            } else if (o instanceof Component) {
                toolBar.add((Component)o);
            }
        }
    }

    private static PopupToolBoxButton buildPopupToolBoxButton(Object[] actions, boolean rollover) {
        PopupToolBoxButton toolBox = null;
        for (int i=0; i < actions.length; ++i) {
            if (actions[i] instanceof Action) {
                Action a = (Action)actions[i];
                if (toolBox == null) {
                    toolBox = new PopupToolBoxButton(a, 0, 1, rollover);
                }
                JButton button = toolBox.add(a);
            } else if (actions[i] instanceof Object[]) {
                Object[] actionRow = (Object[])actions[i];
                for (int j=0; j < actionRow.length; ++j) {
                    Action a = (Action)actionRow[j];
                    if (toolBox == null) {
                        int cols = actionRow.length;
                        toolBox = new PopupToolBoxButton(a, 0, cols, rollover);
                    }
                    JButton button = toolBox.add(a);
                }
            }
        }
        return toolBox;
    }
}
