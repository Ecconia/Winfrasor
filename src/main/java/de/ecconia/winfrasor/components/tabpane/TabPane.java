package de.ecconia.winfrasor.components.tabpane;

import java.awt.BorderLayout;

import javax.swing.JComponent;

import de.ecconia.winfrasor.TabData;
import de.ecconia.winfrasor.components.NoWrap;
import de.ecconia.winfrasor.factories.FactoryContext;

/**
 * The TabPane has a header and a body. Its your custom TabView.
 *
 * The body of this pane will be replaced with the selected tab (or nothing).
 */
public class TabPane extends JComponent implements NoWrap
{
	private final TabPaneHeader header;
	private final TabPaneBody body;
	
	public TabPane(FactoryContext factoryContext)
	{
		body = new TabPaneBody(factoryContext);
		header = new TabPaneHeader(factoryContext, body);
		
		setLayout(new BorderLayout());
		add(header, BorderLayout.NORTH);
		add(body, BorderLayout.CENTER);
	}
	
	public void addTab(TabData data)
	{
		header.addTab(data);
	}
	
	public int getTabAmount()
	{
		return header.getComponentCount();
	}
	
	public void setNeverClose(boolean neverClose)
	{
		header.setCloseOnEmpty(!neverClose);
	}
}
