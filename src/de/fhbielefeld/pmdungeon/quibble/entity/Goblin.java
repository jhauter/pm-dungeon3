package de.fhbielefeld.pmdungeon.quibble.entity;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class Goblin extends NPC
{
	/**
	 * Creates a goblin instance at the given coordinates.
	 * The coordinates can be changed after creating the goblin by calling
	 * {@link Goblin#setPosition(float, float)}. This way it can be placed anywhere in the dungeon.
	 * @param x x position
	 * @param y y position
	 */
	public Goblin(float x, float y)
	{
		super(x, y);
		//Default idle animation will always be played if no other animation is being played
		//This must be added or an exception will be thrown
		this.animationHandler.addAsDefaultAnimation(Creature.ANIM_NAME_IDLE, 4, 0.15F, 1, 4, "assets/textures/entity/goblin/goblin_idle.png");
		
		//Other animations
		this.animationHandler.addAnimation(Creature.ANIM_NAME_RUN, 4, 0.1F, 1, 4, "assets/textures/entity/goblin/goblin_run.png");
		
		//Render properties
		this.renderWidth = 1.0F;
		this.renderHeight = 1.0F;
		this.renderOffsetY = this.renderHeight * 0.5F;
	}
	
	/**
	 * Creates a goblin instance at <code>x = 0</code> and <code>y = 0</code>.
	 * The coordinates can be changed after creating the knight by calling
	 * {@link Goblin#setPosition(float, float)}. This way it can be placed anywhere in the dungeon.
	 */
	public Goblin()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public String getDisplayName()
	{
		return "Goblin";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.25F, 0.0F, 0.5F, 0.8F);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CreatureStats getBaseStatsForLevel(int level)
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.HEALTH, 4 + level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);
		stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.0D);
		stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.25D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.1D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2.0D + level);
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1.0D + level);
		stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.05D);
		stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.4D);
		stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15.0D);
		return stats;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onEntityCollision(Entity otherEntity)
	{
		super.onEntityCollision(otherEntity);
		if(otherEntity instanceof Player) //Attack player when touched
		{
			this.attack((Player)otherEntity);
		}
	}
	
	@Override
	public int getExpDrop()
	{
		return 2;
	}
}
