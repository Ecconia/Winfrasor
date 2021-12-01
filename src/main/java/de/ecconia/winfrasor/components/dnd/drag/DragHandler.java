package de.ecconia.winfrasor.components.dnd.drag;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DragSourceMotionListener;
import java.io.IOException;

import de.ecconia.winfrasor.components.dnd.TabFlavor;

/**
 * In charge of recognizing when a component is moved to start dragging it.
 */
public class DragHandler implements DragGestureListener, DragSourceListener, DragSourceMotionListener
{
	private final DragSource source;
	private final Object data;
	
	private final DragListener listener;
	
	private boolean isDragging; //For some reason the drop-end fires twice. This boolean shall prevent any ending call, if there was no drag start.
	
	public DragHandler(Component component, DragListener listener, Object data)
	{
		this.data = data;
		this.listener = listener;
		
		source = new DragSource();
		source.createDefaultDragGestureRecognizer(component, DnDConstants.ACTION_MOVE, this);
		source.addDragSourceListener(this);
		source.addDragSourceMotionListener(this);
	}
	
	@Override
	public void dragGestureRecognized(DragGestureEvent event)
	{
		isDragging = true;
		//TODO: Create window.
		listener.dragStart();
		
		source.startDrag(event, Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR), new Transferable()
		{
			@Override
			public boolean isDataFlavorSupported(DataFlavor flavor)
			{
				return flavor == TabFlavor.flavor;
			}
			
			@Override
			public DataFlavor[] getTransferDataFlavors()
			{
				return new DataFlavor[] {TabFlavor.flavor};
			}
			
			@Override
			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
			{
				if(flavor == TabFlavor.flavor)
				{
					return data;
				}
				
				throw new UnsupportedFlavorException(flavor);
			}
		}, this);
	}
	
	@Override
	public void dragEnter(DragSourceDragEvent event)
	{
	}
	
	@Override
	public void dragOver(DragSourceDragEvent event)
	{
	}
	
	@Override
	public void dropActionChanged(DragSourceDragEvent event)
	{
	}
	
	@Override
	public void dragExit(DragSourceEvent event)
	{
	}
	
	@Override
	public void dragDropEnd(DragSourceDropEvent event)
	{
		if(!isDragging)
		{
			return;
		}
		isDragging = false;
		
		//TODO: Remove window if exist.
		listener.dragEnd();
		
		if(event.getDropSuccess())
		{
			listener.dragSuccessful();
		}
	}
	
	@Override
	public void dragMouseMoved(DragSourceDragEvent dsde)
	{
		//TODO: Update window if exist.
	}
	
	//TODO: Re-add the ability to show dragged content. But this time in a safer way. 
//	if(image != null)
//	{
//		Window owner = SwingUtilities.getWindowAncestor(pane);
//		
//		window = new Window(owner)
//		{
//			@Override
//			public void paint(Graphics g)
//			{
//				g.drawImage(image, 0, 0, null);
//			}
//		};
//		window.setAlwaysOnTop(true);
//		window.setSize(image.getWidth(null), image.getHeight(null));
//		window.setOpacity(0.8f);
//		window.setFocusable(false);
//		setOpacity(window);
//	}
	
//	if(window != null)
//	{
//		Point p = new Point(dsde.getX(), dsde.getY());
//		Dimension size = window.getSize();
	
	//TODO: Get monitors, check on which is the x and y and get that size...
//		System.out.println(getHeight() + ", " + p.y + ", " + size.height);
//		if(getHeight() - p.y < size.height)
//		{
//			window.setVisible(false);
//		}
//		else
//		{
//			window.setLocation(p);
//			window.setVisible(true);
//		}
//	if(window != null)
//	{
//		window.dispose();
//		window = null;
//	}
//	
//	if(dsde.getDropSuccess())
//	{
//		removeTab(draggedIndex);
//		draggedIndex = null;
//		draggedTab = null;
//	}
}
