/*
 * ToolButton.java
 *
 * Created on 15 February 2003, 20:27
 */

package org.tigris.toolbar.toolbutton;

import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.UIManager;

import org.tigris.toolbar.ToolBarManager;

/** 
 * An extension of JButton which gives rollover effect for all plafs. Even
 * those not provided for in JDK1.3
 * @author Bob Tarling
 */
public class ToolButton extends JButton {
    
    protected ArrayList containedGroups = new ArrayList();
    protected JButton _button;
    protected boolean selected;
    private boolean rollover;
    
    private static String javaVersion =
        System.getProperties().getProperty("java.specification.version");

    public ToolButton(Action a) {
        super(a);
                
        addMouseListener(new MyMouseListener());
        if ("1.3".equals(System.getProperties().getProperty("java.specification.version"))) {
            setMargin(new Insets(0,0,0,0));
        }
    }
    
    void setInGroup(ToolButtonGroup group) {
        containedGroups.add(group);
    }
    
    public void setRolloverEnabled(boolean enabled) {
        super.setRolloverEnabled(enabled);
        rollover = enabled;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            setBackground(UIManager.getColor("controlShadow"));
            super.setRolloverEnabled(false);
            Iterator it = containedGroups.iterator();
            while (it.hasNext()) {
                ToolButtonGroup sbg = (ToolButtonGroup)it.next();
                sbg.buttonSelected(this);
            }
            //getModel().setPressed(true);
            //getModel().setArmed(true);
        } else {
            //getModel().setPressed(false);
            //getModel().setArmed(false);
            setBackground(UIManager.getColor("control"));
            super.setRolloverEnabled(rollover);
        }
    }
    
    public Action getRealAction() {
        return _button.getAction();
    }
    
    protected void performAction(java.awt.event.ActionEvent actionEvent) {
        // Toggle the selected state and then trigger the real action
        setSelected(!selected);
        Action a = _button.getAction();
        a.actionPerformed(actionEvent);
    }
    
    protected class ToolButtonAction extends AbstractButtonAction {
        
        public ToolButtonAction() {
            super();
        }
        
        public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            performAction(actionEvent);
        }
    }
    
    /**
     *  If the button is selected it needs to show the border even when rollover effect is on
     *  and the mouse is not over.
     */
    private class MyMouseListener implements MouseListener {

        /**
         * Empty method to satisy interface only, there is no special
         * action to take place when the mouse first enters the
         * PopupToolBoxButton area
         */
        public void mouseEntered(MouseEvent me) {
            if (getRealAction().isEnabled() && !ToolBarManager.alwaysUseStandardRollover()) {
                setBorderPainted(true);
            }
        }

        /**
         * Be double sure the dropdowns rollover divider is removed when we leave the
         * button.
         */
        public void mouseExited(MouseEvent me) {
            if (!selected && !ToolBarManager.alwaysUseStandardRollover()) {
                setBorderPainted(false);
            }
        }
        
        public void mouseClicked(MouseEvent me) {
            //ToolButton.this.getModel().setArmed(true);
        }
        
        /**
         * Empty method to satisy interface only, there is no special
         * action to take place when the mouse is pressed on the
         * PopupToolBoxButton area
         */
        public void mousePressed(MouseEvent me) {}
        
        /**
         * Empty method to satisy interface only, there is no special
         * action to take place when the mouse is released on the
         * PopupToolBoxButton area
         */
        public void mouseReleased(MouseEvent me) {}
    }
}
