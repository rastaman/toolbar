/*
 * PopupToolBoxButton.java
 *
 * Created on 15 February 2003, 20:27
 */

package org.tigris.toolbar.toolbutton;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import org.tigris.toolbar.ToolBar;
import org.tigris.toolbar.ToolBarManager;

/** 
 * An extension of JButton to which alternative actions can be added.
 * The button to trigger these actions become available when a
 * dropdown icon is pressed on this button.
 *
 * @author Bob Tarling
 */
public class OverflowButton extends ToolButton {

    private static final long serialVersionUID = -684520584458885655L;
    
    private ToolBar toolBar;
    
    private static final int X = 2;
    private static final int Y = 6;
    
    /**
     * Creates a new instance of PopupToolboxButton
     * @param a The default action when pressing this button
     * @param rows The number of rows of buttons to display in the popup toolbox
     * @param cols The number of columns of buttons to display in the popup toolbox
     */
    public OverflowButton(ToolBar toolBar, boolean rollover) {
	super(null);
	this.toolBar = toolBar;
	super.setAction(new OverflowAction());
    }
    
    private void popup() {
	
	Component[] components = toolBar.getOverflowActions();
	
	PopupToolBox popupToolBox = new PopupToolBox(components.length, 1, isRolloverEnabled());
	
	for (int i = 0; i < components.length; ++i) {
            if (components[i] instanceof JButton) {
                Action action = ((JButton) components[i]).getAction();
                popupToolBox.add(action);
            }
	}
	
        final JPopupMenu popup = new JPopupMenu();
        
        MouseAdapter m = (new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Component c = e.getComponent();
                if (c instanceof ModalButton) {
                    Action a = ((ModalButton)c).getRealAction();
                    setAction(a);
                    a.putValue("popped", Boolean.valueOf(true));
                    setSelected(true);
                    ButtonModel bm = getModel();
                    bm.setRollover(true);
                    if (!ToolBarManager.alwaysUseStandardRollover()) {
                        setBorderPainted(true);
                    }
                    bm.setArmed(true);
                }
                popup.setVisible(false);
            }
        });
        popupToolBox.setButtonMouseListener(m);
        
        popupToolBox.rebuild();
        popup.add(popupToolBox);
        popup.show(this, 0, getHeight());
    }
    
    public void paint(Graphics g) {
 	super.paint(g);
        g.setColor(UIManager.getColor("controlText"));
        
        for (int i=0; i < 5; ++i) {
            dualDrawLine(g, X + (2 - Math.abs(i-2)), i + Y);
        }
    }
    
    private void dualDrawLine(Graphics g, int x, int y) {
        g.drawLine(x, y, x+1, y);
        g.drawLine(x+4, y, x+5, y);
    }
    
    private class OverflowAction extends AbstractAction {

	private static final long serialVersionUID = 3031308217208709369L;

	OverflowAction() {
	    super(" ");
	}
	public void actionPerformed(ActionEvent e) {
            popup();
	}
    }
}
