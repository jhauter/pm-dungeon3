package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
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

public class GolemStartPhase extends BossPhase
{
	private final ArrayList<BossAction> actions = new ArrayList<>();
	private Random rand = new Random();
	
	public GolemStartPhase(BossBattle battle)
	{
		super(battle);
		var bullet = new BulletCreationFunction()
		{
			@Override
			public Projectile createProjectile()
			{
				var projectileStats = new CreatureStats();
				projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 4);
				return new GolemProjectile("def", 0, 0, projectileStats, battle.getBoss());
			}
		};
		
		List<ProjectileSpawner> projSpawner = new ArrayList<>();
		
		for(int i = 0; i < 3; ++i)
		{
			var face = rand.nextInt(360);
			var proj = new ProjectileSpawner(50, new CreatureStats(), new Vector2(0, 0), bullet, battle.getBoss());
			proj.addPattern(new SpinMovementPattern(proj, 200));
			proj.currentBulletSpeed = 0.08f;
			proj.setFacing(face);
			projSpawner.add(proj);
		}
		var testProjectileAction = new ProjectileBossAction(projSpawner, 80, 40);
		
		var knockbackAction = new GroundEffectBossAction(new KnockbackGroundAOE(), 2, new Vector2(0, 0));
		
		actions.add(testProjectileAction);
		actions.add(knockbackAction);
	}
	
	@Override
	public void init()
	{
		super.init();
	}
	
	@Override
	protected List<BossAction> getActions()
	{
		return actions;
		
	}
}