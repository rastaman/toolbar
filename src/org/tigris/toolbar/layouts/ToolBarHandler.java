/*
 * 	ToolBarHandler.java
 * 	2003-12-19
 */


package org.tigris.toolbar.layouts;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicToolBarUI;


/**
 * Utilitiy class that replaces JToolBar's look and feel mouse listeners
 * to manage drag and drop behavior.  Also provides a drag outline window
 * and a floating frame for the associated toolbar.
 * @author Christopher Bach
 */
// Package access only...
class ToolBarHandler
{

	private DockingConstraints			ourConstraints = new DockingConstraints();

	private JToolBar					ourToolBar = null;
	private DockLayout					ourDockLayout = null;
	private JDialog						ourFloatFrame = null;
	private DraggingWindow				ourDraggingWindow = null;

	private ToolBarDragListener			ourDragListener = null;
	private UIChangeListener			ourUIListener = null;

	private boolean						ourToolBarShouldFloat = false;

	public static final String 			TOOL_BAR_HANDLER_KEY = "ToolBarHandler";





	/**
	 * Creates a ToolBarHandler for the specified toolbar
	 * to be docked within the specified container.
	 */
	public ToolBarHandler(JToolBar toolbar, DockLayout layout)
	{
		ourToolBar = toolbar;
		ourDockLayout = layout;

		ourDragListener = new ToolBarDragListener();
		ourUIListener = new UIChangeListener();


		// Remove UI mouse listeners and replace with our own listeners...
		installListeners();


		// Create a frame for floating this toolbar...
		Window w = SwingUtilities.getWindowAncestor(
									ourDockLayout.getTargetContainer());
		Frame fr = null;
		if (w instanceof Frame) fr = (Frame)w;
		ourFloatFrame = new JDialog(fr);
		ourFloatFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		ourFloatFrame.addWindowListener(new FloatFrameCloseListener());
		ourFloatFrame.getContentPane().setLayout(new BorderLayout());
		ourFloatFrame.setTitle(ourToolBar.getName());
		ourFloatFrame.setResizable(false);
	}
















	/**
	 * Sets the DockingConstraints for this handler representing
	 * the edge and indicies at which this handler's toolbar
	 * should be docked.
	 */
	public void setConstraints(DockingConstraints constraints)
	{
		ourConstraints = constraints;
	}


	/**
	 * Returns this handler's DockingConstraints representing
	 * the edge and indicies at which this handler's toolbar
	 * should be docked.
	 */
	public DockingConstraints getConstraints()
	{
		return ourConstraints;
	}








	/**
	 * Gets the edge in which the assocated toolbar should be docked.
	 */
	public int getDockEdge()
	{
		return ourConstraints.getEdge();
	}


	/**
	 * Sets the edge in which the associated toolbar should be docked.
	 */
	public void setDockEdge(int edge)
	{
		ourConstraints.setEdge(edge);

	}



	/**
	 * Gets the index at which the associated toolbar
	 * should be docked within its edge.
	 */
	public int getDockIndex()
	{
		return ourConstraints.getIndex();
	}


	/**
	 * Sets the index at which the associated toolbar
	 * should be docked within its edge.
	 */
	public void setDockIndex(int index)
	{
		ourConstraints.setIndex(index);
	}





	/**
	 * Gets the row index at which the associated toolbar
	 * should be docked within its edge.  Not all dock boundary
	 * styles will support control over the row index.
	 */
	public int getRowIndex()
	{
		return ourConstraints.getRow();
	}


	/**
	 * Sets the row index at which the associated toolbar
	 * should be docked within its edge.  Not all dock boundary
	 * styles will support control over the row index.
	 */
	public void setRowIndex(int index)
	{
		ourConstraints.setRow(index);
	}














	/**
	 * Hides the associated toolbar by removing it from its dock
	 * or by closing its client floating frame.
	 */
	public void hideToolBar()
	{
		Container target = ourDockLayout.getTargetContainer();
		target.remove(ourToolBar);
		ourFloatFrame.setVisible(false);
		ourFloatFrame.getContentPane().remove(ourToolBar);

		target.validate();
		target.repaint();
	}








	/**
	 * Docks the associated toolbar at its last visible location.
	 */
	public void dockToolBar()
	{
		dockToolBar(getDockEdge());
	}


	/**
	 * Docks the associated toolbar at the specified edge.
	 */
	public void dockToolBar(int edge)
	{
		dockToolBar(edge, getRowIndex(), getDockIndex());
	}


	/**
	 * Docks the associated toolbar at the secified edge and indicies.
	 */
	public void dockToolBar(int edge, int row, int index)
	{
		Container target = ourDockLayout.getTargetContainer();
		if (target == null) return;

		target.remove(ourToolBar);
		ourFloatFrame.setVisible(false);
		ourFloatFrame.getContentPane().remove(ourToolBar);

		ourConstraints.setEdge(edge);
		ourConstraints.setRow(row);
		ourConstraints.setIndex(index);

		String edgeName = DockLayout.north;

		if (edge == DockLayout.SOUTH)
				edgeName = DockLayout.south;

		else if (edge == DockLayout.EAST)
				edgeName = DockLayout.east;

		else if (edge == DockLayout.WEST)
				edgeName = DockLayout.west;

		target.add(ourToolBar, edgeName);
		ourToolBarShouldFloat = false;

		target.validate();
		target.repaint();
	}








	/**
	 * Floats the associated toolbar at its natural location.
	 */
	public void floatToolBar()
	{
		Point p = getFloatingLocation();
		floatToolBar(p.x, p.y);
	}


	/**
	 * Floats the associated toolbar at the specified screen location.
	 */
	public void floatToolBar(int x, int y)
	{
		floatToolBar(x, y, false);
	}


	/**
	 * Floats the associated toolbar at the specified screen location,
	 * optionally centering the floating frame on this point.
	 */
	public void floatToolBar(int x, int y, boolean center)
	{
		Container target = ourDockLayout.getTargetContainer();
		if (target != null) target.remove(ourToolBar);
		ourFloatFrame.setVisible(false);
		ourFloatFrame.getContentPane().remove(ourToolBar);

		ourToolBar.setOrientation(DockLayout.HORIZONTAL);
		ourFloatFrame.getContentPane().add(ourToolBar, BorderLayout.CENTER);
		ourFloatFrame.pack();

		if (center)
		{
			x -= ourFloatFrame.getWidth() / 2;
			y -= ourFloatFrame.getHeight() / 2;
		}

		// x and y are given relative to screen
		ourFloatFrame.setLocation(x, y);
		ourFloatFrame.setTitle(ourToolBar.getName());
		ourFloatFrame.setVisible(true);

		ourToolBarShouldFloat = true;

		if (target != null)
		{
			target.validate();
			target.repaint();
		}
	}

















	/**
	 * Gets the screen location of the associated
	 * toolbar's floating frame.
	 */
	public Point getFloatingLocation()
	{
		return ourFloatFrame.getLocation();
	}


	/**
	 * Sets the screen location of the associated
	 * toolbar's floating frame.
	 */
	public void setFloatingLocation(int x, int y)
	{
		ourFloatFrame.setLocation(x, y);
	}




	/**
	 * Determines whether or not the associated toolbar should be floated.
	 */
	public boolean shouldFloat()
	{
		return ourToolBarShouldFloat;
	}


	/**
	 * Stores whether or not the associated toolbar should be floated.
	 */
	public void setShouldFloat(boolean shouldFloat)
	{
		ourToolBarShouldFloat = shouldFloat;
	}










	/**
	 * Gets the nearest dockable DockBoundary for the specified point,
	 * or returns null if the point is not a dockable location.
	 */
	protected DockBoundary getDockableBoundary(Point point)
	{
		return ourDockLayout.getDockableBoundary(point);
	}



	/**
	 * Checks to see if the DockBoundary containing the provided
	 * toolbar wishes to veto the drag operation at the provided
	 * point.  Some DockBoundaries may manipulate the positions
	 * of the toolbars without this handler having to undock and
	 * redock the toolbar.
	 */
	protected boolean isDraggable(Point point, JToolBar toolbar)
	{
		if (toolbar == null) return false;

		DockBoundary boundary = ourDockLayout.getBoundary(point);
		if (boundary == null) return true;

		else if (boundary.containsToolBar(toolbar))
			return boundary.isDraggablePoint(point, toolbar);

		else return true;
	}











	/**
	 * Releases this handler's listeners from the associated toolbar.
	 */
	public void uninstallListeners()
	{
		// May want to restore the original listeners that were
		// stripped from the toolbar when this handler was created.
		ourToolBar.removeMouseListener(ourDragListener);
		ourToolBar.removeMouseMotionListener(ourDragListener);
		ourToolBar.removePropertyChangeListener(ourUIListener);
	}



	/**
	 * Strips off the UI's mouse listeners attached to the associated toolbar
	 * and replaces them with this handler's listeners.
	 */
	private void installListeners()
	{
		ourToolBar.removePropertyChangeListener("UI", ourUIListener);

		// Uninstall the current ui, collect the remaining listeners
		// on the toolbar, and reinstall the ui...
		ComponentUI ui = ourToolBar.getUI();
		ui.uninstallUI(ourToolBar);
		java.util.List mList = Arrays.asList(ourToolBar.getMouseListeners());
		java.util.List mmList = Arrays.asList(ourToolBar.getMouseMotionListeners());
		ui.installUI(ourToolBar);

		// ...then remove the listeners that were added by the ui...
		MouseListener[] ml = ourToolBar.getMouseListeners();
		MouseMotionListener[] mml = ourToolBar.getMouseMotionListeners();

		for (int i=0; i < ml.length; i++)
		{
			if (!mList.contains(ml[i]))
					ourToolBar.removeMouseListener(ml[i]);
		}

		for (int i=0; i < mml.length; i++)
		{
			if (!mmList.contains(mml[i]))
					ourToolBar.removeMouseMotionListener(mml[i]);
		}

		// ...and add our listeners to the toolbar.
		ourToolBar.addMouseListener(ourDragListener);
		ourToolBar.addMouseMotionListener(ourDragListener);
		ourToolBar.addPropertyChangeListener("UI", ourUIListener);
	}

























	/**
	 * Inner class that monitors the float frame for closing.
	 */
	private class FloatFrameCloseListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent we)
		{
			dockToolBar(getDockEdge(), getRowIndex(), getDockIndex());
		}
	}






	/**
	 * Inner class that listens for changes in the UI on the
	 * associated toolbar.  Insures that when a new UI is installed,
	 * its listeners are replaced.
	 */
	private class UIChangeListener implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent pce)
		{
			installListeners();
		}
	}










	/**
	 * Inner class that replaces the associated toolbar's mouse
	 * listeners and handles the drag and drop behavior.
	 */
	private class ToolBarDragListener extends MouseInputAdapter
	{

		public void mouseDragged(MouseEvent me)
		{
			// Only allow Button 1 to perform the drag...
			if ((me.getModifiers() & me.BUTTON1_MASK) != me.BUTTON1_MASK)
			{
				hideDraggingWindow();
				return;
			}


			Container target = ourDockLayout.getTargetContainer();
			if (target == null) return;

			Point p = me.getPoint();
			p = SwingUtilities.convertPoint(ourToolBar, p, target);


			// Make sure the DockBoundary containing this point
			// and this toolbar wishes to allow the drag operation
			// to commence or continue...
			if (!isDraggable(p, ourToolBar))
			{
				hideDraggingWindow();
				return;
			}


			// Ensure that the dragging window exists...
			if (ourDraggingWindow == null)
			{
				Window ancestorWindow =
					SwingUtilities.getWindowAncestor(ourToolBar);
				ourDraggingWindow = new DraggingWindow(ancestorWindow);
			}


			// Determine if this point lies within a
			// DockBoundary's dockable range...
			int orient = DockLayout.HORIZONTAL;
			boolean dockable = false;

			if (!me.isControlDown())
			{
				DockBoundary dock = getDockableBoundary(p);

				if (dock != null)
				{
					dockable = true;
					orient = dock.getOrientation();
				}
			}


			// Present the dragging window at this point on the screen...
			SwingUtilities.convertPointToScreen(p, target);
			ourDraggingWindow.presentWindow(p, dockable, orient);

		}








		public void mouseReleased(MouseEvent me)
		{
			if (ourDraggingWindow == null) return;

			if ((me.getModifiers() & me.BUTTON1_MASK) != me.BUTTON1_MASK)
			{
				return;
			}

			hideDraggingWindow();

			Container target = ourDockLayout.getTargetContainer();
			if (target == null) return;

			Point p = me.getPoint();
			p = SwingUtilities.convertPoint(ourToolBar, p, target);


			DockBoundary dock = null;

			if (!me.isControlDown())
			{
				dock = getDockableBoundary(p);
				if (dock != null)
				{
					setDockIndex(dock.getDockIndex(p));
					setRowIndex(dock.getRowIndex(p));
					setDockEdge(dock.getEdge());
				}
			}


			if (dock != null)
			{
				dockToolBar(getDockEdge(), getRowIndex(), getDockIndex());
			}


			else
			{
				SwingUtilities.convertPointToScreen(p, target);
				floatToolBar(p.x, p.y, true);
			}


		}




		private void hideDraggingWindow()
		{
			if (ourDraggingWindow != null)
			{
				ourDraggingWindow.hideWindow();
				ourDraggingWindow = null;
			}
		}

	}









	/**
	 * Inner class that represents the bounds of the associated
	 * toolbar while dragging.
	 */
	private class DraggingWindow extends JWindow
	{
		private Border		myFloatBorder = null;
		private Border		myDockBorder = null;
		private Color		myFloatColor = null;
		private Color		myDockColor = null;
		private JPanel		myContent = null;



		public DraggingWindow(Window ancestor)
		{
			super(ancestor);

			Color myFloatColor = UIManager.getColor("ToolBar.floatingForeground");
			Color myDockColor = UIManager.getColor("ToolBar.dockingForeground");
			if (myFloatColor == null) myFloatColor = Color.darkGray;
			if (myDockColor == null) myDockColor = Color.yellow;

			myFloatBorder = BorderFactory.createLineBorder(myFloatColor, 3);
			myDockBorder = BorderFactory.createLineBorder(myDockColor, 3);

			myContent = new JPanel();
			myContent.setOpaque(true);

			myFloatColor = UIManager.getColor("ToolBar.floatingBackground");
			myDockColor = UIManager.getColor("ToolBar.dockingBackground");
			if (myFloatColor == null) myFloatColor = myContent.getBackground();
			if (myDockColor == null) myDockColor = myContent.getBackground();

			getContentPane().setLayout(new BorderLayout());
			getContentPane().add(myContent, BorderLayout.CENTER);
		}





		// Present the window at the given point for the given dockability
		// at the given dock orientation...
		public void presentWindow(Point screenPoint,
											boolean dockable,
													int dockOrientation)
		{
			setCentroidLocation(screenPoint);


			int tbOrientation = ourToolBar.getOrientation();
			int orientation = (dockable ? dockOrientation
											: DockLayout.HORIZONTAL);

			setSize(getPreferredToolBarSize(orientation));

			myContent.setBorder( dockable ? myDockBorder : myFloatBorder );
			myContent.setBackground( dockable ? myDockColor : myFloatColor );

			setCentroidLocation(screenPoint);

			validate();
			if (!isVisible()) setVisible(true);
		}




		public void hideWindow()
		{
			setVisible(false);
		}




		private void setCentroidLocation(Point screenPoint)
		{
			setLocation(screenPoint.x - getWidth() / 2,
					screenPoint.y - getHeight() / 2);
		}



		private Point getCentroidLocation()
		{
			Point p = getLocation();
			p.x += (getWidth() / 2);
			p.y += (getHeight() / 2);
			return p;
		}



		private Dimension getPreferredToolBarSize(int orientation)
		{
			Component[] comps = ourToolBar.getComponents();
			int w = 0, h = 0;
			for (int i=0; i < comps.length; i++)
			{
				Dimension d = comps[i].getPreferredSize();
				if (orientation == DockLayout.HORIZONTAL)
				{
					w += d.width;
					h = Math.max(h, d.height);
				}

				else
				{
					w = Math.max(w, d.width);
					h += d.height;
				}
			}

			return new Dimension(w, h);
		}


	}



}