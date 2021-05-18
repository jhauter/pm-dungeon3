package de.fhbielefeld.pmdungeon.quibble.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationFactory {

	private static AnimationFactory animationFactory = new AnimationFactory();
	
	Texture texture;
	
	public Animation<TextureRegion> createAnimation(Animations animation){
		
		texture = animation.texture;
		
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / animation.col, texture.getHeight() / animation.row);
		
		TextureRegion [] frames = new TextureRegion[animation.col * animation.row];
		int index = 0;
		for (int i = 0; i < animation.row; i++) {
			for (int j = 0; j < animation.col; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		
		Animation<TextureRegion> finishedAnimation = new Animation<>(0.1f, frames);
		
		return finishedAnimation;
	}

	public static AnimationFactory getAnimationFactory() {
		return animationFactory;
	}

}
