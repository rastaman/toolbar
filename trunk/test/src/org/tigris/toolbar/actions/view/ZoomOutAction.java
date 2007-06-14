/*
 * StandardAction.java
 *
 * Created on 11 May 2003, 22:30
 */

package org.tigris.toolbar.actions.view;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import org.tigris.toolbar.LogPanel;

/**
 * A simple button action that simply logs that it took place.
 *
 * @author Bob Tarling
 */
public class ZoomOutAction extends AbstractAction {

    public ZoomOutAction(String name, Icon icon) {
        super(name, icon);
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        LogPanel.getInstance().add("ZoomOut clicked");
    }
}
