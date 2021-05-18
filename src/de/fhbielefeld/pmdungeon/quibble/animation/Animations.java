package de.fhbielefeld.pmdungeon.quibble.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum Animations {
	
	
	SPELL_ICE_BLAST(new Texture(Gdx.files.internal("assets/textures/projectiles/IceBlast.png")), 8 , 1);
	
	
	Texture texture;
	int col;
	int row;

	Animations(Texture texture, int col, int row) {
		this.texture = texture;
		this.col = col;
		this.row = row;
	}
	
	

}
