package de.fhbielefeld.pmdungeon.quibble.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationFactory {

	private static AnimationFactory animationFactory = new AnimationFactory();
	
	private AnimationFactory()
	{
		
	}
	
	public Animation<TextureRegion> createAnimation(SpriteSheets animation){
		
		Texture texture = animation.texture;
		
		TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / animation.col, texture.getHeight() / animation.row);
		
		TextureRegion [] frames = new TextureRegion[animation.col * animation.row];
		int index = 0;
		for (int i = 0; i < animation.row; i++) {
			for (int j = 0; j < animation.col; j++) {
				frames[index++] = tmp[i][j];
			}
		}
		
		return new Animation<>(0.1f, frames);
	}

	public static AnimationFactory getAnimationFactory() {
		return animationFactory;
	}

}
