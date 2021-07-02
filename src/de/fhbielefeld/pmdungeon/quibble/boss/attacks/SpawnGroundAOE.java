package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.entity.Chort;
import de.fhbielefeld.pmdungeon.quibble.entity.Goblin;
import de.fhbielefeld.pmdungeon.quibble.entity.Lizard;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;

/**
 * Groundaoe that spawns either random or specific entities
 */
public class SpawnGroundAOE extends GroundAoe
{
	private DungeonLevel level;
	
	private int spawnCD = 5;
	private int spawnCDCounter = 0;
	
	private boolean activated = false;
	private Random rand = new Random();
	public boolean useSpecificTarget = false;
	
	public SpawnGroundAOE()
	{
		this.radius = 1;
		animationHandler.addAsDefaultAnimation("spawn", 11, 0, 0.1f, 11, 11,
			"assets/textures/entity/boss_general/effect_spawn.png");
		this.renderScaleX = 3;
		this.renderScaleY = 3;
		this.ticksUntilAction = 30;
	}
	
	@Override
	protected void onRoam()
	{
		spawnCDCounter++;
		if(spawnCDCounter >= spawnCD && !activated)
		{
			activated = true;
			spawnCDCounter = 0;
		}
	}
	
	@Override
	protected void onTrigger()
	{
		//        System.out.println("Spawning enemies");
		if(target == null)
		{
			var r = rand.nextInt(2);
			NPC enemy = null;
			switch(r)
			{
				case 0 -> enemy = new Goblin();
				case 1 -> enemy = new Chort();
				case 2 -> enemy = new Lizard();
			}
			//            System.out.println("Enemy Spawn");
			level.spawnEntity(enemy);
			enemy.setPosition(this.getPosition());
		}
		else
		{
			try
			{
				var enemy = target.getClass().getDeclaredConstructor(null).newInstance();
				
				//                System.out.println("Specific");
				//                System.out.println(enemy.getClass().getName());
				level.spawnEntity(enemy);
				enemy.setPosition(this.getPosition());
			}
			catch(InstantiationException e)
			{
				e.printStackTrace();
			}
			catch(IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch(InvocationTargetException e)
			{
				e.printStackTrace();
			}
			catch(NoSuchMethodException e)
			{
				e.printStackTrace();
			}
		}
		
		spawnCDCounter = 0;
		activated = false;
	}
	
	@Override
	public void onSpawn(DungeonLevel level)
	{
		this.level = level;
		super.onSpawn(level);
	}
}
