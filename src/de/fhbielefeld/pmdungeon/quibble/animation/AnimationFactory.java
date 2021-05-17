package de.fhbielefeld.pmdungeon.quibble.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationFactory {
	
	private static final int PIXEL_HEIGHT = 32;
	Texture texture;
	
	public Animation<TextureRegion> createAnimation(Animations animation){
		
		texture = animation.texture;
		
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / animation.col, PIXEL_HEIGHT);
		
		TextureRegion [] frames = new TextureRegion[animation.col];
		
		int index = 0;
		for (int i = 0; i < animation.col; i++) {
			frames[index++] = tmp[i][0];
		}
		
		Animation<TextureRegion> finishedAnimation = new Animation<>(0.1f, frames);
		
		return finishedAnimation;
	}

}
