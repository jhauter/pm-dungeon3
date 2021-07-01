package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandlerImpl;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class Boss extends NPC
{
	public BossDifficulty difficulty;
	private CreatureStats buff;
	//Not yet used
	private Boss child;
	
	//TODO: NEIN!
	public int physBuff = 0;
	public int magBuff = 0;
	
	/**
	 * Boss entity
	 * @param builder Boss-Info
	 */
	public Boss(BossBuilder builder)
	{
		super(0, 0);
		this.build(builder);
	}
	
	public void growBoundingBox(float x, float y)
	{
		this.boundingBox.grow(x, y);
	}
	
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		this.level.getFogOfWarController().light(this.getX(), this.getY(), 1F);
	}
	
	/**
	 * Builds a boss entity described by the BossBuilder structure
	 * @param builder Boss-info
	 */
	public void build(BossBuilder builder)
	{
		
		setPosition(0, 0);
		
		var scale = builder.renderScale
			.orElseGet(() -> new Vector2(1, 1));
		this.renderWidth = scale.x;
		this.renderHeight = scale.y;
		//NOTE: Maybe only give it to context instead
		this.difficulty = builder.difficulty
			.orElseGet(() -> BossDifficulty.Easy);
		
		var stats = new CreatureStats();
		
		//NOTE: These are weird defaults REPLACE
		stats.setStat(CreatureStatsAttribs.HEALTH, 500);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, 100);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, 5);
		stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.0D);
		stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.25D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.1D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2.0D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 3.0D);
		stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.04D);
		stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.4D);
		stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 20.0D);
		
		this.child = builder.child.orElseGet(() -> null);
		
		ArrayList<AnimationHandlerImpl.AnimationInfo> animDefaultList = new ArrayList<>();
		animDefaultList.add(new AnimationHandlerImpl.AnimationInfo("idle",
			4, 0, 0.15F, 1, 4,
			"assets/textures/entity/demon/big_demon_idle.png"));
		
		var animList = builder.animations.orElseGet(() -> animDefaultList);
		this.animationHandler.addAsDefaultAnimation(animList.get(0));
		
		for(int i = 1; i < animList.size(); ++i)
		{
			this.animationHandler.addAnimation(animList.get(i));
		}
		
	}
	
	@Override
	protected boolean useHitAnimation()
	{
		return true;
	}
	
	@Override
	public int getExpDrop()
	{
		return 50;
	}
	
	public void setRenderOffset(float x, float y)
	{
		this.renderOffsetX = x;
		this.renderOffsetY = y;
	}
	
	public void setRenderSize(float x, float y)
	{
		this.renderWidth = x;
		this.renderHeight = y;
	}
	
	public void setRenderScale(float x, float y)
	{
		this.renderScaleX = x;
		this.renderScaleY = y;
	}
	
	public Vector2 getRenderScale()
	{
		return new Vector2(this.renderScaleX, this.renderScaleY);
	}
	
	public void addDefaultAnimation(AnimationHandlerImpl.AnimationInfo info)
	{
		this.animationHandler.addAsDefaultAnimation(info);
	}
	
	/**
	 * Plays an animation
	 * @param animName Name of the animation held by the animationHandler
	 * @param cyclic Cyclic NOTE: See AnimationHandler
	 * @param priority Priority NOTE: See AnimationHandler
	 */
	public void playAttackAnimation(String animName, boolean cyclic, int priority)
	{
		animationHandler.playAnimation(animName, priority, cyclic);
	}
	
	@Override
	protected CreatureStats getBaseStatsForLevel(int level)
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.HEALTH, 100 + 2 * level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level + physBuff);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level + magBuff);
		stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.0D);
		stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.25D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.3D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 3.0D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1.0D);
		stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.02D);
		stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.4D);
		stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15.0D);
		return stats;
	}
}
