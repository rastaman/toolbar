/*
 * UmlToolBar.java
 *
 * Created on 11 May 2003, 22:26
 */

package org.tigris.toolbar;

import org.tigris.toolbutton.PopupToolBoxButton;
import org.tigris.toolbutton.ResourceLocator;
import org.tigris.toolbutton.ToolButton;
import org.tigris.toolbutton.ToolButtonGroup;

/**
 *
 * @author Bob Tarling
 */
public class UmlToolBar extends ToolBar {
    
    /** Creates a new instance of UmlToolBar */
    public UmlToolBar() {
        setName("UML Toolbar");

        ToolButtonGroup group = new ToolButtonGroup();
        group.setDefaultButton(group.add((ToolButton)this.add(new StickyAction("Select", ResourceLocator.getInstance().getIcon("Select.gif"), true))));
        group.add((ToolButton)this.add(new StickyAction("Broom", ResourceLocator.getInstance().getIcon("Broom.gif"), true)));
        this.addSeparator();
        group.add((ToolButton)this.add(new StickyAction("Package", ResourceLocator.getInstance().getIcon("Package.gif"), true)));
        group.add((ToolButton)this.add(new StickyAction("Class", ResourceLocator.getInstance().getIcon("Class.gif"), true)));
        group.add((ToolButton)this.add(new StickyAction("Interface", ResourceLocator.getInstance().getIcon("Interface.gif"), true)));
        group.add((ToolButton)this.add(makePopup()));
    }
    
    private PopupToolBoxButton makePopup() {
        StickyAction initialAction = new StickyAction("aggregation", ResourceLocator.getInstance().getIcon("Aggregation.gif"), true);
        PopupToolBoxButton ptbb = new PopupToolBoxButton(initialAction, 2, 2);
        ptbb.add(initialAction);
        ptbb.add(new StickyAction("association", ResourceLocator.getInstance().getIcon("Association.gif"), true));
        ptbb.add(new StickyAction("uniaggregation", ResourceLocator.getInstance().getIcon("UniAggregation.gif"), true));
        ptbb.add(new StickyAction("uniassociation", ResourceLocator.getInstance().getIcon("UniAssociation.gif"), true));
        return ptbb;
    }
}
