package de.ecconia.winfrasor.factories;

import java.awt.Point;

import de.ecconia.winfrasor.TabData;

public interface DragFailedHandler
{
	void dropFailed(TabData tabData, Point dropLocation);
}
