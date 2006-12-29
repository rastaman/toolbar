/*
 * ActionCanvas.java
 *
 * Created on 10 May 2003, 18:15
 */

package org.tigris.toolbar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.tigris.toolbar.actions.uml.AggregationAction;
import org.tigris.toolbar.actions.uml.AssociationAction;
import org.tigris.toolbar.actions.uml.BroomAction;
import org.tigris.toolbar.actions.uml.ClassAction;
import org.tigris.toolbar.actions.uml.CompositionAction;
import org.tigris.toolbar.actions.uml.InterfaceAction;
import org.tigris.toolbar.actions.uml.PackageAction;
import org.tigris.toolbar.actions.uml.SelectAction;
import org.tigris.toolbar.actions.uml.UniAggregationAction;
import org.tigris.toolbar.actions.uml.UniAssociationAction;
import org.tigris.toolbar.actions.uml.UniCompositionAction;
import org.tigris.toolbar.toolbutton.AbstractButtonAction;
import org.tigris.toolbar.toolbutton.ResourceLocator;
import org.tigris.toolbar.toolbutton.ToolButton;

/**
 * An example canvas to demonstrate the previously selected actions
 *
 * @author Bob Tarling
 */
public class CanvasPanel extends JPanel {
    
    private static CanvasPanel instance = new CanvasPanel();
    
    RadioAction selectAction = new RadioAction(new SelectAction());
    
    Object associationActions[][] = {
        {new RadioAction(new AssociationAction()), new RadioAction(new UniAssociationAction())},
        {new RadioAction(new AggregationAction()), new RadioAction(new UniAggregationAction())},
        {new RadioAction(new CompositionAction()), new RadioAction(new UniCompositionAction())}
    };

    /** true if the canvas should not lose mode after being actioned */
    private boolean modeLocked;
    
    private Object actions[] = {
        selectAction,
        new RadioAction(new BroomAction()),
        null,
        new RadioAction(new PackageAction()),
        new RadioAction(new ClassAction()),
        new RadioAction(new InterfaceAction()),
        associationActions
    };

    private JToolBar toolBar = null;
    
    private Icon selectedIcon;
    
    public static CanvasPanel getInstance() {
        return instance;
    }
    
    /** Creates a new instance of ActionCanvas */
    private CanvasPanel() {
        JPanel canvas = new JPanel();
        setLayout(new BorderLayout());
        createToolBar();
        add(canvas, BorderLayout.CENTER);
        canvas.setLayout(new GridLayout(10,10));
        for (int i=0; i < 100; ++i) {
            canvas.add(new JButton(new CanvasAction()));
        }
        JPanel standardToolbarPanel = new JPanel();
        standardToolbarPanel.setLayout(new BorderLayout());
        JToolBar standardToolbar = new JToolBar();
        standardToolbarPanel.add(standardToolbar, BorderLayout.WEST);
        add(standardToolbarPanel, BorderLayout.SOUTH);
        standardToolbar.add(new EmptyAction("Copy", ResourceLocator.getInstance().getIcon("Copy.gif")));
        standardToolbar.add(new EmptyAction("Copy", ResourceLocator.getInstance().getIcon("Copy.gif")));
        standardToolbar.add(new EmptyAction("Copy", ResourceLocator.getInstance().getIcon("Copy.gif")));
        standardToolbar.add(new EmptyAction("Copy", ResourceLocator.getInstance().getIcon("Copy.gif")));
        standardToolbar.add(new EmptyAction("Copy", ResourceLocator.getInstance().getIcon("Copy.gif")));
        standardToolbar.add(new EmptyAction("Copy", ResourceLocator.getInstance().getIcon("Copy.gif")));
    }

    public void createToolBar() {
        if (toolBar != null) remove(toolBar);
        toolBar = ToolBarFactory.createToolBar(true, actions, false);
        add(toolBar, BorderLayout.NORTH);
    }
    
    public void setSelectedIcon(Icon selectedIcon, boolean modeLocked) {
        this.selectedIcon = selectedIcon;
        this.modeLocked = modeLocked;
    }
    
    public boolean getModeLocked() {
        return modeLocked;
    }
    
    public Icon getSelectedIcon() {
        return selectedIcon;
    }

    /**
     * Set all toolbar buttons to unselected other then the toolbar button
     * with the supplied action.
     */
    public void deselectOtherTools(RadioAction otherThanAction) {
        //System.out.println("Looking for action " + otherThanAction);
        int toolCount=toolBar.getComponentCount();
        for (int i=0; i<toolCount; ++i) {
            Component c = toolBar.getComponent(i);
            if (c instanceof ToolButton) {
                ToolButton tb = (ToolButton)c;
                Action action = (Action)tb.getRealAction();
                if (action instanceof RadioAction) {
                    action = ((RadioAction)action).getAction();
                }
                Action otherAction = otherThanAction;
                if (otherThanAction instanceof RadioAction) {
                    otherAction = otherThanAction.getAction();
                }
                if (!action.equals(otherAction)) {
                    //System.out.println("Unselecting " + tb);
                    tb.setSelected(false);
                    ButtonModel bm = tb.getModel();
                    bm.setRollover(false);
                    bm.setSelected(false);
                    bm.setArmed(false);
                    bm.setPressed(false);
                    if (!ToolBarManager.alwaysUseStandardRollover()) {
                        tb.setBorderPainted(false);
                    }
                } else {
                    //System.out.println("Selecting " + tb);
                    tb.setSelected(true);
                    ButtonModel bm = tb.getModel();
                    bm.setRollover(true);
                    if (!ToolBarManager.alwaysUseStandardRollover()) {
                        tb.setBorderPainted(true);
                    }
                }
            }
        }
    }

    /*
     * This action is performed whenever the canvas is clicked and sets
     * the clicked area to the current selected icon.
     */
    class CanvasAction extends AbstractAction {
        
        CanvasAction() {
            super();
        }
        
        public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            if (selectedIcon != null) {
                JButton button = (JButton)actionEvent.getSource();
                button.setIcon(selectedIcon);
                if (!modeLocked) {
                    deselectOtherTools(selectAction);
                    selectedIcon = null;
                }
            }
        }
    }
    
    class RadioAction extends AbstractButtonAction {
        
        AbstractButtonAction realAction;
        
        RadioAction(AbstractButtonAction action) {
            super(action.getName(), action.getIcon());
            realAction = action;
            action.getIcon();
        }
        
        public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            realAction.actionPerformed(actionEvent);
            deselectOtherTools(this);
        }
        
        public Action getAction() {
            return realAction;
        }
    }
    
    private class EmptyAction extends AbstractAction {
        EmptyAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
        }
    }
}
