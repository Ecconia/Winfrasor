package de.ecconia.winfrasor.api;

import java.awt.Component;

public interface Element
{
	/**
	 * Convenience method to not cast the component yourself.
	 * @return Component - this Element as Component. 
	 */
	Component asComponent();
}
