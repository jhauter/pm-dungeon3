package de.fhbielefeld.pmdungeon.quibble.boss.bulletHell;

public class SpinMovementPattern extends ProjectileMovementPattern
{
	private int counter;
	private int speed;
	
	public SpinMovementPattern(ProjectileSpawner spawner, int speed)
	{
		super(spawner);
		this.speed = speed;
	}
	
	@Override
	public void execute()
	{
		counter += speed;
		if(counter >= 361)
		{
			counter = 0;
		}
		this.spawner.setFacing(spawner.getAngle() + counter);
	}
	
	@Override
	public boolean isValid()
	{
		return true;
	}
}
