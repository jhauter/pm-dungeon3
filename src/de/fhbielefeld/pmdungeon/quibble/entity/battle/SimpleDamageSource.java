package de.fhbielefeld.pmdungeon.quibble.entity.battle;

public class SimpleDamageSource implements DamageSource
{
	private final float x, y;
	
	private final CreatureStats stats;
	
	/**
	 * Creates an object that contains <code>CreatureStats</code> and thus carry information
	 * for how damaging a creature should happen.
	 * The position of this represents the position of the attacker (knockback), but does not need to be set.
	 * @param x x-position of the attacker
	 * @param y y-position of the attacker
	 * @param stats stat values of the attacker
	 */
	public SimpleDamageSource(float x, float y, CreatureStats stats)
	{
		this.x = x;
		this.y = y;
		this.stats = stats;
	}
	
	@Override
	public float getX()
	{
		return this.x;
	}
	
	@Override
	public float getY()
	{
		return this.y;
	}
	
	@Override
	public CreatureStats getCurrentStats()
	{
		return this.stats;
	}
}
