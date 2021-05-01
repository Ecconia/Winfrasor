package de.ecconia.winfrasor.api;

import java.awt.Component;

public interface Element
{
	/**
	 * Convinience method to not cast the component yourself.
	 * @return Component - this Element as Component. 
	 */
	Component asComponent();
}
