package de.ecconia.winfrasor.components.tabpane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;

import de.ecconia.winfrasor.components.Colors;
import de.ecconia.winfrasor.components.Replacer;
import de.ecconia.winfrasor.components.dnd.drop.DnDWrapper.DnDDetectorMulti;
import de.ecconia.winfrasor.components.dnd.drop.DropLocation;

@SuppressWarnings("serial")
public class TabPaneBody extends JComponent implements Replacer
{
	private DnDDetectorMulti wrapper;
	
	public TabPaneBody()
	{
		setBackground(Colors.contentBG);
		setLayout(new BorderLayout());
		wrapper = new DnDDetectorMulti(null);
		add(wrapper);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		super.paint(g);
	}
	
	public void setContent(Component comp)
	{
		//TBI: Is changing the visibility okay?
		if(wrapper.getComponentCount() > 0)
		{
			wrapper.getComponent(0).setVisible(false);
		}
		
		wrapper.removeAll();
		if(comp != null)
		{
			comp.setVisible(true);
			wrapper.add(comp);
		}
		
		wrapper.invalidate();
		wrapper.validate();
		wrapper.repaint();
	}
	
	@Override
	public void replace(Component component)
	{
		throw new UnsupportedOperationException("The call to here should have never happend.");
	}
	
	@Override
	public void replace(Component origin, Component component, DropLocation location)
	{
		Replacer tabPaneContainer = (Replacer) getParent().getParent();
		tabPaneContainer.replace(getParent(), component, location);
	}
	
	@Override
	public void removeComp(Component identifier)
	{
		throw new UnsupportedOperationException("The call to here should have never happend.");
	}
	
	@Override
	public void free(Component identifier, Component component)
	{
		throw new UnsupportedOperationException("The call to here should have never happend.");
	}
}
