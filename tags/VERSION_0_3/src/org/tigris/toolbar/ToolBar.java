/*
 * ToolBar.java
 *
 * Created on 29 September 2002, 21:01
 */

package org.tigris.toolbar;

import java.awt.Insets;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.tigris.toolbar.toolbutton.ModalAction;
import org.tigris.toolbar.toolbutton.ModalButton;

/**
 * A toolbar class which assumes rollover effects and automatically gives tooltip
 * to any buttons created by adding an action.
 *
 * @author  Bob Tarling
 */
public class ToolBar extends JToolBar {
    
    String javaVersion;

    /** Creates a new instance of an un-named horizontal ToolBar
     */
    public ToolBar() {
        this("");
    }
    
    /** Creates a new instance of a horizontal ToolBar with the given name
     * @param name the title to display while floating
     */
    public ToolBar(String name) {
        this(name, HORIZONTAL);
    }
    
    /** Creates a new instance of ToolBar with the given name and orientation
     * @param name the title to display while floating
     * @param orientation HORIZONTAL or VERTICAL
     */
    public ToolBar(String name, int orientation) {
        super(name, orientation);
        javaVersion = System.getProperties().getProperty("java.specification.version");
        this.setMargin(new Insets(0,0,0,0));
    }
    
    /** Creates a new instance of an un-named ToolBar with the given orientation
     * @param orientation HORIZONTAL or VERTICAL
     */
    public ToolBar(int orientation) {
        this("", orientation);
    }

    /**
     * Add a new button to the toolbar with properties based on
     * the given action and triggering the given action.
     * @param action the action from which to create the button
     * @return the resulting <code>JButton</code> class
     */
    public JButton add(Action action) {
        JButton button;

        if (action instanceof ModalAction) {
            //System.out.println("Adding model action" + action);
            button = new ModalButton(action);
            add(button);
        } else {
            //System.out.println("Adding action" + action);
            //button = super.add(action);
            button = new ModalButton(action);
            add(button);
        }
        if (javaVersion.equals("1.3")) {
            button.setBorderPainted(false);
            // This is needed specifically for JDK1.3 on Windows & Motif
            button.setMargin(new Insets(0,0,0,0));
        }
        return button;
    }
}
