package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;

public class ProjectileBossAction extends BossAction
{
	
	//NOTE: Die Positionen der Projectiles sollen Boss-Relative sein. Ist noch unsch�n und net eindeutig
	List<ProjectileSpawner> spawnerList;
	
	/**
	 * Creates a new action that spawns projectileSpawners
	 * @param spawnerList  List of projectile spawners that are supposed to spawn upon executing this action
	 */
	public ProjectileBossAction(List<ProjectileSpawner> spawnerList, int duration, int cooldown, Boss boss)
	{
		this.spawnerList = spawnerList;
		
		this.duration = duration;
		this.cooldown = cooldown;
		
		for(var i : spawnerList)
		{
			i.setPosition(new Vector2(boss.getPosition().x + i.getX(),
				boss.getY() + i.getY()));
		}
	}
	
	public ProjectileBossAction(List<ProjectileSpawner> spawnerList, int duration, int cooldown)
	{
		this.spawnerList = spawnerList;
		
		this.duration = duration;
		this.cooldown = cooldown;
		
		for(var i : spawnerList)
		{
			i.setPosition(new Vector2(i.getX(), i.getY()));
		}
		
	}
	
	@Override
	public void onActionBegin(BossBattle battle)
	{
		super.onActionBegin(battle);
		battle.getBoss().playAttackAnimation("shoot", false, 10);
		
		for(var i : spawnerList)
		{
			//            System.out.println("Spawn");
			i.despawnFlag = false;
			battle.level.spawnEntity(i);
		}
	}
	
	@Override
	public void execute()
	{
		super.execute();
	}
	
	@Override
	public void onActionEnd()
	{
		for(var i : spawnerList)
		{
			//            System.out.println("Unspawn");
			i.despawnFlag = true;
		}
	}
}
