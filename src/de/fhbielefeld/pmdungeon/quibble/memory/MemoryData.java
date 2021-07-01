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
		getMemory().putInteger(SaveType.INVENTORY_SLOTS.name(), creature.getInventorySlots());
		saveItemInformations(creature.getInventorySlots(), SaveType.INVENTORY_ITEM);
		saveItemInformations(creature.getEquipmentSlots(), SaveType.EQUIPPED_ITEM);
	}

	private void saveItemInformations(int slotLenght, SaveType invType) {
		for (int i = 0; i < slotLenght; i++) {
			if (creature.getInventory().getItem(i) != null) {
				getMemory().putString(invType.name() + i,
						creature.getInventory().getItem(i).getItemType().getClass().getTypeName());
				getMemory().putString(invType.name() + i + "displayName", creature.getInventory().getItem(i).getDisplayText());
				writeStats(creature.getInventory().getItem(i).getItemType().getAttackStats(), invType.name() + i);
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
