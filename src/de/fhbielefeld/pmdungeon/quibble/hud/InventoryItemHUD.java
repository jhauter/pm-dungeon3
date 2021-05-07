package de.fhbielefeld.pmdungeon.quibble.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class InventoryItemHUD extends HUDElement
{
	private final Inventory<Item> invRef;
	private final int invSlot;
	
	private boolean showAsEmptySlot;
	
	private int unclickableTicks;
	
	public InventoryItemHUD(Inventory<Item> inv, int slot, int x, int y, int w, int h)
	{
		super(x, y);
		this.setSize(w, h);
		this.invRef = inv;
		this.invSlot = slot;
	}

	@Override
	public String getTexturePath()
	{
		return "assets/textures/hud/slot.png";
	}
	
	@Override
	public void onMouseClicked(HUDManager hudManager)
	{
		if(this.unclickableTicks > 0)
		{
			return;
		}
		HUDElement onMouseElement = hudManager.getElementOnMouse();
		if(onMouseElement == null)
		{
			hudManager.setElementOnMouse(new InventoryItemMouseHUD(this));
			this.showAsEmptySlot = true;
		}
	}
	
	public Inventory<Item> getInventoryReference()
	{
		return this.invRef;
	}
	
	public int getInventorySlot()
	{
		return this.invSlot;
	}
	
	public void setShowAsEmpty(boolean b)
	{
		this.showAsEmptySlot = b;
	}
	
	@Override
	public void update()
	{
		super.update();
		if(this.unclickableTicks > 0)
		{
			--this.unclickableTicks;
		}
	}
	
	@Override
	public void render(SpriteBatch batch, int renderX, int renderY)
	{
		super.render(batch, renderX, renderY);
		
		if(this.showAsEmptySlot)
		{
			return;
		}
		
		final InventoryItem<Item> shownItem = this.invRef.getItem(this.invSlot);
		if(shownItem != null)
		{
			final String itemTexturePath = Item.ITEMS_TEXTURE_PATH + shownItem.getItemType().getTexture() + ".png";
			DungeonResource<Texture> texture = ResourceHandler.requestResourceInstantly(itemTexturePath, ResourceType.TEXTURE);
			if(texture.isLoaded())
			{
				batch.draw(texture.getResource(), renderX + 4, renderY + 4, this.width - 8, this.height - 8);
			}
		}
	}
}
