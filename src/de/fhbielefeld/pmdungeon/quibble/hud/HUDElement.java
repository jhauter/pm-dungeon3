package de.fhbielefeld.pmdungeon.quibble.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;

public abstract class HUDElement
{
	private int x, y;
	protected int width, height;
	
	protected float alpha = 1.0F;
	
	public HUDElement(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int w, int h)
	{
		this.width = w;
		this.height = h;
	}
	
	public final int getX()
	{
		return this.x;
	}
	
	public final int getY()
	{
		return this.y;
	}
	
	public final DungeonResource<Texture> getTexture()
	{
		if(this.getTexturePath() == null || this.getTexturePath().isEmpty())
		{
			return null;
		}
		return ResourceHandler.requestResourceInstantly(this.getTexturePath(), ResourceType.TEXTURE);
	}
	
	public abstract String getTexturePath();
	
	public final int getWidth()
	{
		return this.width;
	}
	
	public final int getHeight()
	{
		return this.height;
	}
	
	public void onMouseClicked(HUDManager hudManager)
	{
		
	}
	
	public void onClickWithGrabbedElement(HUDManager hudManager, HUDElement grabbedElement)
	{
		
	}
	
	public void onClickAsGrabbedElement(HUDManager hudManager, HUDElement clickedOn)
	{
		
	}
	
	public void update()
	{
		
	}
	
	public void onDelete()
	{
		
	}
	
	public void render(SpriteBatch batch, int renderX, int renderY)
	{
		if(this.getTexture() == null || !this.getTexture().isLoaded())
		{
			return;
		}
		batch.draw(getTexture().getResource(), renderX, renderY, width, height);
	}
	
	public boolean shouldAutoDelete()
	{
		return false;
	}
}
