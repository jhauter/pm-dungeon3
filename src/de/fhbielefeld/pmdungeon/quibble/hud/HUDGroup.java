package de.fhbielefeld.pmdungeon.quibble.hud;

import java.util.ArrayList;
import java.util.List;

public class HUDGroup
{
	final List<HUDElement> hudElements;
	
	public HUDGroup()
	{
		this.hudElements = new ArrayList<>();
	}
	
	public void addHUDElement(HUDElement e)
	{
		this.hudElements.add(e);
	}
	
	public int getNumElements()
	{
		return this.hudElements.size();
	}
	
	public HUDElement getHUDElement(int index)
	{
		return this.hudElements.get(index);
	}
}
