/*
 * FileToolBar.java
 *
 * Created on 11 May 2003, 22:26
 */

package org.tigris.toolbar;

import org.tigris.toolbutton.ResourceLocator;

/**
 *
 * @author Bob Tarling
 */
public class FileToolBar extends ToolBar {
    
    /** Creates a new instance of FileToolBar */
    public FileToolBar() {
        super(true);
        setName("File Toolbar");

        add(new StandardAction("New", ResourceLocator.getInstance().getIcon("New.gif")));
        add(new StandardAction("Open", ResourceLocator.getInstance().getIcon("Open.gif")));
        add(new StandardAction("Save", ResourceLocator.getInstance().getIcon("Save.gif")));
    }
}
