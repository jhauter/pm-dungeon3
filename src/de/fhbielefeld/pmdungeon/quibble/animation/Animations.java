package de.fhbielefeld.pmdungeon.quibble.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum Animations {
	
	SPELL_ICE_BLAST(new Texture(Gdx.files.internal("pika_special_attack.png")), 10);
	
	
	Texture texture;
	int col;
	

	Animations(Texture texture, int col) {
		this.texture = texture;
		this.col = col;
	}
	

}
