package de.ecconia.winfrasor.api;

import java.awt.Component;

public interface Root
{
	/**
	 * Get the current child in the root.
	 * @return null or the current child of the root pane. 
	 */
	Element getChild();
	
	/**
	 * Sets the child of the root pane. Set to null to empty the root pane.
	 * @param tab - the element to insert into the root pane or null to empty it.
	 */
	void setChild(Element tab);
	
	/**
	 * Convenience method to not cast the component yourself.
	 * @return Component - this Element as Component. 
	 */
	Component asComponent();
}
