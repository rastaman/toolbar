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
import org.tigris.toolbutton.ModalAction;
import org.tigris.toolbutton.ModalButton;

/**
 * A toolbar class which assumes rollover effects and automatically gives tooltip
 * to any buttons created by adding an action.
 *
 * @author  Bob Tarling
 */
public class ToolBar extends JToolBar {
    
    /** Creates a new instance of Toolbar
     */
    public ToolBar() {
        super();
        this.setMargin(new Insets(0,0,0,0));
    }
    
    /** Creates a new instance of Toolbar
     */
    public ToolBar(String name) {
        super(name);
        this.setMargin(new Insets(0,0,0,0));
    }
    
    /** Creates a new instance of Toolbar
     */
    public ToolBar(String name, int orientation) {
        super(name, orientation);
        this.setMargin(new Insets(0,0,0,0));
    }
    
    /** Creates a new instance of Toolbar with the given orientation
     * @param orientation HORIZONTAL or VERTICAL
     */
    public ToolBar(int orientation) {
        super(orientation);
        this.setMargin(new Insets(0,0,0,0));
    }

    public void setRollover(boolean rollover) {
        // TODO Check for JDK1.4 before calling super class setRollover
        //super.setRollover(rollover);
        //this._rollover = rollover;
        // TODO Check for JDK1.4 before using Boolean.valueOf(rollover)
        //this.putClientProperty("JToolBar.isRollover", Boolean.valueOf(rollover));
        Boolean showRollover = Boolean.FALSE;
        if (rollover) showRollover = Boolean.TRUE;
        this.putClientProperty("JToolBar.isRollover",  showRollover);
    }
    
    
    public JButton add(Action action) {
        JButton button;

        if (action instanceof ModalAction) {
            System.out.println("Adding model action" + action);
            button = new ModalButton(action);
            add(button);
        } else {
            System.out.println("Adding action" + action);
            button = super.add(action);
        }
        return button;
    }
    
//    public JButton add(ModalAction action) {
//        System.out.println("Adding model action " + action);
//        ModalButton button = new ModalButton((Action)action);
//        add(button);
//        return button;
//    }
}
