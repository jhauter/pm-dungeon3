package de.fhbielefeld.pmdungeon.quibble.level;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.entity.Chort;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Demon;
import de.fhbielefeld.pmdungeon.quibble.entity.Goblin;
import de.fhbielefeld.pmdungeon.quibble.entity.Lizard;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

/**
 * Spawns random monster in the Dungeon. Their strength depends on the current level the hero is in.
 * If the hero reaches a certain level, stronger monster start to spawn in greater numbers.
 * 
 * @author Michael Herber
 *
 */

public class EnemySpawner
{
	// current level the hero is in
	private int dungeonLevelCounter = 1;
	//experience monster get if the hero progresses in the dungeon
	private int creatureExp = 10;
	
	/**
	 * places Enemies at random locations in the dungeon.
	 * stronger monster start spawning if hero reaches floor 3.
	 * 
	 * @param level the dungeon of the game
	 */
	public void placeEnemies(DungeonLevel level)
	{
		//spawns 5 monster at random locations
		for(int i = 0; i < 5; ++i)
		{
			final Point pos = level.getDungeon().getRandomPointInDungeon();
			Creature toSpawn = switch(level.getRNG().nextInt(4))
			{
				case 0 -> new Demon();
				case 1 -> new Goblin();
				case 2 -> new Lizard();
				case 3 -> new Chort();
				default -> throw new IllegalArgumentException("Unexpected value [spawn entity]");
			};
			
			//makes monster stronger by rewarding them experience starting at floor 2.
			if(this.dungeonLevelCounter >= 2)
			{
				toSpawn.rewardExp(creatureExp);
				this.creatureExp = toSpawn.totalExpFunction(dungeonLevelCounter);
				
			}
			
			toSpawn.setPosition(pos.x, pos.y);
			toSpawn.heal(toSpawn.getMaxHealth() - toSpawn.getCurrentHealth());
			level.spawnEntity(toSpawn);
			
		}
		// stronger monster spawn starting at floor 3.
		if(dungeonLevelCounter >= 3)
		{
			//how many spawn depends on the floor the hero is in
			for(int k = 0; k < dungeonLevelCounter; ++k)
			{
				
				final Point pos2 = level.getDungeon().getRandomPointInDungeon();
				final Creature toSpawn2 = new Lizard();
				toSpawn2.rewardExp(creatureExp);
				this.creatureExp = toSpawn2.totalExpFunction(dungeonLevelCounter);
				toSpawn2.setPosition(pos2.x, pos2.y);
				toSpawn2.heal(toSpawn2.getMaxHealth() - toSpawn2.getCurrentHealth());
				level.spawnEntity(toSpawn2);
			}
		}
		
	}
	
	/**
	 * Should be called when the hero steps on a ladder to update the current dungeon level.
	 */
	public void updateDungeonLevel()
	{
		
		this.dungeonLevelCounter++;
	}
}
