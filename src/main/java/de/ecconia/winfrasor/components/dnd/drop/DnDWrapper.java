package de.ecconia.winfrasor.components.dnd.drop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;

import de.ecconia.winfrasor.TabData;
import de.ecconia.winfrasor.components.Replacer;
import de.ecconia.winfrasor.components.tabpane.TabPane;

/**
 * Two classes to wrap a component to receive drop events.
 */
public abstract class DnDWrapper extends JComponent
{
	public Component getWrapped()
	{
		return getComponent(0);
	}
	
	private static void drawRing(Graphics g, int x, int y, int w, int h)
	{
		g.setColor(Color.orange);
		g.drawRect(x, y, w, h);
		g.drawRect(x + 1, y + 1, w - 2, h - 2);
		g.drawRect(x + 2, y + 2, w - 4, h - 4);
	}
	
	/**
	 * Meant for components which you fully want to replaced with dropped content.
	 */
	public static class DnDDetectorSingle extends DnDWrapper implements DropListener
	{
		private Point dndPoint;
		
		public DnDDetectorSingle()
		{
			new DropHandler(this, this);
		}
		
		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
			
			if(dndPoint != null)
			{
				drawRing(g, 0, 0, getWidth(), getHeight());
			}
		}
		
		@Override
		public void newDropPoint(Point p)
		{
			dndPoint = p;
			repaint();
		}
		
		@Override
		public boolean drop(Point object, Object obj)
		{
			TabData data = (TabData) obj;
			
			//TODO: Factory
			TabPane tabber = new TabPane();
			tabber.addTab(data);
			
			Replacer parent = (Replacer) getParent();
			parent.replace(tabber);
			return true;
		}
	}
	
	/**
	 * Meant for splitting the content into multiple areas, if you drop something into it.
	 */
	public static class DnDDetectorMulti extends DnDWrapper implements DropListener
	{
		private Point dndPoint;
		
		public DnDDetectorMulti(Component component)
		{
			new DropHandler(this, this);
			
			setLayout(new BorderLayout());
			if(component != null)
			{
				add(component);
			}
		}
		
		@Override
		public void paint(Graphics g)
		{
			super.paint(g);
			
			if(dndPoint != null)
			{
				DropLocation location = getHalf(dndPoint);
				if(location != null)
				{
					int width = getWidth();
					int height = getHeight();
					
					switch(location)
					{
						case Top:
							drawRing(g, 0, 0, width, height / 2);
							break;
						case Bottom:
							drawRing(g, 0, height / 2, width, height / 2);
							break;
						case Left:
							drawRing(g, 0, 0, width / 2, height);
							break;
						case Right:
							drawRing(g, width / 2, 0, width / 2, height);
							break;
						default:
							break;
					}
				}
			}
		}
		
		private DropLocation getHalf(Point p)
		{
			float width = getWidth();
			float height = getHeight();
			
			if(p.x < 0 || p.y < 0 || p.x > width || p.y > height)
			{
				return null;
			}
			
			boolean left = width / 2 > p.x;
			boolean top = height / 2 > p.y;
			
			float heightValue = top ? (float) p.y / height : (height - p.y) / height;
			float widthValue = left ? (float) p.x / width : (width - p.x) / width;
			
			if(heightValue < widthValue)
			{
				return top ? DropLocation.Top : DropLocation.Bottom;
			}
			else
			{
				return left ? DropLocation.Left : DropLocation.Right;
			}
		}
		
		@Override
		public void newDropPoint(Point p)
		{
			dndPoint = p;
			repaint();
		}
		
		@Override
		public boolean drop(Point p, Object obj)
		{
			DropLocation location = getHalf(p);
			if(location != null)
			{
				TabData data = (TabData) obj;
				
				//TODO: Factory, which sets if drops generate new windows for example.
				TabPane tabber = new TabPane();
				tabber.setDropCreatesNewWindow(true);
				tabber.addTab(data);
				
				Replacer parent = (Replacer) getParent();
				parent.replace(this, tabber, location);
				return true;
			}
			return false;
		}
	}
}
