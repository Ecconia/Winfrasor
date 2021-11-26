package de.ecconia.winfrasor.components.dnd.drop;

import java.awt.Point;

/**
 * Called by the DropHandler, if new drop events occur.
 */
public interface DropListener
{
	void newDropPoint(Point p);
	
	boolean drop(Point object, Object object2);
}
