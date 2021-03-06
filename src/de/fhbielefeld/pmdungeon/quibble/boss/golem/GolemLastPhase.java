package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossPhase;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.ProjectileBossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.WaitAction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.boss.misc.CamRumbleEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class GolemLastPhase extends BossPhase
{
	private int teleportCD = 200;
	private int teleportCDCounter = 0;
	private Entity mage = new GolemMageAdd(new Vector2(0, 0));
	private Random rand = new Random();
	private BossBattle battle;
	private ArrayList<BossAction> actions = new ArrayList<>();
	
	public GolemLastPhase(BossBattle battle)
	{
		super(battle);
		var bullet = new BulletCreationFunction()
		{
			@Override
			public Projectile createProjectile()
			{
				var projectileStats = new CreatureStats();
				projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 6f);
				return new GolemProjectile("def", 0, 0, projectileStats, battle.getBoss());
			}
		};
		ArrayList<ProjectileSpawner> projSpawner = new ArrayList<>();
		for(int i = 0; i < 9; ++i)
		{
			var face = rand.nextInt(360);
			var spawner = new ProjectileSpawner(30, new CreatureStats(), new Vector2(0, 0), bullet, battle.getBoss());
			spawner.setFacing(face);
			spawner.addPattern(new SpinMovementPattern(spawner, 30));
			spawner.currentBulletSpeed = 0.08f;
			
			projSpawner.add(spawner);
		}
		
		actions.add(new ProjectileBossAction(projSpawner, 300, 300));
		actions.add(new WaitAction(100, 100));
		this.battle = battle;
	}
	
	@Override
	public void run()
	{
		super.run();
		battle.getBoss().playAttackAnimation("panic_idle", true, 19);
		CamRumbleEffect.shake(0.05f, 9999f);
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
	
	@Override
	public void init()
	{
		super.init();
		battle.getBoss().playAttackAnimation("animPanic", false, 20);
	}
	
	@Override
	public void cleanStage()
	{
		super.cleanStage();
	}
	
	@Override
	protected List<BossAction> getActions()
	{
		return actions;
	}
}
