/*
 * Test.java
 *
 * Created on 01 March 2003, 22:05
 */

package org.tigris.toolbar;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.JToolBar;
import org.tigris.toolbar.actions.file.NewAction;
import org.tigris.toolbar.actions.file.OpenAction;
import org.tigris.toolbar.actions.file.SaveAction;
import org.tigris.toolbar.actions.edit.CopyAction;
import org.tigris.toolbar.actions.edit.CutAction;
import org.tigris.toolbar.actions.edit.PasteAction;
import org.tigris.toolbar.actions.edit.UndoAction;
import org.tigris.toolbar.actions.edit.RedoAction;
import org.tigris.toolbar.layouts.DockLayout;
import org.tigris.toolbar.toolbutton.ResourceLocator;

public class Test extends JFrame implements ActionListener {

    JToolBar fileToolBar;
    JToolBar editToolBar;
    JToolBar viewToolBar;

    String javaVersion;
    
    public static void main(String[] args) {
        JFrame f = new Test();
        f.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        
        f.setBounds(10, 10, 600, 400);
        f.setVisible(true);
    }

    public Test() {
        javaVersion = System.getProperties().getProperty("java.specification.version");
        
        setTitle("ToolBar Test");
        
        initialLookAndFeel();
        
        ResourceLocator.getInstance().addResourcePath("/org/tigris/toolbar/Images/");

        buildMenu();

        getContentPane().setLayout(new DockLayout(this, DockLayout.STACKING_STYLE));
        
        createApplicationToolbars(getContentPane());

        // Add main panel to the centre
        getContentPane().add(MainPanel.getInstance(), BorderLayout.CENTER);
        
    }

    private void initialLookAndFeel() {
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
        
        String[] names = getAvailableLookAndFeelNames();
        for (int i = 0; i < names.length; ++i) {
            JMenuItem menuItem = new JMenuItem(names[i]);
            menuItem.setActionCommand(names[i]);
            menuItem.addActionListener(this);
            lnfMenu.add(menuItem);
        }
        
        menuBar.add(lnfMenu);
        setJMenuBar(menuBar);
    }
    
    /**
     * Returns the display names of the available look and feel choices.
     * 
     * @return	look and feel display names
     **/
    public String[] getAvailableLookAndFeelNames() {
        UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();

        String[] names = new String[lafs.length];
        for (int i = 0; i < lafs.length; ++i) {
            names[i] = lafs[i].getName();
        }

        return names;
    }

    public String getLookAndFeelClassName(String name) {
        UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();

        for (int i = 0; i < lafs.length; ++i) {
            if (name.equals(lafs[i].getName())) return lafs[i].getClassName(); 
        }

        return "";
    }

    public void actionPerformed(ActionEvent ae) {
        JMenuItem item = (JMenuItem)ae.getSource();
        String lookAndFeelName = item.getActionCommand();
        String lookAndFeelClassName = getLookAndFeelClassName(lookAndFeelName);
        if (!javaVersion.equals("1.3")) {
            setLookAndFeel(getLookAndFeelClassName(item.getActionCommand()));
        }
        createApplicationToolbars(getContentPane());
        CanvasPanel.getInstance().createToolBar();
        if (javaVersion.equals("1.3")) {
            setLookAndFeel(getLookAndFeelClassName(item.getActionCommand()));
        }
    }
    
    private void setLookAndFeel(String lookAndFeelClassName) {
        try {
            UIManager.setLookAndFeel(lookAndFeelClassName);
        } catch (Exception ex) { }
        SwingUtilities.updateComponentTreeUI(this);
        getContentPane().invalidate();
        validate();
    }
    
    private void createApplicationToolbars(Container pane) {
        Object[] fileActions = {
            new NewAction ("New",  ResourceLocator.getInstance().getIcon("New.gif")),
            new OpenAction("Open", ResourceLocator.getInstance().getIcon("Open.gif")),
            new SaveAction("Save", ResourceLocator.getInstance().getIcon("Save.gif")),
			new SaveAction("PageSetup", ResourceLocator.getInstance().getIcon("PageSetup.gif")),
			new SaveAction("Print", ResourceLocator.getInstance().getIcon("Print.gif"))
        };

		Object[] editActions = {
			new CutAction ("Cut",  ResourceLocator.getInstance().getIcon("Cut.gif")),
			new CopyAction("Copy", ResourceLocator.getInstance().getIcon("Copy.gif")),
			new PasteAction("Paste", ResourceLocator.getInstance().getIcon("Paste.gif")),
			new UndoAction("Undo", ResourceLocator.getInstance().getIcon("Undo.gif")),
			new RedoAction("Redo", ResourceLocator.getInstance().getIcon("Redo.gif"))
		};

		Object[] viewActions = {
			new CutAction ("ZoomIn",  ResourceLocator.getInstance().getIcon("ZoomReset.gif")),
			new CopyAction("ZoomOut", ResourceLocator.getInstance().getIcon("ZoomOut.gif")),
			new PasteAction("ZoomReset", ResourceLocator.getInstance().getIcon("ZoomIn.gif"))
		};

        if (fileToolBar != null) remove(fileToolBar);
        if (editToolBar != null) remove(editToolBar);
        if (viewToolBar != null) remove(viewToolBar);
        
        fileToolBar = ToolBarFactory.createToolBar(true, "File", fileActions, true);
        editToolBar = ToolBarFactory.createToolBar(true, "Edit", editActions, true);
        viewToolBar = ToolBarFactory.createToolBar(true, "View", viewActions, true);
        
        pane.add(DockLayout.north, fileToolBar);
        pane.add(DockLayout.north, editToolBar);
        pane.add(DockLayout.north, viewToolBar);
    }
}
