/*
 * ToolBar.java
 *
 * Created on 29 September 2002, 21:01
 */

package org.tigris.toolbar;

import org.tigris.toolbutton.AbstractToolBar;

/**
 * A toolbar class which assumes rollover effects and automatically gives tooltip
 * to any buttons created by adding an action.
 *
 * @author  Bob Tarling
 */
public class ToolBar extends AbstractToolBar {
    /** Creates a new instance of Toolbar
     */
    public ToolBar() {
        super();
    }
    
    /** Creates a new instance of Toolbar
     */
    public ToolBar(String name) {
        super(name);
    }
    
    /** Creates a new instance of Toolbar
     */
    public ToolBar(String name, int orientation) {
        super(name, orientation);
    }
    
    /** Creates a new instance of Toolbar
     */
    public ToolBar(int orientation) {
        super(orientation);
    }
}
