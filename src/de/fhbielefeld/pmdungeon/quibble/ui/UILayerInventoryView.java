package de.fhbielefeld.pmdungeon.quibble.ui;

import java.util.Iterator;

import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.fhbielefeld.pmdungeon.quibble.inventory.BagInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;

public class UILayerInventoryView extends UILayer
{
	private Inventory<?> inventoryRef;
	
	private final float x;
	private final float y;
	
	private final float slotSize;
	private final float slotGap;
	
	private Label labelTitle;
	
	public UILayerInventoryView(String title, float x, float y, float slotSize, float slotGap)
	{
		this.x = x;
		this.y = y;
		this.slotSize = slotSize;
		this.slotGap = slotGap;
		this.labelTitle = this.addText(title, UIFonts.SUBTITLE, x, y + slotSize);
		this.labelTitle.setVisible(false);
	}
	
	@Override
	public void draw()
	{
		super.draw();
//		Iterator<UIElement> slotElements = this.getUIElements();
//		while(slotElements.hasNext())
//		{
//			slotElements.next();
//		}
	}
	
	public void setInventory(Inventory<?> inventory)
	{
		this.inventoryRef = inventory;
		this.labelTitle.setVisible(inventory != null);
		this.updateInventoryStructure();
	}
	
	public void setMarkedSlot(int markedSlot)
	{
		if(this.inventoryRef == null)
		{
			return;
		}
		Iterator<UIElement> slotElements = this.getUIElements();
		UIElement e;
		UIElementInvSlot slot;
		while(slotElements.hasNext())
		{
			e = slotElements.next();
			if(e instanceof UIElementInvSlot)
			{
				slot = (UIElementInvSlot)e;
				//Check reference because bags items have different reference
				if(slot.getInventoryReference() == this.inventoryRef && slot.getInventorySlotIndex() == markedSlot)
				{
					slot.isMarked = true;
				}
				else
				{
					slot.isMarked = false;
				}
			}
		}
	}
	
	private void updateInventoryStructure()
	{
		//we can do this because we know that only inventory slot elements are added
		this.clearUIElements();
		
		if(this.inventoryRef == null)
		{
			return;
		}
		
		InventoryItem<?> cItem;
		UIElementInvSlot cSlotElem;
		float nextItemX = this.x;
		
		float slotXDiff = this.slotSize + this.slotGap;
		float slotXDiffBag = slotXDiff * 0.5F;
		float slotSizeBag = this.slotSize * 0.5F;
		
		for(int i = 0, n = this.inventoryRef.getCapacity(); i < n; ++i)
		{
			cItem = this.inventoryRef.getItem(i);
			
			cSlotElem = new UIElementInvSlot(this.inventoryRef, i, nextItemX, y, this.slotSize, this.slotSize);
			nextItemX += slotXDiff;
			
			if(cItem instanceof BagInventoryItem<?, ?>)
			{
				//Add additional slots for bag
				
				BagInventoryItem<?, ?> bag = (BagInventoryItem<?, ?>)cItem;
				Inventory<?> bagItems = bag.getBagItems();
				for(int j = 0; j < bagItems.getCapacity(); ++j)
				{
					UIElementInvSlot bagSlotElement = new UIElementInvSlot(bagItems, j, nextItemX, y + slotSizeBag * (j % 2), slotSizeBag, slotSizeBag);
					bagSlotElement.isInsideBag = true;
					this.addUIElement(bagSlotElement);
					nextItemX += slotXDiffBag * (j % 2);
				}
				nextItemX += slotXDiffBag * (bagItems.getCapacity() % 2);
			}
//			if(inv == this.myHero.getEquippedItems() && i == this.myHero.getSelectedEquipSlot())
//			{
//				currentHUDElement.setMarked(true);
//			}
			this.addUIElement(cSlotElem);
		}
	}
}
