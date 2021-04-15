package de.fhbielefeld.pmdungeon.quibble.entity.debug;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

/**
 * Just a debug entity that displays a red rectangle.
 * @author Andreas
 */
public class DotRed extends Entity
{
	public DotRed(float x, float y)
	{
		super(x, y);
		this.setNoclip(true);
		this.animationHandler.addAsDefaultAnimation("default", 1, Integer.MAX_VALUE, "assets/textures/debug", "dot_red");
	}
	
	@Override
	protected Point getDrawingOffsetOverride()
	{
		return new Point(0.0F, 0.0F);
	}
}
