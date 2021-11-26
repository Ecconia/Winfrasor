package de.ecconia.winfrasor.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import de.ecconia.winfrasor.api.Element;
import de.ecconia.winfrasor.api.Root;
import de.ecconia.winfrasor.components.dnd.drop.DropLocation;

/**
 * This class is a root handler component to be inserted as top-level entry for this framework.
 *
 * It mainly hosts a ReplacerPane, with a start component (some sort of Tab-View) or nothing.
 */
public class ReplacerRoot extends JComponent implements Replacer, Root
{
	public ReplacerRoot(Element element)
	{
		this((Component) element);
	}
	
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
		this((Component) null);
	}
	
	@Override
	public void replace(Component component)
	{
		//Won't be called, cause a DnDGenericReceiver always wraps the content and never throws it.
		throw new UnsupportedOperationException("This method should never be called.");
	}
	
	@Override
	public void replace(Component identifier, Component component, DropLocation location)
	{
		//Won't be called, cause a DnDGenericReceiver always wraps the content and never throws it.
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
		setChild(component);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		super.paint(g);
	}
	
	@Override
	public Element getChild()
	{
		Component comp = getComponent(0);
		if(comp instanceof ReplacerPane)
		{
			return ((ReplacerPane) comp).getElement();
		}
		else
		{
			throw new IllegalStateException("The root pane should only and always contain a " + ReplacerPane.class.getSimpleName() + ", but " + comp.getClass().getSimpleName() + " was found.");
		}
	}
	
	private void setChild(Component component)
	{
		removeAll();
		//In some cases component is not wrapped anymore. (Was original a TAB or something). This wraps it again to ensure proper treatment.
		//By now it can be something inserted (external) too.
		if(!(component instanceof ReplacerPane))
		{
			component = new ReplacerPane(component);
		}
		add(component);
	}
	
	@Override
	public void setChild(Element tab)
	{
		setChild(tab.asComponent());
	}
	
	@Override
	public Component asComponent()
	{
		return this;
	}
}
