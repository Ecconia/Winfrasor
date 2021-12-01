package de.ecconia.winfrasor.api;

public interface TabHolder extends Element
{
	Tab getSelected();
	
	int getTabAmount();
	
	boolean setSelected(Tab tab);
	
	boolean setSelected(int index);
	
	boolean addTab(Tab tab);
	
	boolean addTab(Tab tab, int index);
	
	boolean removeTab(Tab tab);
	
	boolean removeTab(int index);
	
	void setNeverClose(boolean neverClose);
	
	void setDropCreatesNewWindow(boolean dropCreatesNewWindow);
}
