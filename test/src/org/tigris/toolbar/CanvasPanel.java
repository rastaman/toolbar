/*
 * ActionCanvas.java
 *
 * Created on 10 May 2003, 18:15
 */

package org.tigris.toolbar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.AbstractAction;
import javax.swing.Action;
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
import org.tigris.toolbutton.AbstractButtonAction;
import org.tigris.toolbutton.ModalAction;
import org.tigris.toolbutton.ResourceLocator;
import org.tigris.toolbutton.ToolButton;

/**
 * An example canvas to demonstrate the previously selected actions
 *
 * @author Bob Tarling
 */
public class CanvasPanel extends JPanel {
    
    private static CanvasPanel instance = new CanvasPanel();
    
    RadioAction selectAction = new RadioAction(new SelectAction());
    
    Object associationActions[][] = {
        {new AssociationAction(), new UniAssociationAction()},
        {new AggregationAction(), new UniAggregationAction()},
        {new CompositionAction(), new UniCompositionAction()}
    };
    
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
        toolBar = ToolBarFactory.createToolBar(true, actions, false);
        JPanel canvas = new JPanel();
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        canvas.setLayout(new GridLayout(10,10));
        for (int i=0; i < 100; ++i) {
            canvas.add(new JButton(new CanvasAction()));
        }
    }
    
    public void setSelectedIcon(Icon selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    /**
     * Set all toolbar buttons to unselected other then the toolbar button
     * with the supplied action.
     */
    public void deselectOtherTools(RadioAction otherThanAction) {
        int toolCount=toolBar.getComponentCount();
        for (int i=0; i<toolCount; ++i) {
            Component c = toolBar.getComponent(i);
            if (c instanceof ToolButton) {
                ToolButton tb = (ToolButton)c;
                Action action = (Action)tb.getRealAction();
                System.out.println("i =" + action);
                System.out.println("s =" + otherThanAction);
                if (action instanceof RadioAction) {
                    action = ((RadioAction)action).getAction();
                }
                if (!action.equals(otherThanAction.getAction())) {
                    ((ToolButton)c).setSelected(false);
                }
            }
        }
    }
    
    class CanvasAction extends AbstractAction {
        
        CanvasAction() {
            super();
        }
        
        public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            JButton button = (JButton)actionEvent.getSource();
            button.setIcon(selectedIcon);
            deselectOtherTools(selectAction);
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
}
