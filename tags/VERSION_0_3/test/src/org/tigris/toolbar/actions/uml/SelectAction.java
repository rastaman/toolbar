/*
 * SelectAction.java
 *
 * Created on 11 May 2003, 22:30
 */

package org.tigris.toolbar.actions.uml;

import org.tigris.toolbar.CanvasPanel;
import org.tigris.toolbar.LogPanel;
import org.tigris.toolbar.toolbutton.AbstractButtonAction;
import org.tigris.toolbar.toolbutton.ResourceLocator;

/**
 * The action of pressing the "Select" button. This changes the mode of
 * canvas to null so that clicking on the canvas has no effect.
 *
 * @author Bob Tarling
 */
public class SelectAction extends AbstractButtonAction {

    public SelectAction() {
        super("Select", ResourceLocator.getInstance().getIcon("Select.gif"));
    }
    
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        super.actionPerformed(actionEvent);
        LogPanel.getInstance().add("Select clicked");
        CanvasPanel.getInstance().setSelectedIcon(null, false);
    }
}
