package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

/**
 * Contains information about the current BossBattle
 */
public abstract class BossBattle extends Entity
{
	
	/*
	    Represents the current state of the fight independent from the stage queue.
	 */
	public DungeonLevel level;
	
	/**
	 * Indicates whether the boss battle is still running or should be ended
	 */
	private boolean active = true;
	private boolean startBattle = false;
	
	private UIBossBar bossBar;
	private BossCutsceneHandler cutscene;
	private boolean initialized = false;
	
	private float initialPlayerSpeed = 0;
	private float initialBossSpeed = 0;
	private boolean playCutscene = true;
	protected Vector2 initialPosition = new Vector2(9, 28);
	
	/**
	 *
	 * @param level Current level
	 */
	public BossBattle(DungeonLevel level)
	{
		this.level = level;
		level.spawnEntity(this);
		
		var builder = getBossInformation();
		boss = createBoss(builder);
	}
	
	/**
	 * Instance of the boss
	 */
	protected Boss boss;
	
	/**
	 * Phase that is currently being executed
	 */
	protected BossPhase currentPhase;
	
	/**
	 * Builds a new boss from a BossBuilder structure
	 * @param boss Information
	 * @return Built Boss-entity
	 */
	public Boss createBoss(BossBuilder boss)
	{
		return new Boss(boss);
	}
	
	/**
	 * Starts the boss-battle
	 */
	public void start()
	{
		DungeonStart.getDungeonMain().setCanLeaveDungeon(false);
		this.level.spawnEntity(this);
		prepareArea();
		bossBar = new UIBossBar();
		bossBar.setBoss(boss);
		
		DungeonStart.getDungeonMain().getUIManager().addUI(bossBar);
		boss.setPosition(getInitialBossPosition());
		this.level.spawnEntity(boss);
		this.cutscene = new BossCutsceneHandler(boss, level, DungeonStart.getDungeonMain().getPlayer());
		this.initialPlayerSpeed = DungeonStart.getDungeonMain().getPlayer().getWalkingSpeed();
		this.initialBossSpeed = boss.getWalkingSpeed();
		
		cutscene.playCutscene();
		startBattle = true;
		// TODO: set boss invisable?
		// moved spawn animation to updateLogic
	}
	
	public void start(boolean playCutscene)
	{
		this.playCutscene = playCutscene;
		this.level.spawnEntity(this);
		prepareArea();
		bossBar = new UIBossBar();
		bossBar.setBoss(boss);
		
		DungeonStart.getDungeonMain().getUIManager().addUI(bossBar);
		boss.setPosition(getInitialBossPosition());
		this.level.spawnEntity(boss);
		this.cutscene = new BossCutsceneHandler(boss, level, DungeonStart.getDungeonMain().getPlayer());
		this.initialPlayerSpeed = DungeonStart.getDungeonMain().getPlayer().getWalkingSpeed();
		this.initialBossSpeed = boss.getWalkingSpeed();
		startBattle = true;
		if(playCutscene)
		{
			cutscene.playCutscene();
		}
	}
	
	public void start(boolean playCutscene, Vector2 pos)
	{
		start(playCutscene);
		initialPosition = pos;
	}
	
	/**
	 * Prepared the area if needed by removing enemy entities
	 */
	public void prepareArea()
	{
		var enemies = this.level.getAllEntitiesOf(Creature.class,
			DungeonStart.getDungeonMain().getPlayer());
		enemies.forEach(Creature::setDead);
	}
	
	/**
	 * Adds the next action in the action pool to the active actions. Wraps to the first action
	 * if actions have been exhausted
	 */
	public void nextAction()
	{
		currentPhase.nextAction();
	}
	
	/**
	 * @param action Action to be removed from active actions
	 */
	public void removeAction(BossAction action)
	{
		currentPhase.removeAction(action);
	}
	
	protected abstract void onBossBattleEnd();
	
	private void onBossBattleEndClean()
	{
		getCurrentPhase().cleanStage();
		bossBar.setBoss(null);
		DungeonStart.getDungeonMain().getUIManager().removeUI(bossBar);
		active = false;
		DungeonStart.getDungeonMain().setCanLeaveDungeon(true);
	}
	
	abstract protected boolean isBattleOver();
	
	@Override
	protected void updateLogic()
	{
		if(!startBattle)
		{
			return;
		}
		if(!initialized)
		{
			if(!playCutscene || cutscene.bossReached())
			{
				boss.playAttackAnimation("spawn", false, 10);
			}
			if(!playCutscene || cutscene.isFinished())
			{
				currentPhase = getCurrentPhase();
				currentPhase.init();
				this.initialized = true;
				boss.getCurrentStats().setStat(CreatureStatsAttribs.WALKING_SPEED, initialBossSpeed);
				DungeonStart.getDungeonMain().getPlayer().getCurrentStats().setStat(CreatureStatsAttribs.WALKING_SPEED, initialPlayerSpeed);
			}
		}
		else
		{
			if(isBattleOver())
			{
				onBossBattleEnd();
				onBossBattleEndClean();
			}
			else
			{
				switchPhase();
				getCurrentPhase().run();
			}
		}
	}
	
	@Override
	protected void updateEnd()
	{
		super.updateEnd();
	}
	
	@Override
	public void onSpawn(DungeonLevel level)
	{
		super.onSpawn(level);
	}
	
	/**
	 * Executed each frame, checks if current phase should be switched
	 */
	abstract protected void switchPhase();
	
	/**
	 * @return The position that the Boss is supposed to spawn at
	 */
	abstract public Vector2 getInitialBossPosition();
	
	abstract protected BossBuilder getBossInformation();
	
	abstract protected BossPhase getCurrentPhase();
	
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(0, 0, 0.5F, 5);
	}
	
	public boolean useAnimationHandler()
	{
		return false;
	}
	
	public boolean isInvisible()
	{
		return true;
	}
	
	@Override
	public boolean shouldDespawn()
	{
		return !this.active;
	}
	
	public Boss getBoss()
	{
		return boss;
	}
}
