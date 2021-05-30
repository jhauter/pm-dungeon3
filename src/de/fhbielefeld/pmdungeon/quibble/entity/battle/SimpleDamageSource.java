package de.fhbielefeld.pmdungeon.quibble.entity.battle;

public class SimpleDamageSource implements DamageSource
{
	private final float x, y;
	
	private final CreatureStats stats;
	
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
