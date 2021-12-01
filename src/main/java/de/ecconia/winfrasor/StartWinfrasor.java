package de.ecconia.winfrasor;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.ecconia.winfrasor.api.Root;
import de.ecconia.winfrasor.api.Tab;
import de.ecconia.winfrasor.api.TabHolder;
import de.ecconia.winfrasor.api.Winfrasor;
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
	
	public static void main(String[] args)
	{
		Winfrasor core = new Winfrasor();
		
		//Origin of tabs, just an infinite generator as source for the other frame.
		JFrame mainFrame = new JFrame("New Empty Frame");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainFrame.setLocation(1300, 100);
		mainFrame.setPreferredSize(new Dimension(500, 100));
		
		TabHolder tabber = core.genTabHolder();
		tabber.setDropCreatesNewWindow(true);
		Root rootPane = core.genRootPane(tabber);
		mainFrame.add(rootPane.asComponent());
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		
		//New TabPane model:
		JFrame newMainFrame = new JFrame("New Empty Frame");
		newMainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		newMainFrame.setLocation(1300, 200);
		newMainFrame.setPreferredSize(new Dimension(500, 500));
		
		Root root = core.genRootPane();
		newMainFrame.add(root.asComponent());
		
		newMainFrame.pack();
		newMainFrame.setVisible(true);
		
		//Refill the amount of TAB to 10, until the window gets closed.
		int label = 0;
		while(mainFrame.isVisible())
		{
			int missingAmount = 10 - tabber.getTabAmount();
			for(int i = 0; i < missingAmount; i++)
			{
				Tab tab = new Tab("Tab: " + label, new NoContent("Long text, yay very long, hmm long, yes! " + label));
				tabber.addTab(tab);
				label++;
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
