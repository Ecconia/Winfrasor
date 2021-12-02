package de.ecconia.winfrasor;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

public class TabData
{
	private final String title;
	private final Component component;
	private final boolean persistent;
	
	public TabData(String title, Component component)
	{
		this(title, component, false);
	}
	
	public TabData(String title, Component component, boolean persistent)
	{
		this.title = title;
		this.component = component;
		this.persistent = persistent;
	}
	
	public boolean isPersistent()
	{
		return persistent;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public Component getComponent()
	{
		return component;
	}
	
	/**
	 * Internal method, to generate the DnD snapshot image of the Tab. Width and Height are the size of this pane.
	 */
	public Image generateImage(int width, int height)
	{
		if(width <= 0 || height <= 0)
		{
			return null;
		}
		
		Rectangle rect = component.getBounds();
		
		component.setSize(width, height);
		
		BufferedImage bufferedImage = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
		
		component.paint(g);
		
		g.dispose();
		
		component.setBounds(rect);
		
		//TBI: Maybe the size is already set.
		return bufferedImage;
	}
	
	public Icon getIcon()
	{
		return null;
	}
}
