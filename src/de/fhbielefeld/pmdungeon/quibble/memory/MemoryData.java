package de.fhbielefeld.pmdungeon.quibble.memory;

import java.util.ArrayList;
import java.util.logging.Level;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;

public class MemoryData
{
	
	public String displayName;
	public String classType;
	public Vector2 position;
	public int invSlots;
	public int equSlots;
	public ArrayList<ItemInformation> inv;
	public ArrayList<ItemInformation> equ;
	public ArrayList<Double> stats;
	public String level;
	
	public MemoryData()
	{
	}
	
	/**
	 * 
	 * @param player to be saved
	 * <blockquote> Things that will be saved
	 * <li> displayname
	 * <li> classtype (to get the right hero)
	 * <li> position
	 * <li> inventar
	 * <li> equippedItems
	 * <li> stats
	 */
	public MemoryData(Player player, int counter)
	{
		if(player != null)
		{
			this.displayName = player.getDisplayName();
			this.classType = player.getClass().getTypeName();
			this.position = player.getPosition();
			this.inv = getItems(player.getInventory());
			this.invSlots = player.getInventorySlots();
			this.equSlots = player.getEquipmentSlots();
			this.equ = getItems(player.getEquippedItems());
			this.stats = getStats(player);
			this.level = saveLevel(counter);
		}
	}
	
	/**
	 * 
	 * @return the last DungeonWorld which was loaded. <br>
	 *         Use this to get the last Map the Player has entered.
	 */
	public String saveLevel(int counter)
	{
		String logMsg = "Loaded Dungeon from Memory";
		switch(counter)
		{
			case 1:
				LoggingHandler.logger.log(Level.INFO, logMsg);
				return Constants.PATHTOLEVEL + "small_dungeon.json";
			case 2:
				LoggingHandler.logger.log(Level.INFO, logMsg);
				return Constants.PATHTOLEVEL + "simple_dungeon_2.json";
			case 3:
				LoggingHandler.logger.log(Level.INFO, logMsg);
				return Constants.PATHTOLEVEL + "simple_dungeon.json";
			case 4:
				LoggingHandler.logger.log(Level.INFO, logMsg);
				return Constants.PATHTOLEVEL + "boss_dungeon.json";
		}
		
		return null;
	}
	
	private ArrayList<Double> getStats(Player player)
	{
		ArrayList<Double> stats = new ArrayList<>();
		for(int i = 0; i < CreatureStatsAttribs.values().length; i++)
		{
			stats.add(player.getCurrentStats().getStat(CreatureStatsAttribs.values()[i]));
		}
		return stats;
	}
	
	private ArrayList<ItemInformation> getItems(Inventory<Item> inv)
	{
		ArrayList<ItemInformation> items = new ArrayList<>();
		ItemInformation info = null;
		for(int i = 0; i < inv.getCapacity(); i++)
		{
			if(inv.getItem(i) != null)
			{
				info = new ItemInformation(inv.getItem(i).getItemType());
				items.add(info);
			}
		}
		return items;
	}
	
	class ItemInformation
	{
		String name;
		String classType;
		CreatureStats stats;
		
		/**
		 * Simple class to store nessecary informations of Items
		 * @param item the item that should be recognized again
		 */
		public ItemInformation(Item item)
		{
			this.name = item.getDisplayName();
			this.classType = item.getClass().getTypeName();
			this.stats = item.getAttackStats();
		}
	}
	
}
