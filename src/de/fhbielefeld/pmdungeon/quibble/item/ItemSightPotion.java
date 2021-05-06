package de.fhbielefeld.pmdungeon.quibble.item;

import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.item.visitor.ItemVisitor;
import de.fhbielefeld.pmdungeon.quibble.trap.Trap;

public class ItemSightPotion extends ItemPotion
{
	private final double timeOfSight;
	
	private Trap tmp;
	
	/**
	 * Creates a healing potion item.
	 * @param displayName user friendly display name
	 * @param healAmount the amount this potion should heal
	 * @param texture texture that is used to render the item
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
	public void onUse(Creature user)
	{
		
		List<Entity> t = (List<Entity>) user.getLevel().getEntitiesInRadius(5, 5, 9999, user);
		for (Entity entity : t) {
			tmp = (Trap) entity;
			if( entity instanceof Trap) {
				((Trap)entity).setActivated(true);;
			}
		}
	}
	
	

	@Override
	public void accept(ItemVisitor visitor)
	{
//		visitor.visit(this);
	}
}
