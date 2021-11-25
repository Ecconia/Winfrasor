package de.ecconia.winfrasor.components.tabpane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;

import javax.swing.JComponent;

import de.ecconia.winfrasor.TabData;
import de.ecconia.winfrasor.components.Colors;
import de.ecconia.winfrasor.components.Replacer;
import de.ecconia.winfrasor.components.dnd.drop.DropHandler;
import de.ecconia.winfrasor.components.dnd.drop.DropListener;

public class TabPaneHeader extends JComponent implements DropListener
{
	private Point dndPoint;
	private TabPaneEntry active;
	private boolean closeOnEmpty = true;
	
	private final TabPaneBody body;
	
	public TabPaneHeader(TabPaneBody body)
	{
		this.body = body;
		
		new DropHandler(this, this);
		
		setBackground(Colors.spaceBG);
		
		setLayout(new TabLayout());
		setMinimumSize(new Dimension(30, 30));
	}
	
	public void setCloseOnEmpty(boolean closeOnEmpty)
	{
		this.closeOnEmpty = closeOnEmpty;
		
		if(closeOnEmpty)
		{
			checkNotEmpty();
		}
	}
	
	public void clicked(TabPaneEntry entry)
	{
		if(active != null)
		{
			active.setActive(false);
		}
		active = entry;
		if(active != null)
		{
			active.setActive(true);
			body.setContent(active.getTab().getComponent());
		}
		else
		{
			body.setContent(null);
		}
	}
	
	public void closed(TabPaneEntry entry)
	{
		//TODO: Uff, make better.
		if(active == entry)
		{
			int index = getIndexForEntry(entry);
			if(index == getComponentCount() - 1)
			{
				if(index == 0)
				{
					clicked(null);
				}
				else
				{
					clicked((TabPaneEntry) getComponent(index - 1));
				}
			}
			else
			{
				clicked((TabPaneEntry) getComponent(index + 1));
			}
		}
		
		remove(entry);
		
		if(checkNotEmpty())
		{
			invalidate();
			validate();
			repaint();
		}
	}
	
	private boolean checkNotEmpty()
	{
		if(closeOnEmpty && getComponentCount() == 0)
		{
			Replacer replacer = (Replacer) getParent().getParent();
			//TBI: Nope, Nope. Please investigate and improve.
			//Does this still happen? -> Yes.
			if(replacer != null)
			{
				replacer.removeComp(getParent());
			}
			else
			{
				System.out.println("The one thing which should not be null just was null... Investigate! #TabPaneHeader");
			}
			
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private int getIndexForEntry(TabPaneEntry entry)
	{
		for(int i = 0; i < getComponentCount(); i++)
		{
			if(entry == getComponent(i))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		super.paint(g);
		
		if(dndPoint != null)
		{
			int index = getDropIndex(dndPoint);
			if(index < 0)
			{
				g.setColor(Color.orange);
				g.fillRect(-2, 0, 4, 30);
			}
			else
			{
				Component hover = getComponent(index);
				g.setColor(Color.orange);
				g.fillRect(hover.getX() + hover.getWidth() - 2, 0, 4, 30);
			}
		}
	}
	
	/**
	 * Returns the index of component when the border is printed after it.
	 * That's cause the code later has to query the component, thus the index should always be valid.
	 * If the index is -1 that means the border before the first index is meant.
	 */
	private int getDropIndex(Point p)
	{
		int xOffset = 0;
		for(int i = 0; i < getComponentCount(); i++)
		{
			//No point in checking from here on.
			if(xOffset > getWidth())
			{
				break;
			}
			
			Component comp = getComponent(i);
			if(!comp.isVisible())
			{
				continue;
			}
			
			int width = comp.getWidth();
			int offset = p.x - comp.getX();
			if(offset < width)
			{
				//Found index.
				int pointOffset = p.x - comp.getX();
				if(pointOffset > comp.getWidth() / 2)
				{
					return i;
				}
				else
				{
					return i - 1;
				}
			}
			
			xOffset += width;
		}
		
		//Return the last possible index.
		return getComponentCount() - 1;
	}
	
	@Override
	public void newDropPoint(Point p)
	{
		dndPoint = p;
		repaint();
	}
	
	public void addTab(TabData tab)
	{
		addTab(tab, getComponentCount());
	}
	
	public void addTab(TabData tab, int index)
	{
		if(index > getComponentCount())
		{
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + getComponentCount());
		}
		
		TabPaneEntry entry = new TabPaneEntry(tab, this);
		add(entry, index);
		
		if(active == null)
		{
			clicked(entry);
		}
		
		invalidate();
		validate();
		repaint();
	}
	
	@Override
	public boolean drop(Point p, Object obj)
	{
		//TODO: Consider point.
		TabData tab = (TabData) obj;
		
		addTab(tab, getDropIndex(p) + 1);
		
		return true;
	}
	
	private static class TabLayout implements LayoutManager
	{
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
			int width = 0;
			
			for(int i = 0; i < parent.getComponentCount(); i++)
			{
				Component comp = parent.getComponent(i);
				if(!comp.isVisible())
				{
					continue;
				}
				
				width += comp.getPreferredSize().width;
			}
			
			return new Dimension(Math.max(100, width), 30);
		}
		
		@Override
		public Dimension minimumLayoutSize(Container parent)
		{
			return new Dimension(100, 30);
		}
		
		@Override
		public void layoutContainer(Container parent)
		{
			synchronized(parent.getTreeLock())
			{
				Insets insets = parent.getInsets();
				
				//TODO: Consider layout issue.
				int xOffset = insets.left;
				for(int i = 0; i < parent.getComponentCount(); i++)
				{
					Component comp = parent.getComponent(i);
					if(comp.isVisible())
					{
						int width = comp.getPreferredSize().width;
						comp.setBounds(xOffset, 0, width, 30);
						xOffset += width;
					}
					else
					{
						comp.setBounds(xOffset, 0, 0, 30);
					}
				}
			}
		}
	}
}
