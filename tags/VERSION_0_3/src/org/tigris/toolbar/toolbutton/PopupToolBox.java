/*
 * PopupToolBox.java
 *
 * Created on 23 February 2003, 09:59
 */

package org.tigris.toolbar.toolbutton;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Action;
import javax.swing.JButton;

/**
 * A toolbox which appears when the user click the drop down image on
 * a PopupToolBoxButton
 *
 * @author  Bob Tarling
 */
public class PopupToolBox extends ToolBox {

    ArrayList _actions = new ArrayList();
    MouseListener _mouseListener;

    /** Creates a new instance of PopupToolBox */
    public PopupToolBox(int rows, int cols) {
        super(rows, cols);
    }

    /** Creates a new instance of PopupToolBox */
    public PopupToolBox(int rows, int cols, boolean rollover) {
        super(rows, cols, rollover);
    }

    public JButton add(Action action) {
        JButton button = super.add(action);
        _actions.add(action);
        
        return button;
    }

    public void setButtonMouseListener(MouseListener mouseListener) {
        _mouseListener = mouseListener;
    }

    /**
     * Occasionally the ToolBox gets in a state where a button
     * shows rollover status at the wrong time.
     * The only way to get around this is to rebuild the ToolBox.
     */
    public void rebuild() {
        super.removeAll();
        Iterator it = _actions.iterator();
        while(it.hasNext()) {
            Action a = (Action)it.next();
            JButton button = super.add(a);
            button.setBorderPainted(false);
            if (_mouseListener != null) {
                button.addMouseListener(_mouseListener);
            }
        }
    }
}
