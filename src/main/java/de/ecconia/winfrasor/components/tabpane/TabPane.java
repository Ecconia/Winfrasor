package de.ecconia.winfrasor.components.tabpane;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JComponent;

import de.ecconia.winfrasor.TabData;
import de.ecconia.winfrasor.api.NoWrap;
import de.ecconia.winfrasor.api.Tab;
import de.ecconia.winfrasor.api.TabHolder;

public class TabPane extends JComponent implements NoWrap, TabHolder
{
	private final TabPaneHeader header;
	private final TabPaneBody body;
	
	public TabPane()
	{
		body = new TabPaneBody();
		header = new TabPaneHeader(body);
		
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
	}
	
	public void addTab(TabData data)
	{
		header.addTab(data);
	}
	
	public int getTabCount()
	{
		return header.getComponentCount();
	}
	
	//API:
	
	@Override
	public Tab getSelected()
	{
		return null;
	}
	
	@Override
	public boolean setSelected(Tab tab)
	{
		return false;
	}
	
	@Override
	public boolean setSelected(int index)
	{
		return false;
	}
	
	@Override
	public boolean addTab(Tab tab)
	{
		addTab(new TabData(tab));
		return true;
	}
	
	@Override
	public boolean addTab(Tab tab, int index)
	{
		return false;
	}
	
	@Override
	public boolean removeTab(Tab tab)
	{
		return false;
	}
	
	@Override
	public boolean removeTab(int index)
	{
		return false;
	}
	
	@Override
	public int getTabAmount()
	{
		return header.getComponentCount();
	}
	
	@Override
	public Component asComponent()
	{
		return this;
	}

	@Override
	public void setNeverClose(boolean neverClose)
	{
		header.setCloseOnEmpty(!neverClose);
	}
}
