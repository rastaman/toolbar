/*
 * StandardAction.java
 *
 * Created on 11 May 2003, 22:30
 */

package org.tigris.toolbar.actions.file;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import org.tigris.toolbar.LogPanel;

//import org.tigris.toolbutton.AbstractButtonAction;

/**
 * A simple button action that simply logs that it took place.
 *
 * @author Bob Tarling
 */
//class NewAction extends AbstractButtonAction {
public class PageSetupAction extends AbstractAction {

    public PageSetupAction(String name, Icon icon) {
        super(name, icon);
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        LogPanel.getInstance().add("PageSetup clicked");
    }
}
