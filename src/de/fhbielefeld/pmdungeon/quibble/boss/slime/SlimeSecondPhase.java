package de.fhbielefeld.pmdungeon.quibble.boss.slime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.boss.attacks.SpawnGroundAOE;
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

public class SlimeSecondPhase extends BossPhase
{
	private ArrayList<BossAction> actions;
	
	public SlimeSecondPhase(BossBattle battle)
	{
		super(battle);
		this.actions = new ArrayList<>();
		var bullet = new BulletCreationFunction()
		{
			@Override
			public Projectile createProjectile()
			{
				var projectileStats = new CreatureStats();
				projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 3);
				return new SlimeProjectile("def", 0, 0, projectileStats, battle.getBoss());
			}
		};
		var fireball = new BulletCreationFunction()
		{
			@Override
			public Projectile createProjectile()
			{
				var projectileStats = new CreatureStats();
				projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 5);
				return new FireballProjectile("def", 0, 0, projectileStats, battle.getBoss());
			}
		};
		
		List<ProjectileSpawner> projSpawner = new ArrayList<>();
		for(int i = 0; i < 4; ++i)
		{
			var face = i * 40;
			var proj = new ProjectileSpawner(40, new CreatureStats(), new Vector2(0, 0), bullet, battle.getBoss());
			proj.addPattern(new SpinMovementPattern(proj, 250));
			proj.currentBulletSpeed = 0.07f;
			proj.setFacing(face);
			projSpawner.add(proj);
		}
		var fireballSpawner = new ProjectileSpawner(20, new CreatureStats(), new Vector2(0, 0), fireball, battle.getBoss());
		fireballSpawner.addPattern(new SpinMovementPattern(fireballSpawner, 150));
		fireballSpawner.currentBulletSpeed = 0.07f;
		var waitAction = new WaitAction(120, 100);
		var spawnGround = new SpawnGroundAOE();
		spawnGround.setTarget(new FireSlimeAdd());
		var spawnAction = new GroundEffectBossAction(spawnGround, 1, new Vector2(0, 0));
		var projAction = new ProjectileBossAction(projSpawner, 60, 50);
		var projAction2 = new ProjectileBossAction(Collections.singletonList(fireballSpawner), 200, 50);
		actions.add(projAction);
		//actions.add(waitAction);
		actions.add(spawnAction);
		actions.add(projAction2);
	}
	
	@Override
	protected List<BossAction> getActions()
	{
		return actions;
	}
	
	@Override
	public void init()
	{
		super.init();
	}
}
