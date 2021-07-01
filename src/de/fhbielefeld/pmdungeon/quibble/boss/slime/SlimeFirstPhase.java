package de.fhbielefeld.pmdungeon.quibble.boss.slime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.boss.attacks.PuddleFireAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossPhase;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.GroundEffectBossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.ProjectileBossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.WaitAction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

public class SlimeFirstPhase extends BossPhase
{
	private final ArrayList<BossAction> actions = new ArrayList<>();
	
	public SlimeFirstPhase(BossBattle battle)
	{
		super(battle);
		var bullet = new BulletCreationFunction()
		{
			
			@Override
			public Projectile createProjectile()
			{
				var projectileStats = new CreatureStats();
				projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 5);
				return new SlimeProjectile("def", 0, 0, projectileStats, battle.getBoss());
			}
		};
		
		List<ProjectileSpawner> projSpawner = new ArrayList<>();
		for(int i = 0; i < 4; ++i)
		{
			var face = i * 20;
			var proj = new ProjectileSpawner(70, new CreatureStats(), new Vector2(0, 0), bullet, battle.getBoss());
			proj.addPattern(new SpinMovementPattern(proj, 250));
			proj.currentBulletSpeed = 0.09f;
			proj.setFacing(face);
			projSpawner.add(proj);
		}
		
		var projSpawner2 = new ProjectileSpawner(10, new CreatureStats(), new Vector2(0, 0), bullet, battle.getBoss());
		projSpawner2.addPattern(new SpinMovementPattern(projSpawner2, 30));
		projSpawner2.currentBulletSpeed = 0.09f;
		var testProjectileAction = new ProjectileBossAction(projSpawner, 60, 50);
		var testProjectileAction2 = new ProjectileBossAction(Collections.singletonList(projSpawner2), 40, 30);
		
		var waitAction = new WaitAction(50, 70);
		var waitAction2 = new WaitAction(70, 100);
		var puddleAction = new GroundEffectBossAction(new PuddleFireAOE(), 1, new Vector2(0, 0));
		
		actions.add(testProjectileAction);
		actions.add(waitAction);
		actions.add(puddleAction);
		actions.add(testProjectileAction2);
		actions.add(waitAction2);
	}
	
	@Override
	public void run()
	{
		super.run();
		//        System.out.println("Running first phase");
	}
	
	@Override
	protected List<BossAction> getActions()
	{
		return actions;
	}
	
	@Override
	public void cleanStage()
	{
		super.cleanStage();
	}
	
	@Override
	public void init()
	{
		super.init();
	}
}
