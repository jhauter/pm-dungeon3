package de.fhbielefeld.pmdungeon.quibble.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;

public class HealthDisplayHUD extends HUDElement
{
	private final Creature creature;
	
	public HealthDisplayHUD(Creature creature, int x, int y)
	{
		super(x, y);
		this.creature = creature;
	}

	@Override
	public String getTexturePath()
	{
		return null;
	}
	
	@Override
	public void render(SpriteBatch batch, int renderX, int renderY)
	{
		DungeonResource<Texture> full = ResourceHandler.requestResourceInstantly("assets/textures/hud/hfull.png", ResourceType.TEXTURE);
		DungeonResource<Texture> empty = ResourceHandler.requestResourceInstantly("assets/textures/hud/hempty.png", ResourceType.TEXTURE);
		if(full.isLoaded() && empty.isLoaded())
		{
			int maxhealth = (int)this.creature.getMaxHealth();
			int currenthealth = (int)this.creature.getCurrentHealth();
			
			for(int i = 0; i < maxhealth; ++i)
			{
				if(i < currenthealth)
				{
					batch.draw(full.getResource(), renderX + i * 35, renderY, 30, 30);
				}
				else
				{
					batch.draw(empty.getResource(), renderX + i * 35, renderY, 30, 30);
				}
			}
		}
	}
}
