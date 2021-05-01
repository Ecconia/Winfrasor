package de.ecconia.winfrasor.api;

import java.util.HashMap;
import java.util.Map;

import de.ecconia.winfrasor.TabData;
import de.ecconia.winfrasor.components.ReplacerPane;
import de.ecconia.winfrasor.components.ReplacerRoot;
import de.ecconia.winfrasor.components.tabpane.TabPane;

public class Winfrasor
{
	private final Map<String, TabData> tabsByName = new HashMap<>();
	
	public Winfrasor()
	{
	}
	
	public Map<String, TabData> getTabsByName()
	{
		return tabsByName;
	}
	
	//GENERATORS:
	
	public Root genRootPane()
	{
		return new ReplacerRoot();
	}
	
	public Root genRootPane(Element element)
	{
		return new ReplacerRoot(element);
	}
	
	public Splitter genSplitter(Orientation orientation, Element first, Element second)
	{
		return new ReplacerPane(orientation, first, second);
	}
	
	public TabHolder genTabHolder()
	{
		return new TabPane();
	}
}
