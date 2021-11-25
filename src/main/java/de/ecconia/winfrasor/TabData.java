package de.ecconia.winfrasor;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

import de.ecconia.winfrasor.api.Tab;

public class TabData
{
	private final Tab tab;
	
	public TabData(Tab tab)
	{
		this.tab = tab;
	}
	
	public String getTitle()
	{
		return tab.getTitle();
	}
	
	public Component getComponent()
	{
		return tab.getComponent();
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
		
		Component component = tab.getComponent();
		
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
