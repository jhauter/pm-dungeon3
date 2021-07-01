package de.fhbielefeld.pmdungeon.quibble.level;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.entity.Chort;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Demon;
import de.fhbielefeld.pmdungeon.quibble.entity.Goblin;
import de.fhbielefeld.pmdungeon.quibble.entity.Lizard;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class DungeonFloorLoader {
	
	private int dungeonLevelCounter = 1;
	private int demonRequirement = 3;
	private int creatureExp = 10;
	
	
	public void placeEnemies(DungeonLevel level) {
		for(int i = 0; i < 5; ++i)
		{
			final Point pos = level.getDungeon().getRandomPointInDungeon();
			final Creature toSpawn = switch(level.getRNG().nextInt(4))
			{
				case 0 -> new Demon();
				case 1 -> new Goblin();
				case 2 -> new Lizard();
				case 3 -> new Chort();
				default -> throw new IllegalArgumentException("Unexpected value [spawn entity]");
			};
			if (this.dungeonLevelCounter >= 2) {
				toSpawn.rewardExp(creatureExp);
				this.creatureExp = toSpawn.totalExpFunction(dungeonLevelCounter);
				
			}
			else {
				
			}
			
			toSpawn.setPosition(pos.x, pos.y);
			toSpawn.heal(toSpawn.getMaxHealth()-toSpawn.getCurrentHealth());
			level.spawnEntity(toSpawn);
			
		}
		
	}
	
	public void updateDungeonLevel() {
		
		this.dungeonLevelCounter++;
	}
}
