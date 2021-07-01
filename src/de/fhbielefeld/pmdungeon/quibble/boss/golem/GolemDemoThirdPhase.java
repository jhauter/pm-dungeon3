package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.SpawnGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossPhase;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.GroundEffectBossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.ProjectileBossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

public class GolemDemoThirdPhase extends BossPhase
{
	private ArrayList<BossAction> actions = new ArrayList<>();
	
	public GolemDemoThirdPhase(BossBattle battle)
	{
		super(battle);
		var bullet = new BulletCreationFunction()
		{
			@Override
			public Projectile createProjectile()
			{
				var projectileStats = new CreatureStats();
				projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 7f);
				return new GolemProjectile("def", 0, 0, projectileStats, battle.getBoss());
			}
		};
		
		var projectile = new ProjectileSpawner(15, new CreatureStats(), new Vector2(0, 0), bullet, battle.getBoss());
		projectile.addPattern(new SpinMovementPattern(projectile, 200));
		projectile.currentBulletSpeed = 0.2f;
		
		var knockbackAction = new GroundEffectBossAction(new KnockbackGroundAOE(), 300, 200, 2, new Vector2(0, 0));
		var spawnAction = new GroundEffectBossAction(new SpawnGroundAOE(), 100, 20, 2, new Vector2(0, 0));
		
		var testProjectileAction = new ProjectileBossAction(Collections.singletonList(projectile), 150, 20);
		
		//TODO: THIS STILL DOES NOT WORK EDIT: IT DOES NOW
		actions.add(spawnAction);
		actions.add(knockbackAction);
		actions.add(testProjectileAction);
	}
	
	@Override
	protected List<BossAction> getActions()
	{
		return this.actions;
	}
	
	@Override
	public void run()
	{
		super.run();
		
	}
	
	@Override
	public void init()
	{
		//Tranform Animation
		battle.getBoss().physBuff = 12;
		battle.getBoss().magBuff = 3;
		battle.getBoss().updateMaxStats();
		
		//BossBattle.boss.setPosition(battle.getInitialBossPosition());
		
		battle.getBoss().playAttackAnimation("arm_transform", false, 12);
		
		var arm = new GolemArmAdd(battle.getBoss().getPosition().x, battle.getBoss().getPosition().y);
		
		battle.level.spawnEntity(arm);
		super.init();
	}
}
