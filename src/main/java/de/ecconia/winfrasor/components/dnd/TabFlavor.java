package de.ecconia.winfrasor.components.dnd;

import java.awt.datatransfer.DataFlavor;

/**
 * The custom flavor. In its own class cause used from multiple places.
 */
public class TabFlavor
{
	public static final DataFlavor flavor = new DataFlavor("application/winfrasor", "Winfrasor Tab");
}
