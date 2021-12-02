package de.ecconia.winfrasor.factories;

import java.awt.Point;

import de.ecconia.winfrasor.TabData;
import de.ecconia.winfrasor.components.tabpane.TabPane;

public class FactoryContext
{
	private TabPaneFactory tabPaneFactory;
	private DragFailedHandler dragFailedHandler;
	
	public FactoryContext()
	{
		tabPaneFactory = () -> new TabPane(FactoryContext.this);
	}
	
	public void setTabPaneFactory(TabPaneFactory tabPaneFactory)
	{
		this.tabPaneFactory = tabPaneFactory;
	}
	
	public void setDragFailedHandler(DragFailedHandler dragFailedHandler)
	{
		this.dragFailedHandler = dragFailedHandler;
	}
	
	public TabPane createTabPane()
	{
		return tabPaneFactory.createTabPane();
	}
	
	public boolean isHandlingDragFail()
	{
		return dragFailedHandler != null;
	}
	
	public void handleDragFail(TabData tab, Point location)
	{
		dragFailedHandler.dropFailed(tab, location);
	}
}
