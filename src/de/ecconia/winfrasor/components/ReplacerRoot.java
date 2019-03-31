package de.ecconia.winfrasor.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import de.ecconia.winfrasor.components.dnd.drop.DropLocation;

@SuppressWarnings("serial")
public class ReplacerRoot extends JComponent implements Replacer
{
	public ReplacerRoot(Component comp)
	{
		setBackground(Colors.contentBG);
		setLayout(new BorderLayout());
		if(comp != null)
		{
			add(new ReplacerPane(comp));
		}
		else
		{
			add(new ReplacerPane());
		}
		setBorder(new LineBorder(Colors.borderBG, 3));
	}
	
	public ReplacerRoot()
	{
		this(null);
	}
	
	@Override
	public void replace(Component component)
	{
		//Won't be called, cause a DnDGenericReciever always wraps the content and never throws it.
		throw new UnsupportedOperationException("This method should never be called.");
	}
	
	@Override
	public void replace(Component identifier, Component component, DropLocation location)
	{
		//Won't be called, cause a DnDGenericReciever always wraps the content and never throws it.
		throw new UnsupportedOperationException("This method should never be called.");
	}
	
	@Override
	public void removeComp(Component identifier)
	{
		removeAll();
		add(new ReplacerPane());
		
		invalidate();
		validate();
		repaint();
	}
	
	@Override
	public void free(Component identifier, Component component)
	{
		removeAll();
		//In some cases component is not wrapped anymore. (Was original a TAB or something). This wraps it again to ensure proper treatment.
		if(!(component instanceof ReplacerPane))
		{
			component = new ReplacerPane(component);
		}
		add(component);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		super.paint(g);
	}
}
