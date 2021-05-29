package de.fhbielefeld.pmdungeon.quibble.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;

public final class AnimationFactory
{
	private AnimationFactory()
	{
		
	}
	
	public static Animation<TextureRegion> createAnimation(SpriteSheets animation, float frameDuration)
	{
		return createAnimation(animation.file, animation.row, animation.col, frameDuration);
	}
	
	public static Animation<TextureRegion> createAnimation(String file, int rows, int columns, float frameDuration)
	{
		DungeonResource<Texture> texture = ResourceHandler.requestResourceInstantly(file, ResourceType.TEXTURE);
		
		if(texture.hasError())
		{
			return null;
		}
		
		TextureRegion[][] tmp = TextureRegion.split(texture.getResource(),
			texture.getResource().getWidth() / columns,
			texture.getResource().getHeight() / rows);
		
		TextureRegion[] frames = new TextureRegion[rows * columns];
		int index = 0, i, j;
		for(i = 0; i < rows; i++)
		{
			for(j = 0; j < columns; j++)
			{
				frames[index++] = tmp[i][j];
			}
		}
		
		return new Animation<>(frameDuration, frames);
	}
}
