package de.ecconia.winfrasor.misc;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class SplitterLayout implements LayoutManager
{
	private final int gap;
	private final Orientation orientation;
	
	private float distribution;
	
	public SplitterLayout(Orientation orientation, int gap, float distribution)
	{
		this.gap = gap;
		this.orientation = orientation;
		this.distribution = distribution;
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
		validateComponentAmount(parent);
		
		Dimension a = parent.getComponent(0).getPreferredSize();
		Dimension b = parent.getComponent(2).getPreferredSize();
		return merge(a, b, parent.getInsets());
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent)
	{
		validateComponentAmount(parent);
		
		Dimension a = parent.getComponent(0).getMinimumSize();
		Dimension b = parent.getComponent(2).getMinimumSize();
		return merge(a, b, parent.getInsets());
	}
	
	//TODO: Consider distribution. Let the smaller side weight more.
	private Dimension merge(Dimension a, Dimension b, Insets insets)
	{
		int width;
		int height;
		
		if(orientation == Orientation.Y)
		{
			width = Math.max(a.width, b.width);
			height = a.height + gap + b.height;
		}
		else
		{
			width = a.width + gap + b.width;
			height = Math.max(a.height, b.height);
		}
		
		return new Dimension(width + insets.left + insets.right, height + insets.bottom + insets.top);
	}
	
	@Override
	public void layoutContainer(Container parent)
	{
		synchronized(parent.getTreeLock())
		{
			validateComponentAmount(parent);
			
			Insets insets = parent.getInsets();
			int width = parent.getWidth() - (insets.left + insets.right);
			int height = parent.getHeight() - (insets.bottom + insets.top);
			
			if(orientation == Orientation.X)
			{
				width -= gap;
				int first = (int) ((float) width * distribution);
				int second = width - first;
				
				parent.getComponent(0).setBounds(insets.left, insets.top, first, height);
				parent.getComponent(1).setBounds(insets.left + first, insets.top, gap, height);
				parent.getComponent(2).setBounds(insets.left + first + gap, insets.top, second, height);
			}
			else
			{
				height -= gap;
				int first = (int) ((float) height * distribution);
				int second = height - first;
				
				parent.getComponent(0).setBounds(insets.left, insets.top, width, first);
				parent.getComponent(1).setBounds(insets.left, insets.top + first, width, gap);
				parent.getComponent(2).setBounds(insets.left, insets.top + first + gap, width, second);
			}
		}
	}
	
	private void validateComponentAmount(Container parent)
	{
		if(parent.getComponentCount() != 3)
		{
			for(int i = 0; i < parent.getComponentCount(); i++)
			{
				System.out.println(parent.getComponent(i));
			}
			throw new IllegalStateException(getClass().getSimpleName() + " only works with 3 child components. Currently: " + parent.getComponentCount());
		}
	}
	
	public void setDistribution(float distribution)
	{
		this.distribution = distribution;
	}
}
