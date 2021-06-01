package de.fhbielefeld.pmdungeon.quibble.item;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.trap.Trap;

public class ItemSightPotion extends ItemPotion
{
	private final double timeOfSight;
	
	/**
	 * Creates a healing potion item.
	 * @param displayName user friendly display name
	 * @param timeOfSight time invisible Objects are visible
	 * @param texture file name of the texture without file extension.
	 * File must be in {@value Item#ITEMS_TEXTURE_PATH}.
	 */
	public ItemSightPotion(String displayName, double timeOfSight, String texture)
	{
		super(displayName, texture);
		this.timeOfSight = timeOfSight;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onUse(Creature user, float targetX, float targetY)
	{
		List<Trap> t = user.getLevel().getAllEntitiesOf(Trap.class);
		t.forEach(trap -> trap.setVisible(true, this.timeOfSight));
		return true;
	}
}
