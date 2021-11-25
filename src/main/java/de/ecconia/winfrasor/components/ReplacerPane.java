package de.ecconia.winfrasor.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import de.ecconia.winfrasor.api.Element;
import de.ecconia.winfrasor.api.NoWrap;
import de.ecconia.winfrasor.api.Orientation;
import de.ecconia.winfrasor.api.Splitter;
import de.ecconia.winfrasor.components.dnd.drop.DnDWrapper;
import de.ecconia.winfrasor.components.dnd.drop.DnDWrapper.DnDDetectorMulti;
import de.ecconia.winfrasor.components.dnd.drop.DnDWrapper.DnDDetectorSingle;
import de.ecconia.winfrasor.components.dnd.drop.DropLocation;
import de.ecconia.winfrasor.misc.SplitterLayout;

public class ReplacerPane extends JComponent implements Replacer, Splitter
{
	private Orientation orientation;
	private float distribution = 0.5f;
	
	//TODO: UI Resource.
	private static final int splitterSize = 5;
	
	private SplitterLayout splitLayout;
	private JPanel splitterPane;
	
	public ReplacerPane()
	{
		setLayout(new BorderLayout());
		add(new DnDDetectorSingle());
		//setBorder(new DebugBorder());
	}
	
	public ReplacerPane(Component component)
	{
		setLayout(new BorderLayout());
		add(wrap(component));
		//setBorder(new DebugBorder());
	}
	
	public ReplacerPane(Orientation orientation, Element componentA, Element componentB)
	{
		this(orientation, (Component) componentA, (Component) componentB);
	}
	
	public ReplacerPane(Orientation orientation, Component componentA, Component componentB)
	{
		this.orientation = orientation;
		initSplitter();
		setLayout(splitLayout);
		//TODO: Replace with "Splitter", which takes care of the layout and interaction.
		add(wrap(componentA));
		add(splitterPane);
		add(wrap(componentB));
		//setBorder(new DebugBorder());
	}
	
	private Component wrap(Component component)
	{
		//Prevent a component to be converted to a drag target, if it doesn't wish to, or if it is already a drop target.
		if(component instanceof NoWrap || component instanceof DnDWrapper)
		{
			return component;
		}
		else
		{
			return new DnDDetectorMulti(component);
		}
	}
	
	public void replace(Component component)
	{
		removeAll();
		setLayout(new BorderLayout());
		
		add(wrap(component));
		
		invalidate();
		validate();
		repaint();
	}
	
	public void replace(Component identifier, Component component, DropLocation location)
	{
		//TBI: Undo whoever set this ever to invisible - oh snap, that was me.
		component.setVisible(true);
		
		int count = getComponentCount();
		if(count == 0)
		{
			throw new IllegalStateException("Attempting to add a Component at location of an empty DndGenericReceiver.");
		}
		else if(count > 3 || count == 2)
		{
			throw new RuntimeException("DndGenericReceiver has invalid elements count: " + count + "/3");
		}
		else
		{
			synchronized(getTreeLock())
			{
				Component componentA;
				Component componentB;
				if(count == 1)
				{
					Component oldComponent = getComponent(0);
					component = wrap(component);
					
					componentA = location.isFirst() ? component : oldComponent;
					componentB = location.isFirst() ? oldComponent : component;
					
					orientation = location.getOrientation();
				}
				else //count == 3
				{
					boolean isFirst = identifier == getComponent(0);
					//Get the component which will be unchanged by this.
					Component keep = isFirst ? getComponent(2) : getComponent(0);
					Component expand = isFirst ? getComponent(0) : getComponent(2);
					
					ReplacerPane splitter = new ReplacerPane(location.getOrientation(),
						location.isFirst() ? component : expand,
						location.isFirst() ? expand : component);
					componentA = isFirst ? splitter : keep;
					componentB = isFirst ? keep : splitter;
				}
				
				removeAll();
				initSplitter();
				setLayout(splitLayout);
				
				add(componentA);
				add(splitterPane);
				add(componentB);
				
				invalidate();
				validate();
				repaint();
			}
		}
	}
	
	@Override
	public void removeComp(Component identifier)
	{
		int count = getComponentCount();
		if(count == 1)
		{
			//TBI: Nope, Nope. Please investigate and improve.
			//Why is this null from time to time, happens at two places... This fix works though.
			Replacer replacer = (Replacer) getParent();
			if(replacer != null)
			{
				replacer.removeComp(this);
			}
		}
		else if(count == 3)
		{
			//Which component has to be removed?
			boolean isFirst = identifier == getComponent(0);
			Component keep = isFirst ? getComponent(2) : getComponent(0);
			removeAll();
			
			//No matter what, this wrapper is probably obsolete. If not just let the RootReceiver recreate it.
			//No need to check the parent type. Remove always.
			//TBI: Check parent if a not the root pane.
			((Replacer) getParent()).free(this, keep);
		}
		else
		{
			throw new RuntimeException("Attempted to remove from DndGenericReceiver but it had " + count + "/3 elements.");
		}
	}
	
	private void initSplitter()
	{
		if(splitLayout == null)
		{
			splitLayout = new SplitterLayout(orientation, splitterSize, distribution);
			splitterPane = new JPanel();
			splitterPane.setBackground(Colors.borderBG);
			splitterPane.addMouseMotionListener(new MouseMotionListener()
			{
				@Override
				public void mouseMoved(MouseEvent e)
				{
				}
				
				@Override
				public void mouseDragged(MouseEvent e)
				{
					Insets insets = getInsets();
					float distr;
					if(orientation == Orientation.X)
					{
						int width = getWidth() - 3 - insets.left - insets.right;
						int pos = splitterPane.getX() + e.getX();
						distr = 1f / (float) width * (float) (pos);
					}
					else
					{
						int height = getHeight() - 3 - insets.top - insets.bottom;
						int pos = splitterPane.getY() + e.getY();
						distr = 1f / (float) height * (float) (pos);
					}
					
					if(distr < 0)
					{
						distr = 0f;
					}
					else if(distr > 1)
					{
						distr = 1f;
					}
					
					((SplitterLayout) splitLayout).setDistribution(distr);
					
					invalidate();
					validate();
					repaint();
				}
			});
		}
	}
	
	@Override
	public void free(Component identifier, Component component)
	{
		int count = getComponentCount();
		if(count == 1)
		{
			removeAll();
			add(component);
			
			invalidate();
			validate();
			repaint();
		}
		else if(count == 3)
		{
			//Which component has to be removed?
			boolean isFirst = identifier == getComponent(0);
			Component keep = isFirst ? getComponent(2) : getComponent(0);
			removeAll();
			
			initSplitter();
			setLayout(splitLayout);
			
			add(isFirst ? component : keep);
			add(splitterPane);
			add(isFirst ? keep : component);
			
			invalidate();
			validate();
			repaint();
		}
		else
		{
			throw new RuntimeException("Attempted to remove from DndGenericReciever but it had " + count + "/3 elements.");
		}
	}

	@Override
	public void setOrientation(Orientation orientation)
	{
	}

	@Override
	public Orientation getOrientation()
	{
		return null;
	}

	/**
	 * INTERNAL Method. Returns either THIS object if this object is in Splitter mode. Or null/the wrapped child of this component. 
	 */
	public Element getElement()
	{
		int count = getComponentCount(); 
		if(count == 3)
		{
			return this;
		}
		else if(count == 1)
		{
			Component comp = getComponent(0);
			if(comp instanceof DnDDetectorSingle)
			{
				return null;
			}
			else if(comp instanceof DnDDetectorMulti)
			{
				//Assume that only Elements will be wrapped by this (once).
				return (Element) ((DnDDetectorMulti) comp).getWrapped();
			}
			else
			{
				//Assume that it implements Element
				return (Element) comp;
			}
		}
		else
		{
			throw new IllegalStateException("Replacer Pane has " + count + " children, only 3 or 1 are allowed.");
		}
	}
	
	@Override
	public void setFirst(Element element)
	{
	}

	@Override
	public void setSecond(Element element)
	{
	}

	@Override
	public Element getFirst()
	{
		return null;
	}

	@Override
	public Element getSecond()
	{
		return null;
	}

	@Override
	public Component asComponent()
	{
		return this;
	}

	@Override
	public void setDistribution(float distribution)
	{
		splitLayout.setDistribution(this.distribution = distribution);
	}

	@Override
	public float getDistribution()
	{
		return distribution;
	}
}
