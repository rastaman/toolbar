package org.tigris.toolbar.actions.file;

import javax.swing.AbstractAction;
import org.tigris.toolbar.LogPanel;

/**
 * A simple button action that simply logs that it took place.
 *
 * @author Bob Tarling
 */
public class ExitAction extends AbstractAction {

    public ExitAction(String name) {
        super(name);
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        LogPanel.getInstance().add("Exit clicked");
    }
}
