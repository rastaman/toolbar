/*
 * StandardAction.java
 *
 * Created on 11 May 2003, 22:30
 */

package org.tigris.toolbar.actions.file;

import javax.swing.AbstractAction;
import org.tigris.toolbar.LogPanel;

//import org.tigris.toolbutton.AbstractButtonAction;

/**
 * A simple button action that simply logs that it took place.
 *
 * @author Bob Tarling
 */
//class NewAction extends AbstractButtonAction {
public class ExitAction extends AbstractAction {

    public ExitAction(String name) {
        super(name);
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        LogPanel.getInstance().add("Exit clicked");
    }
}
