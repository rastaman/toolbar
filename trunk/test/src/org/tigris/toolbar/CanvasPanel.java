/*
 * ActionCanvas.java
 *
 * Created on 10 May 2003, 18:15
 */

package org.tigris.toolbar;

import java.awt.BorderLayout;
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
import org.tigris.toolbutton.ResourceLocator;

/**
 * An example canvas to demonstrate the previously selected actions
 *
 * @author Bob Tarling
 */
public class CanvasPanel extends JPanel {
    
    private static CanvasPanel instance = new CanvasPanel();
    
    Object associationActions[][] = {
        {new AssociationAction(), new UniAssociationAction()},
        {new AggregationAction(), new UniAggregationAction()},
        {new CompositionAction(), new UniCompositionAction()}
    };
    
    private Object actions[] = {
        new SelectAction(),
        new BroomAction(),
        null,
        new PackageAction(),
        new ClassAction(),
        new InterfaceAction(),
        associationActions
    };

    private Icon selectedIcon;
    
    public static CanvasPanel getInstance() {
        return instance;
    }
    
    /** Creates a new instance of ActionCanvas */
    private CanvasPanel() {
        JToolBar toolBar = ToolBarFactory.createToolBar(true, actions, false);
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
    
    class CanvasAction extends AbstractAction {
        
        CanvasAction() {
            super();
        }
        
        public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            System.out.println("Canvas action");
            if (actionEvent.getSource() instanceof JButton) {
                System.out.println("Canvas button action");
                JButton button = (JButton)actionEvent.getSource();
                button.setIcon(selectedIcon);
            }
        }
    }
}
