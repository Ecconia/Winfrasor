package de.ecconia.winfrasor.api;

public interface Splitter extends Element
{
	void setOrientation(Orientation orientation);
	
	Orientation getOrientation();
	
	void setFirst(Element element);
	
	void setSecond(Element element);
	
	Element getFirst();
	
	Element getSecond();
	
	void setDistribution(float f);
	
	float getDistribution();
}
