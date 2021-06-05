package de.fhbielefeld.pmdungeon.quibble.item;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.trap.Trap;

public class ItemSightPotion extends ItemPotion
{
	private final int visibleTimeTicks;
	
	/**
	 * @param displayName user friendly display name
	 * @param visibleTimeTicks time in ticks during which traps are revealed
	 * @param texture file name of the texture without file extension.
	 * File must be in {@value Item#ITEMS_TEXTURE_PATH}.
	 */
	public ItemSightPotion(String displayName, int visibleTimeTicks, String texture)
	{
		super(displayName, texture);
		this.visibleTimeTicks = visibleTimeTicks;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onUse(Creature user, float targetX, float targetY)
	{
		List<Trap> t = user.getLevel().getAllEntitiesOf(Trap.class);
		t.forEach(trap -> trap.setVisible(this.visibleTimeTicks));
		return true;
	}
}
