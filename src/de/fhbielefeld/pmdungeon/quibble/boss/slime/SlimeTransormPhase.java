package de.fhbielefeld.pmdungeon.quibble.boss.slime;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandlerImpl;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossPhase;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.GroundEffectBossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.ProjectileBossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.WaitAction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.boss.misc.CamRumbleEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class SlimeTransormPhase extends BossPhase
{
	private ArrayList<BossAction> actions = new ArrayList<>();
	
	public SlimeTransormPhase(BossBattle battle)
	{
		super(battle);
		
		var fireball = new BulletCreationFunction()
		{
			@Override
			public Projectile createProjectile()
			{
				return new FireballProjectile("def", 0, 0, new CreatureStats(), battle.getBoss());
			}
		};
		
		var fireball2 = new BulletCreationFunction()
		{
			@Override
			public Projectile createProjectile()
			{
				return new FireballProjectileLarge("def", 0, 0, new CreatureStats(), battle.getBoss());
			}
		};
		List<ProjectileSpawner> projSpawner = new ArrayList<>();
		for(int i = 0; i < 5; ++i)
		{
			var face = i * 45;
			var proj = new ProjectileSpawner(20, new CreatureStats(), new Vector2((float)Math.pow(-1, i) * 2, (float)Math.pow(-1, i) * 2), fireball,
				battle.getBoss());
			proj.addPattern(new SpinMovementPattern(proj, 250));
			proj.currentBulletSpeed = 0.07f;
			proj.setFacing(face);
			projSpawner.add(proj);
		}
		
		for(int i = 0; i < 5; ++i)
		{
			var face = i * 45;
			var proj = new ProjectileSpawner(20, new CreatureStats(), new Vector2((float)Math.pow(-1, i + 1) * -2, (float)Math.pow(-1, i + 1) * -2), fireball2,
				battle.getBoss());
			proj.addPattern(new SpinMovementPattern(proj, 250));
			proj.currentBulletSpeed = 0.07f;
			proj.setFacing(face);
			projSpawner.add(proj);
		}
		
		for(int i = 0; i < 3; ++i)
		{
			var face = i * 45;
			var proj = new ProjectileSpawner(20, new CreatureStats(), new Vector2((float)Math.pow(-1, i + 1) * 4, (float)Math.pow(-1, i + 1) * -4), fireball,
				battle.getBoss());
			proj.addPattern(new SpinMovementPattern(proj, 250));
			proj.currentBulletSpeed = 0.08f;
			proj.setFacing(face);
			projSpawner.add(proj);
		}
		
		var knockback = new GroundEffectBossAction(new KnockbackGroundAOE(), 1, new Vector2(0, 0));
		var projAction = new ProjectileBossAction(projSpawner, 60, 50);
		var waitAction = new WaitAction(200, 200);
		
		actions.add(knockback);
		actions.add(projAction);
		actions.add(waitAction);
		
	}
	
	public void init()
	{
		super.init();
		//battle.getBoss().setRenderOffset(8, 5);
		battle.getBoss().setRenderSize(10, 8);
		var newidle = new AnimationHandlerImpl.AnimationInfo("idle2",
			6, 192, 0.1f, 12, 32, "assets/textures/entity/boss_slime/sheet.png");
		
		battle.getBoss().addDefaultAnimation(newidle);
		battle.getBoss().playAttackAnimation("transform", false, 14);
		battle.getBoss().heal(100);
	}
	
	@Override
	protected List<BossAction> getActions()
	{
		return actions;
	}
	
	@Override
	public void run()
	{
		super.run();
		
		CamRumbleEffect.shake(0.1f, 9999f);
		var cam = DungeonStart.getDungeonMain().getCamera();
		var fpos = DungeonStart.getDungeonMain().getPlayer().getPosition();
		
		if(CamRumbleEffect.getTimeLeft() > 0)
		{
			
			var rumble = CamRumbleEffect.update();
			cam.setFocusPoint(new Point(fpos.x + rumble.x, fpos.y + rumble.y));
		}
		else
		{
			DungeonStart.getDungeonMain().setCameraTarget(DungeonStart.getDungeonMain().getPlayer());
		}
		
	}
}
