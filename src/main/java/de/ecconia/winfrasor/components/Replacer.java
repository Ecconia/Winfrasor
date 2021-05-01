package de.ecconia.winfrasor.components;

import java.awt.Component;

import de.ecconia.winfrasor.components.dnd.drop.DropLocation;

public interface Replacer
{
	void replace(Component component);
	
	void replace(Component identifier, Component component, DropLocation location);
	
	void removeComp(Component identifier);
	
	void free(Component identifier, Component component);
}
