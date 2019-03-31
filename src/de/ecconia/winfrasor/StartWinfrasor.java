package de.ecconia.winfrasor;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.ecconia.winfrasor.components.ReplacerRoot;
import de.ecconia.winfrasor.components.tabpane.TabPane;
import de.ecconia.winfrasor.misc.NoContent;

public class StartWinfrasor
{
	/*
	 * VAGUE/MINOR TODO's:
	 * - New Button (Is that even needed?)
	 * - Optimization, no unnessecary calls, no redundant rendering.
	 * - Threadsafing, only work from AWT thread, lock components, and moar?
	 * - Retina support.
	 * - New Tab support.
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
		//Origin of tabs, just an infinite generator as source for the other frame.
		JFrame mainFrame = new JFrame("New Empty Frame");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainFrame.setLocation(1300, 100);
		mainFrame.setPreferredSize(new Dimension(500, 100));
		
		TabPane tabber = new TabPane();
		mainFrame.add(new ReplacerRoot(tabber));
		
		mainFrame.dispose();
		
		mainFrame.pack();
		mainFrame.setVisible(true);
		
		//New TabPane model:
		JFrame newMainFrame = new JFrame("New Empty Frame");
		newMainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		newMainFrame.setLocation(1300, 200);
		newMainFrame.setPreferredSize(new Dimension(500, 500));
		
		newMainFrame.add(new ReplacerRoot());
		
		newMainFrame.dispose();
		
		newMainFrame.pack();
		newMainFrame.setVisible(true);
		
		//Refill the amount of TAB to 10, until the window gets closed.
		int label = 0;
		while(mainFrame.isVisible())
		{
			int missingAmount = 10 - tabber.getTabCount();
			for(int i = 0; i < missingAmount; i++)
			{
				TabData tab = new TabData("Tab: " + label, new NoContent("" + label));
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
