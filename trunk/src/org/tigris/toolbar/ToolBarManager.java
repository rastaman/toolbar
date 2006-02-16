/*
 * ToolBarManager.java
 *
 * Created on 02 March 2003, 15:08
 */

package org.tigris.toolbar;

import javax.swing.UIManager;

/**
 * This class exists to propogate change in toolbar settings (icon size etc)
 * to all toolbars registered with it.
 *
 * @author Bob Tarling
 */
public class ToolBarManager {
    
    /** Creates a new instance of ToolBarManager */
    public ToolBarManager() {
    }
    
    /**
     * The toolbar projcet tries to add rollover borders to
     * buttons. However some specific lafs do not want this.
     * @return
     */
    public static final boolean alwaysUseStandardRollover() {
        return UIManager.getLookAndFeel().getID().equals("Metal");
    }
}
