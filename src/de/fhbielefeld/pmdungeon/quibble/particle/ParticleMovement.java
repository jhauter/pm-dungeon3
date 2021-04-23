package de.fhbielefeld.pmdungeon.quibble.particle;

public interface ParticleMovement
{
	public void originValues(float x, float y);
	
	public float getRotation(float time);
	
	public float getOffsetX(float time);
	
	public float getOffsetY(float time);
	
	public float getScaleX(float time);
	
	public float getScaleY(float time);
}
