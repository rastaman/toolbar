/*
 * Test.java
 *
 * Created on 01 March 2003, 22:05
 */

package org.tigris.toolbar;

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
import org.tigris.toolbutton.ResourceLocator;

public class Test extends JFrame implements ActionListener {

    JToolBar fileToolBar1;
    JToolBar fileToolBar2;
    JToolBar fileToolBar3;

    String javaVersion;
    
    public static void main(String[] args) {
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
        javaVersion = System.getProperties().getProperty("java.specification.version");
        
        setTitle("ToolBar Test");
        
        initialLookAndFeel();
        
        ResourceLocator.getInstance().addResourcePath("/org/tigris/toolbar/Images/");

        buildMenu();

        getContentPane().setLayout(new DockLayout());
        
        createApplicationToolbars(getContentPane());

        // Add main panel to the centre
        getContentPane().add(MainPanel.getInstance(), DockLayout.CENTER);
        
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
        Object[] actions = {
            new NewAction ("New",  ResourceLocator.getInstance().getIcon("New.gif")),
            new OpenAction("Open", ResourceLocator.getInstance().getIcon("Open.gif")),
            new SaveAction("Save", ResourceLocator.getInstance().getIcon("Save.gif"))
        };

        if (fileToolBar1 != null) remove(fileToolBar1);
        if (fileToolBar1 != null) remove(fileToolBar2);
        if (fileToolBar1 != null) remove(fileToolBar3);
        
        fileToolBar1 = ToolBarFactory.createToolBar(true, "File", actions, true);
        fileToolBar2 = ToolBarFactory.createToolBar(true, "File", actions, true);
        fileToolBar3 = ToolBarFactory.createToolBar(true, "File", actions, true);
        
        pane.add(fileToolBar1, DockLayout.NORTH);
        pane.add(fileToolBar2, DockLayout.NORTH);
        pane.add(fileToolBar3, DockLayout.NORTH);
    }
}
