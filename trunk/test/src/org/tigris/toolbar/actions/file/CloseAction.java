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
public class CloseAction extends AbstractAction {

    public CloseAction(String name) {
        super(name);
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        LogPanel.getInstance().add("Close clicked");
    }
}
