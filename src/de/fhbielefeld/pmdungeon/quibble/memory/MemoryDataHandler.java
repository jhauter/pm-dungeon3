package de.fhbielefeld.pmdungeon.quibble.memory;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

import com.badlogic.gdx.Preferences;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;

public class MemoryDataHandler {

	private static MemoryDataHandler instance;

	MemoryData memoryData;

	private MemoryDataHandler() {
		memoryData = new MemoryData();
	}

	/**
	 * The MemoryDataHandler is an instance that should be able to save the player,
	 * its items and the current level.
	 * 
	 * @return instance of the MemoryDataHandler
	 */
	public static MemoryDataHandler getInstance() {
		if (instance == null)
			instance = new MemoryDataHandler();
		return instance;
	}

	/**
	 * Called to save the Player
	 */
	public void save() {
		memoryData.save();
	}

	private Preferences getMemory() {
		return memoryData.getMemory();
	}

	/**
	 * 
	 * @return the last instance of the Player. <br>
	 *         <blockquote> Stored data :
	 *         <li>Position
	 *         <li>Inventory
	 *         <li>Items
	 *         <li>Equipped
	 *         <li>Items
	 *         <li>Exp
	 *         <li>Life Points
	 */
	public Creature getSavedPlayer() {
		Player player = getSavedCreatureType();

		player.setPosition(getMemory().getFloat(SaveType.POSITION_X.name()),
				getMemory().getFloat(SaveType.POSITION_Y.name()));
		player.setName(getMemory().getString(SaveType.NAME.name()));

		player.setTotalExp(getMemory().getInteger(SaveType.TOTAL_EXP.name()));
		setPlayerAttrib((Player) player);

		loadEqu(getMemory().getInteger(SaveType.EQUPPEMENT_SLOTS.name()), SaveType.EQUIPPED_ITEM, player);
		loadInv(getMemory().getInteger(SaveType.INVENTORY_SLOTS.name()), SaveType.INVENTORY_ITEM, player);
		LoggingHandler.logger.log(Level.INFO, "Loaded Player from Memory");
		return player;
	}
	
	private void loadInv(int lenghtOfInventory, SaveType invType, Player player) {
		Item item = null;
		for (int i = 0; i < lenghtOfInventory; i++) {
			String memory = getMemory().getString(invType.name() + i);
			if(!memory.isBlank()) {
				item = getItem(memory);
				setItemAttrib(item, invType.name() + i);
				item.setDisplayName(getMemory().getString(invType.name() + i + "displayName"));
				player.getInventory().addItem(item);
			}
		}
	}
	private void loadEqu(int lenghtOfInventory, SaveType invType, Player player) {
		Item item = null;
		for (int i = 0; i < lenghtOfInventory; i++) {
			String memory = getMemory().getString(invType.name() + i);
			if(!memory.isBlank()) {
				item = getItem(memory);
				setItemAttrib(item, invType.name() + i);
				player.getEquippedItems().addItem(item);
			}
		}
	}


	private void addItems(int lenghtOfInventory, String InventoryType, Inventory<Item> inv) {
		Item item = null;
		for (int i = 0; i < lenghtOfInventory; i++) {
			String memory = getMemory().getString(InventoryType + i);
			if(!memory.isBlank()) {
				item = getItem(memory);
				setItemAttrib(item, InventoryType + i);
				item.setDisplayName(getMemory().getString(InventoryType + i + "displayName"));
				inv.addItem(item);
			}
		}
	}

	private Item getItem(String type) {
		for (Item item : Item.getRegisteredItems()) {
			if (type.equals(item.getClass().getTypeName()))
				return item;
		}
		return null;
	}

	private void setItemAttrib(Item item, String saveType) {
		CreatureStats stats = new CreatureStats();
		for (int i = 0; i < CreatureStatsAttribs.values().length; i++) {
			stats.setStat(CreatureStatsAttribs.values()[i], getMemory().getFloat(saveType + i));
		}
		item.setAttackStats(stats);
	}

	private void setPlayerAttrib(Player player) {
		for (int i = 0; i < CreatureStatsAttribs.values().length; i++) {
			player.getCurrentStats().setStat(CreatureStatsAttribs.values()[i],
					getMemory().getFloat(SaveType.NAME.name() + CreatureStatsAttribs.values()[i]));
		}
	}

	/**
	 * 
	 * @return the last DungeonWorld which was loaded. <br>
	 *         Use this to get the last Map the Player has entered.
	 */
	public String getSavedLevel() {
		String logMsg = "Loaded Dungeon from Memory";
		int x = getMemory().getInteger(SaveType.CURRENT_LEVEL.name());
		switch (x) {
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

	/**
	 * Recreate the right instance of the Creature. <br>
	 * For Example Hero or Mage.
	 * 
	 * @return The saved Creature.
	 */
	private Player getSavedCreatureType() {
		Player creature = null;
		String type = getMemory().getString(SaveType.CLASS.name());
		SimpleClassReflection sc = new SimpleClassReflection(type, false);
		try {
			creature = (Player) sc.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return creature;
	}

}
