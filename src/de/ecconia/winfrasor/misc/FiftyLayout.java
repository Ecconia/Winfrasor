package de.ecconia.winfrasor.misc;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import de.ecconia.winfrasor.api.Orientation;

public class FiftyLayout implements LayoutManager
{
	private final Orientation orientation;
	
	public FiftyLayout(Orientation orientation)
	{
		this.orientation = orientation;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp)
	{
	}
	
	@Override
	public void removeLayoutComponent(Component comp)
	{
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent)
	{
		int amount = parent.getComponentCount();
		if(amount != 2)
		{
			throw new IllegalStateException("Unexpected amount of children in FiftyLayout: " + amount + "/2");
		}
		
		return merge(parent.getComponent(0).getPreferredSize(), parent.getComponent(1).getPreferredSize());
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent)
	{
		int amount = parent.getComponentCount();
		if(amount != 2)
		{
			throw new IllegalStateException("Unexpected amount of children in FiftyLayout: " + amount + "/2");
		}
		
		return merge(parent.getComponent(0).getMinimumSize(), parent.getComponent(1).getMinimumSize());
	}
	
	private Dimension merge(Dimension a, Dimension b)
	{
		int width;
		int height;
		
		if(orientation == Orientation.Y)
		{
			width = Math.max(a.width, b.width);
			height = a.height + b.height;
		}
		else
		{
			width = a.width + b.width;
			height = Math.max(a.height, b.height);
		}
		
		return new Dimension(width, height);
	}
	
	@Override
	public void layoutContainer(Container parent)
	{
		int amount = parent.getComponentCount();
		if(amount != 2)
		{
			throw new IllegalStateException("Unexpected amount of children in FiftyLayout: " + amount + "/2");
		}
		
		synchronized(parent.getTreeLock())
		{
			Insets insets = parent.getInsets();
			
			int x = insets.left;
			int y = insets.top;
			
			Rectangle bounds = parent.getBounds();
			
			int width = bounds.width - (insets.left + insets.right);
			int height = bounds.height - (insets.top + insets.bottom);
			
			if(orientation == Orientation.X)
			{
				int hw = width / 2;
				int hw2 = width - hw;
				parent.getComponent(0).setBounds(x,      y, hw, height);
				parent.getComponent(1).setBounds(x + hw, y, hw2, height);
			}
			else
			{
				int hh = height / 2;
				int hh2 = height - hh;
				parent.getComponent(0).setBounds(x, y, width, hh);
				parent.getComponent(1).setBounds(x, y + hh, width, hh2);
			}
			
		}
	}
}
