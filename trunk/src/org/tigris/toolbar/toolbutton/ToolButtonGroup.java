/*
 * StickyButtonGroup.java
 *
 * Created on 16 March 2003, 17:22
 */

package org.tigris.toolbar.toolbutton;

import java.util.ArrayList;
import java.util.Iterator;

import org.tigris.toolbar.ToolBarManager;

/**
 * 
 *
 * @author  Bob Tarling
 */
public class ToolButtonGroup {
    
    ArrayList buttons = new ArrayList();
    ToolButton defaultButton;
    
    /** Creates a new instance of StickyButtonGroup */
    public ToolButtonGroup() {
    }
    
    public ToolButton add(ToolButton toolButton) {
        buttons.add(toolButton);
        toolButton.setInGroup(this);
        return toolButton;
    }
    
    public void buttonSelected(ToolButton toolButton) {
        Iterator it = buttons.iterator();
        boolean alwaysShowBorder = ToolBarManager.alwaysUseStandardRollover();
        while (it.hasNext()) {
            ToolButton button = (ToolButton)it.next();
            if (button != toolButton) {
                button.setSelected(false);
                if (alwaysShowBorder) {
                    button.setBorderPainted(false);
                }
            } else {
                if (alwaysShowBorder) {
                    button.setBorderPainted(true);
                }
            }
        }
    }
    
    public ToolButton setDefaultButton(ToolButton toolButton) {
        defaultButton = toolButton;
        return toolButton;
    }
    
    public ToolButton getDefaultButton() {
        return defaultButton;
    }
}
