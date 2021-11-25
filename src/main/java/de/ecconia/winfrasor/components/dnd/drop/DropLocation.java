package de.ecconia.winfrasor.components.dnd.drop;

import de.ecconia.winfrasor.api.Orientation;

public enum DropLocation
{
	Top(true, Orientation.Y),
	Bottom(false, Orientation.Y),
	Left(true, Orientation.X),
	Right(false, Orientation.X),
	;
	
	private final boolean isFirst;
	private final Orientation orientation;
	
	DropLocation(boolean isFirst, Orientation orientation)
	{
		this.isFirst = isFirst;
		this.orientation = orientation;
	}
	
	public boolean isFirst()
	{
		return isFirst;
	}
	
	public Orientation getOrientation()
	{
		return orientation;
	}
}
