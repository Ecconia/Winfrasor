package de.ecconia.winfrasor.misc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class DebugBorder extends AbstractBorder
{
	private final Color color;
	
	public DebugBorder(Color color)
	{
		this.color = color;
	}
	
	public DebugBorder()
	{
		this(Color.red);
	}
	
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	{
		drawRing(g, x, y, width - 1, height - 1);
	}
	
	private void drawRing(Graphics g, int x, int y, int w, int h)
	{
		g.setColor(color);
		g.drawRect(x, y, w, h);
		g.setColor(Color.black);
		g.drawRect(x + 1, y + 1, w - 2, h - 2);
		g.setColor(color);
		g.drawRect(x + 2, y + 2, w - 4, h - 4);
	}

	
	public Insets getBorderInsets(Component c, Insets insets)
	{
		insets.set(3, 3, 3, 3);
		return insets;
	}
}
