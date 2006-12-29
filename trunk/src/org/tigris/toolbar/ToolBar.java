/*
 * ToolBar.java
 *
 * Created on 29 September 2002, 21:01
 */

package org.tigris.toolbar;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.tigris.toolbar.toolbutton.ModalButton;
import org.tigris.toolbar.toolbutton.OverflowButton;

/**
 * A toolbar class which assumes rollover effects and automatically gives tooltip
 * to any buttons created by adding an action.
 * Use ToolBarFactory to create.
 *
 * @author  Bob Tarling
 */
public class ToolBar extends JToolBar {
    
    private static final long serialVersionUID = -5763580513766833944L;
    
    private String javaVersion;
    
    private JButton overflowButton;
    
    private List hiddenComponents;
    
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
    
    /**
     * Creates a new instance of ToolBar with the given name and orientation
     * All other constructors call this constructor.
     * @param name the title to display while floating
     * @param orientation HORIZONTAL or VERTICAL
     */
    public ToolBar(String name, int orientation) {
        super(name, orientation);
        javaVersion = System.getProperties().getProperty("java.specification.version");
        this.setMargin(new Insets(0,0,0,0));
        overflowButton = new OverflowButton(ToolBar.this, isRollover());
        addComponentListener(new SizeListener());
    }
    
    /**
     * Creates a new instance of an un-named ToolBar with the given
     * orientation
     * @param orientation HORIZONTAL or VERTICAL
     * TODO: Provide a factory method for this, then ToolBar can become
     * package private.
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
        JButton button = new ModalButton(action);
        add(button);
        if (!ToolBarManager.alwaysUseStandardRollover()) {
            button.setBorderPainted(false);
        }
        if (javaVersion.equals("1.3")) {
            // This is needed specifically for JDK1.3 on Windows & Motif
            button.setMargin(new Insets(0,0,0,0));
        }
        return button;
    }

    protected void addImpl(Component comp, Object constraints, int index) {
        if (comp instanceof OverflowButton) {
            overflowButton = (OverflowButton) comp;
        }
        super.addImpl(comp, constraints, index);
    }

    private class SizeListener extends ComponentAdapter {

        public void componentResized(ComponentEvent e) {
            super.componentResized(e);
            
            if (hiddenComponents != null) {
                remove(overflowButton);
        	for(int i=0; i < hiddenComponents.size(); ++i) {
        	    add((Component) hiddenComponents.get(i));
        	}
        	hiddenComponents = null;
            }
            int overflowX = getWidth() - overflowButton.getWidth();
            overflowButton.setLocation(
            	overflowX, overflowButton.getHeight());
            boolean hide = false;
            for (int i = 0; i < getComponentCount() && !hide; ++i) {
                Component c = getComponent(i);
                if (c != overflowButton && c.getX() + c.getWidth() > getWidth()) {
                    hide = true;
                }
            }
            if (hide) {
                hiddenComponents = new ArrayList();
                int i = 0;
                while (i < getComponentCount()) {
                    Component c = getComponent(i);
                    if (c != overflowButton && c.getX() + c.getWidth() > overflowX) {
                        hiddenComponents.add(c);
                        remove(c);
                    } else {
                        ++i;
                    }
                }
                remove(overflowButton);
                add(overflowButton);
                validate();
            }
	}
    }
    
    public Component[] getOverflowActions() {
	Component[] componentArray = new Component[hiddenComponents.size()];
	hiddenComponents.toArray(componentArray);
	return componentArray;
    }
}
