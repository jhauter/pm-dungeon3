package de.fhbielefeld.pmdungeon.quibble.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum SpriteSheets
{
	
	//Sprite sheets for use with AnimationFactory
	
	SPELL_ICE_BLAST(new Texture(Gdx.files.internal("assets/textures/projectiles/IceBlast.png")), 8, 1),
	SPELL_FIRE_BALL(new Texture(Gdx.files.internal("assets/textures/projectiles/fireBall.png")), 8, 1),
	ARROW(new Texture(Gdx.files.internal("assets/textures/projectiles/arrow.png")), 1, 1),
	
	BURNING_CIRCLE(new Texture(Gdx.files.internal("assets/textures/particle/burningCircle.png")), 8, 8);
	
	Texture texture;
	int col;
	int row;
	
	SpriteSheets(Texture texture, int col, int row)
	{
		this.texture = texture;
		this.col = col;
		this.row = row;
	}
	
}
