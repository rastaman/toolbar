/*
 * AbstractButtonAction.java
 *
 * Created on 02 March 2003, 00:26
 */

package org.tigris.toolbar.toolbutton;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * The abstract class for all button actions that wish to detect double click events
 *
 * @author Bob Tarling
 */
abstract public class AbstractButtonAction extends AbstractAction implements ModalAction {
    
    private String name;
    private Icon _icon;
    
    private static AbstractButtonAction lastClickedAction = null;
    private long lastTimeClicked = 0;

    private boolean doubleClick;
    
    protected boolean isDoubleClick() {
        return doubleClick;
    }
    
    public AbstractButtonAction() {
        super();
    }
    /**
     * Creates a new instance of AbstractButtonAction
     */
    public AbstractButtonAction(String name, Icon icon) {
        super(name, icon);
        this._icon = icon;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public Icon getIcon() {
        return _icon;
    }
    
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        long timeClicked = System.currentTimeMillis();
        if (lastClickedAction == this && (timeClicked - lastTimeClicked) < 1000) {
            doubleClick = true;
            lastTimeClicked = timeClicked;
            lastClickedAction = this;
        } else {
            if (doubleClick && lastClickedAction == this) {
                lastClickedAction = null;
                doubleClick = false;
            } else {
                lastTimeClicked = timeClicked;
                lastClickedAction = this;
            }
        }
    }
}
