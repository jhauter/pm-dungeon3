package de.fhbielefeld.pmdungeon.quibble.animation;

public enum SpriteSheets
{
	//Sprite sheets for use with AnimationFactory
	//It is not necessary to use this enum because AnimationFactory
	//also directly accepts the parameters used here
	
	SPELL_ICE_BLAST("assets/textures/projectiles/IceBlast.png", 8, 1),
	SPELL_FIRE_BALL("assets/textures/projectiles/fireBall.png", 8, 1),
	ARROW("assets/textures/projectiles/arrow.png", 1, 1),
	
	BURNING_CIRCLE("assets/textures/particle/burningCircle.png", 8, 8);
	
	String file;
	int col;
	int row;
	
	SpriteSheets(String file, int col, int row)
	{
		this.file = file;
		this.col = col;
		this.row = row;
	}
}
