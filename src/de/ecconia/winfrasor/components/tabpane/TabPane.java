package de.ecconia.winfrasor.components.tabpane;

import java.awt.BorderLayout;

import javax.swing.JComponent;

import de.ecconia.winfrasor.TabData;
import de.ecconia.winfrasor.api.NoWrap;

@SuppressWarnings("serial")
public class TabPane extends JComponent implements NoWrap
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
}
