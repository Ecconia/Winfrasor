package de.ecconia.winfrasor.api;

import java.awt.Component;

public class Tab
{
	protected String title;
	protected Component component;
	
	public Tab(String title, Component component)
	{
		this.title = title;
		this.component = component;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public Component getComponent()
	{
		return component;
	}
}
