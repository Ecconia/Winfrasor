package de.ecconia.winfrasor.components.dnd.drop;

import java.awt.Point;

public interface DropListener
{
	void newDropPoint(Point p);
	
	boolean drop(Point object, Object object2);
}
