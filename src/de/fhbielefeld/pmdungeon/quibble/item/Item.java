package de.fhbielefeld.pmdungeon.quibble.item;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.inventory.DefaultInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.InventoryItem;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;

public abstract class Item
{
	private static List<Item> registeredItems = new ArrayList<Item>();
	
	/**
	 * @return the amount of items in the game
	 */
	public static int getNumItems()
	{
		return registeredItems.size();
	}
	
	/**
	 * @param index the index of the item
	 * @return returns the item at the specified index
	 */
	public static Item getItem(int index)
	{
		return registeredItems.get(index);
	}
	
	public static final String ITEMS_TEXTURE_PATH = "assets/textures/items/";
	
	public static final ItemHealingPotion POTION_RED_BIG = new ItemHealingPotion("Healing Potion", 5.0D, "pot_red_big");
	public static final ItemHealingPotion POTION_RED_SMALL = new ItemHealingPotion("Small Healing Potion", 2.0D, "pot_red_small");
	public static final ItemSpeedPotion POTION_YELLOW_BIG = new ItemSpeedPotion("Big Speed Potion", 1.4, 300, "pot_yellow_big");
	public static final ItemSpeedPotion POTION_YELLOW_Small = new ItemSpeedPotion("Small Speed Potion", 1.3, 200, "pot_yellow_small");
	public static final ItemSightPotion POTION_SIGHT_BIG = new ItemSightPotion("Big Sight Potion", 1800, "pot_green_big");
	public static final ItemWeaponMelee SWORD_BLUE = new ItemSwordBlue();
	public static final ItemWeaponMelee SWORD_KATANA = new ItemKatana();
	public static final ItemWeaponRanged RED_MAGIC_STAFF = new ItemRedMagicStaff();
	public static final ItemWeaponRanged BLUE_MAGIC_STAFF = new ItemBlueMagicStaff();
	public static final ItemWeaponRanged SIMPLE_BOW = new ItemWeaponSimpleBow();
	public static final ItemBag<Item> BAG_DEFAULT = new ItemBag<Item>("Bag", 3, "bag");
	public static final ItemFogSightPotion POTION_FOG_SIGHT = new ItemFogSightPotion("Fog Sight Potion", 0.5, 1800, "pot_fog_sight");
	
	private String displayName;
	
	private final String textureFile;
	
	private final int visibleTicks;

	private CreatureStats attackStats = new CreatureStats();
	
	//--------- Render Settings ------------
	protected float renderWidth = 1.0F;
	protected float renderHeight = 1.0F;
	protected float renderPivotX = 0.5F;
	protected float renderPivotY = 0.0F;
	protected float renderOffsetX = 0.0F;
	protected float renderOffsetY = 0.5F;
	
	/**
	 * Sets whether the rendered item can have a negative scale if the holding entity
	 * has a negative scale.
	 * If this if <code>false</code> then this item will only rendered with
	 * the positive entity scale.
	 */
	protected boolean renderAllowNegativeEntityScale = true;
	//These default settings make the rotation point the bottom center of the texture
	//--------------------------------------
	
	protected float holdOffsetX = 0.0F;
	protected float holdOffsetY = 0.0F;
	
	/**
	 * Animation to use when the item is used by a creature.
	 * <code>null</code> means not animated; use the normal icon.
	 */
	private final Animation<TextureRegion> animWhenUsing;
	
	/**
	 * Creates an item with the specified display name
	 * @param name the display name of the item
	 * @param visibleTicks the number of ticks that the item is displayed at the creature
	 * @param texture the texture that is used to render the item. This is the
	 * complete relative path to the texture including the file extension.
	 */
	protected Item(String name, int visibleTicks, String texture)
	{
		this.displayName = name;
		this.animWhenUsing = this.loadAnimation();
		this.textureFile = texture;
		this.visibleTicks = visibleTicks;
		Item.registeredItems.add(this);
	}
	
	/**
	 * This is called when a creature uses this item.
	 * @param user the creature that used the item
	 * @param targetX the x-position in the dungeon that the user targeted (ex. via mouse click)
	 * @param targetY the y-position in the dungeon that the user targeted (ex. via mouse click)
	 * @return whether using the item was successful (item will only be shown on the creature
	 * after a successful use)
	 */
	public abstract boolean onUse(Creature user, float targetX, float targetY);
	
	/**
	 * Items that can be consumed will disappear from the inventory after use.
	 * @return whether this item is consumable
	 */
	public abstract boolean canBeConsumed();
	
	/**
	 * These stats will be added to a creature's base stats if the creature has this item equipped.
	 * @return stats of this item
	 */
	public CreatureStats getItemStats()
	{
		//Everything is initialized to 0.0
		return new CreatureStats();
	}
	
	/**
	 * These stats will be used to damage an entity when hit with this item if this item is a weapon.
	 * Projectiles will also get the stats that this method returns.
	 * @return attack stats of this item
	 */
	public CreatureStats getAttackStats()
	{
		return attackStats;
	}

	/**
	 * These stats will be used to damage an entity when hit with this item if this item is a weapon.
	 * Projectiles will also get the stats that this method gets.
	 * @param stats the stats for the item
	 */
	public void setAttackStats(CreatureStats stats){
		this.attackStats = stats;
	}
	
	/**
	 * @return user friendly display name
	 */
	public String getDisplayName()
	{
		return this.displayName;
	}
	
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}
	
	/**
	 * @return the complete relative file path to the texture of this item
	 */
	public String getTextureFile()
	{
		return this.textureFile;
	}
	
	/**
	 * This method loads an animation if one should be used.
	 * If this method returns <code>null</code> then no animation is used.
	 * The animation is used when an item is used and rendered in the world.
	 * In order to load an animation, override this method.
	 * @return the animation to use when the item is shown in the level
	 */
	public Animation<TextureRegion> loadAnimation()
	{
		return null;
	}
	
	/**
	 * This method returns the animation that is played on the item when
	 * a creature uses it and it is rendered in the world.
	 * If this returns <code>null</code> then no animation is used and the normal
	 * item icon is shown instead.
	 * @return the animation that is played when the item is shown in the level
	 */
	public final Animation<TextureRegion> getAnimation()
	{
		return this.animWhenUsing;
	}
	
	/**
	 * @return the number of ticks that the item is displayed at the creature
	 */
	public int getVisibleTicks()
	{
		return this.visibleTicks;
	}
	
	/**
	 * This generates an <code>InventoryItem</code> for use with inventories.
	 * @return a new <code>InventoryItem</code> instance that represents this item
	 */
	public InventoryItem<? extends Item> createInventoryItem()
	{
		return new DefaultInventoryItem<Item>(this);
	}
	
	public ParticleMovement getOnUseMovement(Creature user, float targetX, float targetY)
	{
		//Default item render animation
		return new Swing((int)user.getLookingDirection().getAxisX(), 5F);
	}

	public float getRenderWidth()
	{
		return renderWidth;
	}

	public float getRenderHeight()
	{
		return renderHeight;
	}

	public float getRenderPivotX()
	{
		return renderPivotX;
	}

	public float getRenderPivotY()
	{
		return renderPivotY;
	}

	public float getRenderOffsetX()
	{
		return renderOffsetX;
	}

	public float getRenderOffsetY()
	{
		return renderOffsetY;
	}
	
	public float getHoldOffsetX()
	{
		return holdOffsetX;
	}
	
	public float getHoldOffsetY()
	{
		return holdOffsetY;
	}
	
	/**
	 * @return whether this item will be rendered with negative scale values if the creature using it
	 * is rendered with negative scale values (ex: looking to the left)
	 */
	public boolean getRenderAllowNegativeEntityScale()
	{
		return this.renderAllowNegativeEntityScale;
	}
	
	public static List<Item> getRegisteredItems() {
		return registeredItems;
	}
}
