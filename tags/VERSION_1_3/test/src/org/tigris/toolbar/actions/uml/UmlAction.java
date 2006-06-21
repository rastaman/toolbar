/*
 * StandardAction.java
 *
 * Created on 11 May 2003, 22:30
 */

package org.tigris.toolbar.actions.uml;

import javax.swing.Icon;

import org.tigris.toolbar.CanvasPanel;
import org.tigris.toolbar.LogPanel;
import org.tigris.toolbar.toolbutton.AbstractButtonAction;
import org.tigris.toolbar.toolbutton.ResourceLocator;

/**
 * A simple button action that simply logs that it took place.
 *
 * @author Bob Tarling
 */
public abstract class UmlAction extends AbstractButtonAction {

    public UmlAction(String name) {
        super(name, ResourceLocator.getInstance().getIcon(name + ".gif"));
    }

    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        Icon lockedIcon = null;
        if (CanvasPanel.getInstance().getModeLocked()) {
            lockedIcon = CanvasPanel.getInstance().getSelectedIcon();
        }
        super.actionPerformed(actionEvent);
        boolean doubleClick = isDoubleClick();
        Icon icon = this.getIcon();
        if (doubleClick) {
            LogPanel.getInstance().add(getName() + " double-clicked");
        } else {
            LogPanel.getInstance().add(getName() + " clicked");
            if (lockedIcon == this.getIcon()) {
                icon = null;
                CanvasPanel.getInstance().deselectOtherTools(null);
            }
        }
        CanvasPanel.getInstance().setSelectedIcon(icon, doubleClick);
    }
}
