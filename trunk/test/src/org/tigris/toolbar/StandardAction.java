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
class StandardAction extends AbstractButtonAction {

    StandardAction(String name, Icon icon) {
        super(name, icon);
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        LogPanel.getInstance().add(getName() + " clicked");
    }
}
