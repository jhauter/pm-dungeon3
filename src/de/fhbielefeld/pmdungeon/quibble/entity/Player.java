package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.List;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.chest.Chest;
import de.fhbielefeld.pmdungeon.quibble.input.DungeonInput;
import de.fhbielefeld.pmdungeon.quibble.input.InputListener;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public abstract class Player extends Creature implements InputListener
{
	private boolean triggeredNextLevel;
	
	private float controlMinX;
	private float controlMaxX;
	private float controlMinY;
	private float controlMaxY;
	
	/**
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public Player(float x, float y)
	{
		super(x, y);
	}
	
	/**
	 * Creates a player entity with a default position
	 */
	public Player()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public void onInputRecieved(DungeonInput input)
	{
		//This logic is to make a player stand still if keys of opposite directions are pressed
		
		this.controlMinX = Math.min(this.controlMinX, input.getAxisScaleX());
		this.controlMaxX = Math.max(this.controlMaxX, input.getAxisScaleX());
		this.controlMinY = Math.min(this.controlMinY, input.getAxisScaleY());
		this.controlMaxY = Math.max(this.controlMaxY, input.getAxisScaleY());
		
		if(input == DungeonInput.HIT)
		{
			this.attackAoE();
			LoggingHandler.logger.log(Level.FINE, "Mouse input: attack");
		}
		else if(input == DungeonInput.INV_LOG)
		{
			LoggingHandler.logger.log(Level.INFO, "Inventory: " + Inventory.inventoryString(getInventory()));
//			Inventory.inventoryVisitor(this.getInventory(), new ItemInvLogVisitor());
		}
		else if(input == DungeonInput.EQUIP_LOG)
		{
			LoggingHandler.logger.log(Level.INFO, "Equipment: " + Inventory.inventoryString(getEquippedItems()));
//			Inventory.inventoryVisitor(this.getEquippedItems(), new ItemEquipLogVisitor());
		}
	}
	
	/**
	 * This must be called to reset the <code>triggeredNextLevel</code> flag
	 * directly after the next level has been loaded.
	 */
	public void onNextLevelEntered()
	{
		//IMPORTANT never forget to set this or else all levels will be loaded so
		//quickly that you're at the end level immediately.
		this.triggeredNextLevel = false;
	}
	
	/**
	 * Whether the <code>triggeredNextLevel</code> flag is set.
	 * This is set when the player steps on the ladder that leads to the next level
	 * and will cause the next level to load.
	 * @return the <code>triggeredNextLevel</code> flag
	 */
	public boolean triggeredNextLevel()
	{
		return this.triggeredNextLevel;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		if(this.level.getDungeon().getNextLevelTrigger() == this.level.getDungeon().getTileAt((int)this.getPosition().x, (int)this.getPosition().y))
		{
			this.triggeredNextLevel = true;
		}
		
		float dirX = (this.controlMaxX + this.controlMinX) * 0.5F;
		float dirY = (this.controlMaxY + this.controlMinY) * 0.5F;
		
		if(dirX != 0.0D || dirY != 0.0D)
		{
			float angle = (float)Math.toDegrees(Math.atan2(dirY, dirX));
			
			this.walk(angle);
			if(angle > 90 || angle < -90)
			{
				this.setLookingDirection(LookingDirection.LEFT);
			}
			else if(angle < 90 && angle > -90)
			{
				this.setLookingDirection(LookingDirection.RIGHT);
			}
			//if it is exactly 90 or -90 do nothing
			//So that when you press up or down, the looking direction does not change
			
			LoggingHandler.logger.log(Level.FINE, "Movement input: " + angle + "deg");
		}
		
		for(int i = 0; i < 9; ++i)
		{
			if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1 + i))
			{
				if(Gdx.input.isKeyPressed(Input.Keys.Q) && i < this.getInventorySlots())
				{
					InventoryItem<Item> droppedItem = this.getInventory().getItem(i);
					if(this.drop(i) && droppedItem != null)
					{
						LoggingHandler.logger.log(Level.INFO, "Dropped item: " + droppedItem.getDisplayText());
					}
				}
				else if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) && i < this.getInventorySlots())
				{
					InventoryItem<Item> equippedItem = this.getInventory().getItem(i);
					if(this.equip(i) && equippedItem != null)
					{
						LoggingHandler.logger.log(Level.INFO, "Put item from inventory into equipment: " + equippedItem.getDisplayText());
					}
				}
				else if(Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && i < this.getEquipmentSlots())
				{
					InventoryItem<Item> unequippedItem = this.getEquippedItems().getItem(i);
					if(this.unequip(i) && unequippedItem != null)
					{
						LoggingHandler.logger.log(Level.INFO, "Put item from eqipment into inventory: " + unequippedItem.getDisplayText());
					}
				}
				else if(i < this.getEquipmentSlots())
				{
					this.useEquippedItem(i);
					LoggingHandler.logger.log(Level.INFO, "Attempted to use item in eqip slot " + (i + 1));
				}
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			List<Entity> l = this.getLevel().getEntitiesInRadius(getX(), getY(), 1);
			for (int i = 0; i < l.size(); i++) {
				if(l.get(i) instanceof Chest) {
					l.get(i).animationHandler.playAnimation("Open_Gold", 4, false  );
					((Chest) l.get(i)).setOpen();
					((Chest) l.get(i)).animationHandler.playAnimation("Open", Integer.MAX_VALUE, true);
			
					LoggingHandler.logger.log(Level.INFO, Inventory.inventoryString(((Chest)l.get(i)).getInv()));
					break;
				}
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateEnd()
	{
		super.updateEnd();
		
		//Clear the controls of this frame
		this.controlMinX = 0.0F;
		this.controlMaxX = 0.0F;
		this.controlMinY = 0.0F;
		this.controlMaxY = 0.0F;
	}
}