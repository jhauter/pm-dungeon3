package de.fhbielefeld.pmdungeon.quibble.hud;

import de.fhbielefeld.pmdungeon.quibble.inventory.BagInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class InventoryItemMouseHUD extends HUDElement
{
	private final InventoryItemHUD sourceSlot;
	
	private boolean autoDelete;
	
	public InventoryItemMouseHUD(InventoryItemHUD sourceSlot)
	{
		super(0, 0);
		this.setSize(64, 64);
		this.sourceSlot = sourceSlot;
	}

	@Override
	public String getTexturePath()
	{
		InventoryItem<Item> item = sourceSlot.getInventoryReference().getItem(sourceSlot.getInventorySlot());
		if(item == null)
		{
			this.markAsRemovable();
			return null;
		}
		return Item.ITEMS_TEXTURE_PATH + item.getItemType().getTexture() + ".png";
	}
	
	@Override
	public void onClickAsGrabbedElement(HUDManager hudManager, HUDElement clickedOn)
	{
		if(!(clickedOn instanceof InventoryItemHUD))
		{
			return;
		}
		InventoryItemHUD slotClickedOn = (InventoryItemHUD)clickedOn;
		if(this.sourceSlot.getInventoryReference().getItem(this.sourceSlot.getInventorySlot()) instanceof BagInventoryItem<?, ?>)
		{
			//if its in a bag, dont do anything
			if(slotClickedOn.isInsideBag())
			{
				return;
			}
		}

		boolean isTargetABag = slotClickedOn.getInventoryReference().getItem(slotClickedOn.getInventorySlot()) instanceof BagInventoryItem<?, ?>;
		if(this.sourceSlot.isInsideBag() && isTargetABag)
		{
			return;
		}
		
		if(slotClickedOn != this.sourceSlot)//not clicked the same slot from where the item was taken
		{
			Inventory.swap(this.sourceSlot.getInventoryReference(), this.sourceSlot.getInventorySlot(),
				slotClickedOn.getInventoryReference(), slotClickedOn.getInventorySlot());
		}
		this.markAsRemovable();
		slotClickedOn.setUnclickableTicks(5);
	}
	
	@Override
	public boolean shouldAutoDelete()
	{
		return this.autoDelete;
	}
	
	public void markAsRemovable()
	{
		this.autoDelete = true;
	}
	
	@Override
	public void onDelete()
	{
		this.sourceSlot.setShowAsEmpty(false);
	}
}
