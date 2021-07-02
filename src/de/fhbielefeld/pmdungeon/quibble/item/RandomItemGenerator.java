package de.fhbielefeld.pmdungeon.quibble.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;

/**
 * Generates a random item with a name and stats randomly assigned but also based upon the current level the hero is in.
 * So in the beginning the items will be rather weak and have corresponding names. In the end stages of the game the
 * items will be strong and have impressively sounding names.
 *
 * This class is built as a singleton.
 *
 * @author Adrian Koß
 */
public class RandomItemGenerator
{
	private static RandomItemGenerator instance;
	
	private final Random random = new Random();
	
	private final ArrayList<String> itemAdjectivesWeak = new ArrayList<>(Arrays.asList("Weak", "Fragile", "Useless", "Tiny"));
	private final ArrayList<String> itemAdjectivesStandard = new ArrayList<>(Arrays.asList("Normal", "Average", "Standard"));
	private final ArrayList<String> itemAdjectivesStrong = new ArrayList<>(Arrays.asList("Powerful", "Enchanted", "Legendary", "Dangerous"));
	
	/**
	 * Private constructor as required in a singleton
	 */
	private RandomItemGenerator()
	{
	}
	
	/**
	 * The method to call first when working with this class. Gives access to the instance of this class and therefore
	 * all the methods for it.
	 * @return the single instance of this class
	 */
	public static RandomItemGenerator getInstance()
	{
		if(instance == null)
		{
			instance = new RandomItemGenerator();
		}
		return instance;
	}
	
	/**
	 * Generates a random item. Could be a melee weapon, a ranged weapon or a potion.
	 * @param levelCount the current level number the hero is in. needed to determine the strength of the item
	 * @return the randomly generated item
	 */
	public Item generateRandomItem(int levelCount)
	{
		int randomInt = random.nextInt(3);
		
		if(randomInt == 0)
		{
			return generateMeleeWeapon(levelCount);
		}
		else if(randomInt == 1)
		{
			return generateRangedWeapon(levelCount);
		}
		else
		{
			return generatePotion(levelCount);
		}
	}
	
	/**
	 * Generates a random melee weapon.
	 * @param levelCount the current level number the hero is in. needed to determine the strength of the item
	 * @return the randomly generated item
	 */
	public Item generateMeleeWeapon(int levelCount)
	{
		int rng = random.nextInt(2);
		
		String itemName = this.generateItemAdjective(levelCount);
		
		if(rng == 0)
		{
			itemName += " Katana";
			Item katana = new ItemKatana(itemName);
			
			CreatureStats stats = katana.getAttackStats();
			stats.multiplyAllStats(this.getModifyingValue(levelCount));
			katana.setAttackStats(stats);
			
			return katana;
		}
		else
		{
			itemName += " Blue Sword";
			Item sword = new ItemSwordBlue(itemName);
			
			CreatureStats stats = sword.getAttackStats();
			stats.multiplyAllStats(this.getModifyingValue(levelCount));
			
			return sword;
		}
	}
	
	/**
	 * Generates a random ranged weapon.
	 * @param levelCount the current level number the hero is in. needed to determine the strength of the item
	 * @return the randomly generated item
	 */
	public Item generateRangedWeapon(int levelCount)
	{
		int rng = random.nextInt(3);
		
		String itemName = this.generateItemAdjective(levelCount);
		
		if(rng == 0)
		{
			itemName += " Blue Staff";
			Item blueStaff = new ItemBlueMagicStaff(itemName);
			
			CreatureStats stats = blueStaff.getAttackStats();
			stats.multiplyAllStats(this.getModifyingValue(levelCount));
			
			return blueStaff;
		}
		else if(rng == 1)
		{
			itemName += " Red Staff";
			Item redStaff = new ItemRedMagicStaff(itemName);
			
			CreatureStats stats = redStaff.getAttackStats();
			stats.multiplyAllStats(this.getModifyingValue(levelCount));
			
			return redStaff;
		}
		else
		{
			itemName += " Bow";
			Item bow = new ItemWeaponSimpleBow(itemName);
			
			CreatureStats stats = bow.getAttackStats();
			stats.multiplyAllStats(this.getModifyingValue(levelCount));
			
			return bow;
		}
	}
	
	/**
	 * Generates a random potion.
	 * @param levelCount the current level number the hero is in. needed to determine the strength of the item
	 * @return the randomly generated item
	 */
	public Item generatePotion(int levelCount)
	{
		int rng = random.nextInt(5);
		
		String itemName = this.generateItemAdjective(levelCount);
		
		if(rng == 0)
		{
			itemName += " Small Healing Potion";
			
			double defaultValue = 2.0;
			double value = modifyDefaultValue(defaultValue, levelCount);
			
			return new ItemHealingPotion(itemName, value, "pot_red_small");
		}
		else if(rng == 1)
		{
			itemName += " Big Healing Potion";
			
			double defaultValue = 5.0;
			double value = modifyDefaultValue(defaultValue, levelCount);
			
			return new ItemHealingPotion(itemName, value, "pot_red_big");
		}
		else if(rng == 2)
		{
			itemName += " Big Sight Potion";
			
			double defaultValue = 1800;
			int value = (int)Math.round(modifyDefaultValue(defaultValue, levelCount));
			
			return new ItemSightPotion(itemName, value, "pot_green_big");
		}
		else if(rng == 3)
		{
			itemName += " Small Speed Potion";
			
			double defaultValue = 200;
			int value = (int)Math.round(modifyDefaultValue(defaultValue, levelCount));
			
			return new ItemSpeedPotion(itemName, 1.3, value, "pot_yellow_small");
		}
		else
		{
			itemName += " Big Speed Potion";
			
			double defaultValue = 300;
			int value = (int)Math.round(modifyDefaultValue(defaultValue, levelCount));
			
			return new ItemSpeedPotion(itemName, 1.4, value, "pot_yellow_big");
		}
	}
	
	/*
	Generates the adjective part of the item name.
	 */
	private String generateItemAdjective(int levelCount)
	{
		String itemAdjective = "";
		if(levelCount < 3)
		{
			itemAdjective += this.itemAdjectivesWeak.get(random.nextInt(itemAdjectivesWeak.size()));
		}
		else if(levelCount < 5)
		{
			itemAdjective += this.itemAdjectivesStandard.get(random.nextInt(itemAdjectivesStandard.size()));
		}
		else
		{
			itemAdjective += this.itemAdjectivesStrong.get(random.nextInt(itemAdjectivesStrong.size()));
		}
		return itemAdjective;
	}
	
	private double modifyDefaultValue(double defaultValue, int levelCount)
	{
		return this.getModifyingValue(levelCount) * defaultValue;
	}
	
	private double getModifyingValue(int levelCount)
	{
		return 1.0 / 60.0 * Math.pow(levelCount, 2) + 11.0 / 60.0 * levelCount + 3.0 / 10.0;
	}
}
