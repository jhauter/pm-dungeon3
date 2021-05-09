package de.fhbielefeld.pmdungeon.quibble.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;

public class ExpBarHUD extends HUDElement
{
	private final Creature creature;
	
	public ExpBarHUD(Creature creature, int x, int y)
	{
		super(x, y);
		this.creature = creature;
		this.setSize(256, 16);
	}

	@Override
	public String getTexturePath()
	{
		return "assets/textures/hud/expbar.png";
	}
	
	@Override
	public void render(SpriteBatch batch, int renderX, int renderY)
	{
		super.render(batch, renderX, renderY);
		
		DungeonResource<Texture> fill = ResourceHandler.requestResourceInstantly("assets/textures/hud/expfill.png", ResourceType.TEXTURE);
		if(fill.isLoaded())
		{
			int nextLevelExp = this.creature.totalExpFunction(this.creature.getCurrentExpLevel() + 1);
			int currentLevelExp = this.creature.totalExpFunction(this.creature.getCurrentExpLevel());
			int progress = this.creature.getTotalExp() - currentLevelExp;
			
			if(nextLevelExp - currentLevelExp > 0)
			{
				int barWidth = 248 * progress / (nextLevelExp - currentLevelExp);
				batch.draw(fill.getResource(), renderX + 4, renderY + 2, barWidth, 12);
			}
		}
	}
}
