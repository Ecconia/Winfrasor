package de.ecconia.winfrasor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.ecconia.winfrasor.components.ReplacerPane;
import de.ecconia.winfrasor.components.ReplacerRoot;
import de.ecconia.winfrasor.components.tabpane.TabPane;
import de.ecconia.winfrasor.components.tabpane.TabPaneEntry;
import de.ecconia.winfrasor.factories.FactoryContext;
import de.ecconia.winfrasor.misc.NoContent;

public class StartWinfrasor
{
	/*
	 * VAGUE/MINOR TODO's:
	 * - New Button (Is that even needed?)
	 * - Optimization, no unnecessary calls, no redundant rendering.
	 * - Threadsafing, only work from AWT thread, lock components, and more?
	 * - Retina support.
	 * - New Tab support.
	 * - If the last Tab of a neverClose TabHolder gets moved, move the TabHolder instead/along.
	 */
	/*
	 * MAJOR TODO's:
	 * - Add icon support.
	 * - Style everything, prevent eye cancer. (Difficult).
	 * - Create API to interact with the whole framework, but preventing Swing abuse.
	 * - Move all styling into UI classes, allow custom style mods.
	 * - TBI: Maybe use the original TabPane class?
	 * - Overflow handling.
	 */
	
	private static FactoryContext factories;
	
	public static void main(String[] args)
	{
		//This listener could be used to prevent windows with persistent tabs from closing.
		WindowAdapter onlyCloseWindowIfItDoesNotContainPersistentTabsAdapter = new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				JFrame window = (JFrame) e.getWindow();
				//Search for persistent tabs:
				boolean containsPersistentTabs = false;
				//WARNING: Very unsafe code. Only works if the framework is not abused. With an API this would be much more pretty.
				outer:
				{
					//Search for persistent tabs.
					JPanel contentPane = (JPanel) window.getContentPane();
					ReplacerRoot replacerRoot = (ReplacerRoot) contentPane.getComponent(0);
					ReplacerPane pane = (ReplacerPane) replacerRoot.getComponent(0);
					
					LinkedList<ReplacerPane> panes = new LinkedList<>();
					panes.addLast(pane);
					while(!panes.isEmpty())
					{
						pane = panes.removeFirst();
						for(Component component : pane.getComponents())
						{
							if(component instanceof TabPane)
							{
								TabPane tabber = (TabPane) component;
								for(Component rawTab : ((Container) tabber.getComponent(0)).getComponents())
								{
									TabPaneEntry entry = (TabPaneEntry) rawTab;
									if(entry.getTab().isPersistent())
									{
										containsPersistentTabs = true;
										break outer;
									}
								}
							}
							else if(component instanceof ReplacerPane)
							{
								panes.addLast((ReplacerPane) component);
							}
						}
					}
				}
				
				if(!containsPersistentTabs)
				{
					window.dispose();
				}
			}
		};
		factories = new FactoryContext();
		//Default factory:
//		factories.setTabPaneFactory(() -> {
//			return new TabPane(factories);
//		});
		factories.setDragFailedHandler((tabData, dropLocation) -> {
			JFrame someFrame = new JFrame("Generated window...");
			someFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			someFrame.addWindowListener(onlyCloseWindowIfItDoesNotContainPersistentTabsAdapter);
			someFrame.setLocation(dropLocation.x, dropLocation.y);
			
			TabPane tabber = factories.createTabPane();
			tabber.addTab(tabData);
			final ReplacerRoot rootPane = new ReplacerRoot(factories, tabber);
			someFrame.add(rootPane.asComponent());
			
			someFrame.pack();
			someFrame.setVisible(true);
		});
		
		//Origin of tabs, just an infinite generator as source for the other frame.
		JFrame mainFrame = new JFrame("New Empty Frame");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainFrame.setLocation(1300, 100);
		mainFrame.setPreferredSize(new Dimension(500, 100));
		
		TabPane tabber = factories.createTabPane();
		ReplacerRoot rootPane = new ReplacerRoot(factories, tabber);
		mainFrame.add(rootPane.asComponent());
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		
		//New TabPane model:
		JFrame newMainFrame = new JFrame("New Empty Frame");
		newMainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		newMainFrame.setLocation(1300, 200);
		newMainFrame.setPreferredSize(new Dimension(500, 500));
		
		ReplacerRoot root = new ReplacerRoot(factories);
		newMainFrame.add(root.asComponent());
		
		newMainFrame.pack();
		newMainFrame.setVisible(true);
		
		//Refill the amount of TAB to 10, until the window gets closed.
		int label = 0;
		boolean removable = true;
		while(mainFrame.isVisible())
		{
			int missingAmount = 10 - tabber.getTabAmount();
			for(int i = 0; i < missingAmount; i++)
			{
				TabData tabData = new TabData("Tab: " + label, new NoContent("Long text, yay very long, hmm long, yes! " + label), removable);
				tabber.addTab(tabData);
				label++;
				removable = !removable;
			}
			
			try
			{
				Thread.sleep(1000);
			}
			catch(InterruptedException e)
			{
			}
		}
	}
}
