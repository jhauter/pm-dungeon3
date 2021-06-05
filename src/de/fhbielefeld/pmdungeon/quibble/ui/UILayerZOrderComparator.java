package de.fhbielefeld.pmdungeon.quibble.ui;

import java.util.Comparator;

public class UILayerZOrderComparator implements Comparator<UILayer>
{
	@Override
	public int compare(UILayer o1, UILayer o2)
	{
		return o1.zIndex - o2.zIndex;
	}
}
