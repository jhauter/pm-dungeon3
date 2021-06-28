package de.fhbielefeld.pmdungeon.quibble.memory;

import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class MemoryData {

	private Creature creature;

	private int levelCount = 1;

	private Preferences memory = Gdx.app.getPreferences("Quibble");

	/**
	 * Called to save PlayerData, Saved : <blockquote>
	 * <li>Class Type
	 * <li>Position in float
	 * <li>current Dungeonlevel
	 * <li>CreatureStat CurrentHealth
	 * <li>Total Exp
	 * <li>Inventar
	 * <li>Equipped
	 */
	public void save() {
		createMemoryfromData(DungeonStart.getDungeonMain().getPlayer());
		LoggingHandler.logger.log(Level.INFO, "Game has saved");
	}

	private void createMemoryfromData(Creature creature) {
		this.creature = creature;
		getMemory().putString(SaveType.CLASS.name(), creature.getClass().getTypeName());
		getMemory().putString(SaveType.NAME.name(), creature.getDisplayName());

		getMemory().putFloat(SaveType.POSITION_X.name(), creature.getPosition().x);
		getMemory().putFloat(SaveType.POSITION_Y.name(), creature.getPosition().y);
		saveDungeonLevel();

		getMemory().putInteger(SaveType.TOTAL_EXP.name(), creature.getTotalExp());
		
		saveCreatureStats();
		
		saveItems();
		
		getMemory().flush();
	}

	private void saveItems() {
		getMemory().putInteger(SaveType.EQUPPEMENT_SLOTS.name(), creature.getEquipmentSlots());
		getMemory().putInteger(SaveType.ITEM_SLOTS.name(), creature.getInventorySlots());
		saveItemInformations(creature.getEquipmentSlots(), creature.getEquippedItems(), SaveType.EQUIPPED_ITEM.name());
		saveItemInformations(creature.getInventorySlots(), creature.getInventory(), SaveType.INVENTORY_ITEM.name());
	}
	private void saveItemInformations(int size, Inventory<Item> inventory, String saveType) {
		for (int i = 0; i < size; i++) {
			if(inventory.getItem(i) != null) {
				getMemory().putString(saveType + i, inventory.getItem(i).getItemType().getClass().getTypeName());
				getMemory().putString(saveType + i + "displayName", inventory.getItem(i).getDisplayText());
				writeStats(inventory.getItem(i).getItemType().getAttackStats(), saveType + i);
			}
		}	
	}                             

	private void saveCreatureStats() {
		writeStats(this.creature.getCurrentStats(), SaveType.NAME.name());
	}

	private void writeStats(CreatureStats stats, String ID) {
		for (int i = 0; i < CreatureStatsAttribs.values().length; i++) {
			Float stat = (float) stats.getStat(CreatureStatsAttribs.values()[i]);
			getMemory().putFloat(ID + CreatureStatsAttribs.values()[i], stat);
		}
	}

	private void saveDungeonLevel() {
		if (this.levelCount < 4)
			this.levelCount += 1;
		getMemory().putInteger(SaveType.CURRENT_LEVEL.name(), levelCount);
	}
	
	public Preferences getMemory() {
		return memory;
	}
}
