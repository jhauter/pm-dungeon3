package de.fhbielefeld.pmdungeon.quibble.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;
import de.fhbielefeld.pmdungeon.quibble.inventory.BagInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class UIElementInvSlot extends UIElement
{
	private final DungeonResource<Texture> slotBG;
	
	private final Inventory<?> inventoryRef;
	
	private final int invSlot;
	
	private boolean drawSlot;
	private boolean drawItem;
	
	private UIElementInvSlot onMouseSrcSlot;
	
	boolean isInsideBag;
	
	boolean isMarked;
	
	public UIElementInvSlot(Inventory<?> inv, int invSlot, float x, float y, float width, float height)
	{
		if(inv == null)
		{
			throw new IllegalArgumentException("inventory cannot be null");
		}
		if(invSlot < 0 || invSlot >= inv.getCapacity())
		{
			throw new IllegalArgumentException("invSlot is out of bounds of the inventory slots");
		}
		this.slotBG = ResourceHandler.requestResourceInstantly("assets/textures/hud/slot.png", ResourceType.TEXTURE);
		this.inventoryRef = inv;
		this.invSlot = invSlot;
		this.setPosition(x, y);
		this.setSize(width, height);
		this.drawSlot = true;
		this.drawItem = true;
	}
	
	@Override
	public void draw(Batch batch)
	{
		if(!this.slotBG.isLoaded())
		{
			return;
		}
		batch.begin();
		if(this.drawSlot)
		{
			batch.draw(this.slotBG.getResource(), x, y, width, height);
		}
		
		final InventoryItem<?> item = this.inventoryRef.getItem(this.invSlot);
		if(this.drawItem && item != null)
		{
			final DungeonResource<Texture> itemTexture = ResourceHandler.requestResourceInstantly(item.getItemType().getTextureFile(), ResourceType.TEXTURE);
			if(itemTexture.isLoaded())
			{
				batch.draw(itemTexture.getResource(), x + width * 0.1F, y + height * 0.1F, width * 0.8F, height * 0.8F);
			}
		}
		
		if(this.isMarked)
		{
			final DungeonResource<Texture> textureFrame = ResourceHandler.requestResourceInstantly("assets/textures/hud/item_frame.png", ResourceType.TEXTURE);
			if(textureFrame.isLoaded())
			{
				batch.draw(textureFrame.getResource(), this.x, this.y, this.width, this.height);
			}
		}
		batch.end();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean onClick(InputEvent event, float x, float y, int pointer, int button)
	{
		UIElement onMouse = this.uiLayer.uiManager.getElementOnMouse();
		if(onMouse != null && !(onMouse instanceof UIElementInvSlot))
		{
			//If something is already grabbed and it's not an inventory slot
			return true;
		}
		
		if(onMouse instanceof UIElementInvSlot)
		{
			//If an item is on the mouse
			UIElementInvSlot slotOnMouse = (UIElementInvSlot)onMouse;
			
			if((slotOnMouse.inventoryRef.getItem(slotOnMouse.invSlot) instanceof BagInventoryItem<?, ?> && this.isInsideBag)
				|| (this.inventoryRef.getItem(this.invSlot) instanceof BagInventoryItem<?, ?> && slotOnMouse.onMouseSrcSlot.isInsideBag))
			{
				//Don't allow bags to be placed inside bags
				return true;
			}
			
			Inventory.swap((Inventory<Item>)this.inventoryRef, this.invSlot, (Inventory<Item>)slotOnMouse.inventoryRef, slotOnMouse.invSlot);
			slotOnMouse.onMouseSrcSlot.drawItem = true;
			
			this.uiLayer.uiManager.setElementOnMouse(null);
			
			return true;
		}
		else
		{
			//If no item is on the mouse
			
			if(this.inventoryRef.getItem(invSlot) == null)
			{
				//If no item is in the slot
				return true;
			}
			
			//Create item on mouse
			UIElementInvSlot justGrabbed = new UIElementInvSlot(inventoryRef, invSlot, 0, 0, width, height);
			justGrabbed.drawSlot = false;
			justGrabbed.onMouseSrcSlot = this;
			this.drawItem = false;
			this.uiLayer.uiManager.setElementOnMouse(justGrabbed);
			return true;
		}
	}
	
	public Inventory<?> getInventoryReference()
	{
		return this.inventoryRef;
	}
	
	public int getInventorySlotIndex()
	{
		return this.invSlot;
	}
}
