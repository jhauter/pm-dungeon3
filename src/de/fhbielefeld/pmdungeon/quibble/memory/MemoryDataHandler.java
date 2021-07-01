package de.fhbielefeld.pmdungeon.quibble.memory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.memory.MemoryData.ItemInformation;

public class MemoryDataHandler
{
	
	private static MemoryDataHandler instance;
	
	private Gson gson;
	
	private MemoryData memoryData;
	
	private int counter = 0;
	
	private MemoryDataHandler()
	{
		this.gson = new Gson();
		
	}
	
	/**
	 * The MemoryDataHandler is an instance that should be able to save the player,
	 * its items and the current level.
	 * 
	 * @return instance of the MemoryDataHandler
	 */
	public static MemoryDataHandler getInstance()
	{
		if(instance == null)
			instance = new MemoryDataHandler();
		return instance;
	}
	
	/**
	 * Will save the player as MemoryData to a JsonFile
	 */
	public void savePlayer()
	{
		this.memoryData = new MemoryData(DungeonStart.getDungeonMain().getPlayer(), counter += 1);
		String memory = gson.toJson(memoryData);
		FileHandle file = Gdx.files.local("Game.json");
		file.writeString(memory, false);
		
		LoggingHandler.logger.log(Level.INFO, "Save Player to Memory");
	}
	
	/**
	 * 
	 * @return the last saved instance of the Player
	 */
	public Player loadPlayer()
	{
		Gson gson = new Gson();
		FileHandle file = Gdx.files.local("Game.json");
		String data = file.readString();
		this.memoryData = gson.fromJson(data, MemoryData.class);
		Player player = (Player)getSavedClass(memoryData.classType);
		player.setPosition(memoryData.position);
		player.setName(memoryData.displayName);
		for(int i = 0; i < CreatureStatsAttribs.values().length; i++)
		{
			player.getCurrentStats().setStat(CreatureStatsAttribs.values()[i], memoryData.stats.get(i));
		}
		getSavedItems(player.getEquippedItems(), memoryData.equ);
		getSavedItems(player.getInventory(), memoryData.inv);
		LoggingHandler.logger.log(Level.INFO, "Loaded Player Data");
		return player;
	}
	
	private void getSavedItems(Inventory<Item> inv, ArrayList<ItemInformation> list)
	{
		Item item = null;
		for(int i = 0; i < list.size(); i++)
		{
			if(list.get(i) != null)
			{
				item = getItem(list.get(i).classType);
				item.setDisplayName(list.get(i).name);
				item.setAttackStats(list.get(i).stats);
				inv.addItem(item);
			}
		}
	}
	
	private Item getItem(String type)
	{
		for(Item item : Item.getRegisteredItems())
		{
			if(type.equals(item.getClass().getTypeName()))
				return item;
		}
		return null;
	}
	
	/**
	 * Recreate the right instance of the Creature. <br>
	 * For Example Hero or Mage.
	 * 
	 * @return The saved Creature.
	 */
	private Object getSavedClass(String type)
	{
		Object object = null;
		SimpleClassReflection sc = new SimpleClassReflection(type, true);
		try
		{
			object = (Object)sc.getConstructor().newInstance();
		}
		catch(InstantiationException | IllegalAccessException | IllegalArgumentException
			| InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return object;
	}
	
	public String getSavedLevel()
	{
		return this.memoryData.level;
	}
	
}
