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
	
	private final boolean isInsideBag;
	
	public InventoryItemHUD(Inventory<Item> inv, int slot, int x, int y, int w, int h, boolean inBag)
	{
		super(x, y);
		this.setSize(w, h);
		this.invRef = inv;
		this.invSlot = slot;
		this.isInsideBag = inBag;
		this.unclickableTicks = 5;
	}
	
	public InventoryItemHUD(Inventory<Item> inv, int slot, int x, int y, int w, int h)
	{
		this(inv, slot, x, y, w, h, false);
	}

	@Override
	public String getTexturePath()
	{
		return "assets/textures/hud/slot.png";
	}
	
	public boolean isInsideBag()
	{
		return this.isInsideBag;
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
		this.unclickableTicks = 5;
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
	
	public void setUnclickableTicks(int t)
	{
		this.unclickableTicks = t;
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