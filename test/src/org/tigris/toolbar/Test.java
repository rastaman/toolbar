/*
 * Test.java
 *
 * Created on 01 March 2003, 22:05
 */

package org.tigris.toolbar;
//
//import java.awt.*;
//import java.awt.image.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.util.*;
//
//import javax.swing.Action;
//import javax.swing.AbstractAction;
//import javax.swing.Icon;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
//import javax.swing.JPanel;
//import javax.swing.JRadioButton;
//import javax.swing.JScrollPane;
//import javax.swing.JToggleButton;
//import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import org.tigris.toolbutton.ResourceLocator;

//
//import javax.swing.event.*;

public class Test extends JFrame implements ActionListener
{
    //private ToolBar toolbar = null;
    
    public static void main(String[] args)
    {
        JFrame f = new Test();
        f.addWindowListener( new WindowAdapter()
                { public void windowClosing(WindowEvent we)
                        { System.exit(0); } } );
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
        getContentPane().add(new FileToolBar(), DockLayout.NORTH );
        getContentPane().add(new UmlToolBar(), DockLayout.NORTH );
        
        // Add main panel to the centre
        getContentPane().add(MainPanel.getInstance(), DockLayout.CENTER);
        
        // Set initial look and feel
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
        //try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ex) { }
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
