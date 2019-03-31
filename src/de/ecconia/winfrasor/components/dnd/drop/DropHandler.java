package de.ecconia.winfrasor.components.dnd.drop;

import java.awt.Component;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import de.ecconia.winfrasor.components.dnd.TabFlavor;

public class DropHandler implements DropTargetListener
{
	private final DropListener listener;
	
	//TBI: When do reject, when to accept?
	public DropHandler(Component component, DropListener listener)
	{
		this.listener = listener;
		new DropTarget(component, this);
	}
	
	@Override
	public void dragEnter(DropTargetDragEvent event)
	{
		if(event.isDataFlavorSupported(TabFlavor.flavor))
		{
			listener.newDropPoint(event.getLocation());
		}
		else
		{
			event.rejectDrag();
		}
	}
	
	@Override
	public void dragOver(DropTargetDragEvent event)
	{
		if(event.isDataFlavorSupported(TabFlavor.flavor))
		{
			listener.newDropPoint(event.getLocation());
		}
		else
		{
			event.rejectDrag();
		}
	}
	
	@Override
	public void dropActionChanged(DropTargetDragEvent event)
	{
		//TBI: Are actions relevant? Well idc for now.
	}
	
	@Override
	public void dragExit(DropTargetEvent event)
	{
		listener.newDropPoint(null);
	}
	
	@Override
	public void drop(DropTargetDropEvent event)
	{
		if(event.isDataFlavorSupported(TabFlavor.flavor))
		{
			//For safety also reset the position here.
			listener.newDropPoint(null);
			
			try
			{
				event.dropComplete(listener.drop(event.getLocation(), event.getTransferable().getTransferData(TabFlavor.flavor)));
			}
			catch(UnsupportedFlavorException | IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			event.rejectDrop();
		}
	}
}
