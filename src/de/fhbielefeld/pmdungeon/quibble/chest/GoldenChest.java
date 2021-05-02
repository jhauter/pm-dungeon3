package de.fhbielefeld.pmdungeon.quibble.chest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.fhbielefeld.pmdungeon.quibble.inventory.DefaultInventory;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.item.ItemHealingPotion;

public class GoldenChest extends Chest {

	private final Random r = new Random();
	private final String CHEST_EMPTY_OPEN = "chest_empty_open_anim_";
	private final String CHEST_FULL_OPEN = "chest_full_open_anim_";
	private final String CHEST_MIMIC_OPEN = "chest_mimic_open_anim_";
	private Inventory<Item> inv;
	Item ItemHealingBig = new ItemHealingPotion("Healing Potion", 10, "pot_red_big");
	Item ItemHealingSmall = new ItemHealingPotion("Healing Potion", 5, "pot_red_Small");
//	Item ItemBlueSword = "BlueSword";
	
	List<Item> ItemList = Arrays.asList(ItemHealingBig, ItemHealingSmall);
	HashMap<String, Item> ItemMap = new HashMap<>();

	GoldenChest(){
		int random = r.nextInt(ItemList.size());
		Item i = ItemList.get(random);
		Inventory<Item> inv = new DefaultInventory<>(r.nextInt(2));
		inv.addItem(i);
	
	}
}
