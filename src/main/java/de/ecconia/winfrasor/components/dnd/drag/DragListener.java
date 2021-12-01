package de.ecconia.winfrasor.components.dnd.drag;

import java.awt.Point;

/**
 * Callback methods for the DragHandler class.
 */
public interface DragListener
{
	void dragStart();
	
	void dragEnd();
	
	void dragSuccessful();
	
	void dragFailed(Point location);
}
