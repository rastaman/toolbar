/*
 * Test.java
 *
 * Created on 01 March 2003, 22:05
 */

package org.tigris.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import org.tigris.toolbar.actions.file.NewAction;
import org.tigris.toolbar.actions.file.OpenAction;
import org.tigris.toolbar.actions.file.SaveAction;
import org.tigris.toolbutton.ResourceLocator;

public class Test extends JFrame implements ActionListener
{
    
    public static void main(String[] args)
    {
        JFrame f = new Test();
        f.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        
        f.setBounds(10, 10, 400, 400);
        f.setVisible(true);
    }

    public Test()
    {
        setTitle("ToolBar Test");
        
        ResourceLocator.getInstance().addResourcePath("/org/tigris/toolbar/Images/");

        buildMenu();

        getContentPane().setLayout(new DockLayout());
        
        // Add toolbars to the top
        Object[] actions = {
            new NewAction ("New",  ResourceLocator.getInstance().getIcon("New.gif")),
            new OpenAction("Open", ResourceLocator.getInstance().getIcon("Open.gif")),
            new SaveAction("Save", ResourceLocator.getInstance().getIcon("Save.gif"))
        };

        for (int i=0; i<3; ++i) {
            getContentPane().add(ToolBarFactory.createToolBar(true, "File", actions, true), DockLayout.NORTH);
        }
        
        // Add main panel to the centre
        getContentPane().add(MainPanel.getInstance(), DockLayout.CENTER);
        
        // Set initial look and feel
        //try {
        //    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        //}
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            // Well, what can we do...
        }
        SwingUtilities.updateComponentTreeUI(this);
        getContentPane().invalidate();
        validate();
    }

    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu lnfMenu = new JMenu("Look and Feel");
        
        JMenuItem sysItem = new JMenuItem("System");
        sysItem.setActionCommand("System");
        sysItem.addActionListener(this);
        
        JMenuItem javaItem = new JMenuItem("Java");
        javaItem.setActionCommand("Java");
        javaItem.addActionListener(this);

        lnfMenu.add(sysItem);
        lnfMenu.add(javaItem);
        menuBar.add(lnfMenu);
        setJMenuBar(menuBar);
    }
    
    public void actionPerformed(ActionEvent ae) {
        JMenuItem item = (JMenuItem)ae.getSource();
        if (item.getActionCommand().equals("System")) {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ex) { }
        } else {
            try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
            catch (Exception ex) { }
        }

        SwingUtilities.updateComponentTreeUI(this);
        getContentPane().invalidate();
        validate();
    }
}
