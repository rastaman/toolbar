/*
 * StandardAction.java
 *
 * Created on 11 May 2003, 22:30
 */

package org.tigris.toolbar;

import javax.swing.Icon;

import org.tigris.toolbutton.AbstractButtonAction;

/**
 *
 * @author Bob Tarling
 */
class StickyAction extends AbstractButtonAction {

    StickyAction(String name, Icon icon, boolean modal) {
        super(name, icon, modal);
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        LogPanel.getInstance().add(getName() + " selected");
        System.out.println(actionEvent);
    }
}
