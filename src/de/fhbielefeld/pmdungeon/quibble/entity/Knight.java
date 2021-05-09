package de.fhbielefeld.pmdungeon.quibble.entity;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class Knight extends Player
{
	/**
	 * Creates a knight instance at the given coordinates.
	 * The coordinates can be changed after creating the knight by calling
	 * {@link Knight#setPosition(float, float)}. This way it can be placed anywhere in the dungeon.
	 * @param x x position
	 * @param y y position
	 */
	public Knight(float x, float y)
	{
		super(x, y);
		//Default idle animation will always be played if no other animation is being played
		//This must be added or an exception will be thrown
		this.animationHandler.addAsDefaultAnimation(Creature.ANIM_NAME_IDLE_R, 4, 5, "assets/textures/entity/knight/knight_m_idle_right_anim_f.png", 4);
		
		//Other animations
		this.animationHandler.addAnimation(Creature.ANIM_NAME_IDLE_L, 4, 5, "assets/textures/entity/knight/knight_m_idle_left_anim_f.png", 4);
		
		this.animationHandler.addAnimation(Creature.ANIM_NAME_RUN_R, 4, 2, "assets/textures/entity/knight/knight_m_run_right_anim_f.png", 4);
		this.animationHandler.addAnimation(Creature.ANIM_NAME_RUN_L, 4, 2, "assets/textures/entity/knight/knight_m_run_left_anim_f.png", 4);
		this.animationHandler.addAnimation(Creature.ANIM_NAME_HIT_R, 1, 15, "assets/textures/entity/knight/knight_m_hit_right_anim_f.png", 4);
		this.animationHandler.addAnimation(Creature.ANIM_NAME_HIT_L, 1, 15, "assets/textures/entity/knight/knight_m_hit_left_anim_f.png", 4);
		
		this.getEquippedItems().addItem(Item.POTION_SIGHT_BIG);
	}
	
	/**
	 * Creates a knight instance at <code>x = 0</code> and <code>y = 0</code>.
	 * The coordinates can be changed after creating the knight by calling
	 * {@link Knight#setPosition(float, float)}. This way it can be placed anywhere in the dungeon.
	 */
	public Knight()
	{
		this(0.0F, 0.0F);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.35F, -0.5F, 0.7F, 1.0F);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CreatureStats getBaseStatsForLevel(int level)
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.HEALTH, 10 + level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);
		stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.4D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.1D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 1.0D + level);
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1.0D);
		stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.1D + level * 0.025D);
		stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.6D);
		stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15.0D);
		return stats;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Point getWeaponHoldOffset()
	{
		return new Point(0.0F, 1.25F);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean useHitAnimation()
	{
		return true;
	}
	
	@Override
	public int getInventorySlots()
	{
		return 8;
	}
	
	@Override
	public int getEquipmentSlots()
	{
		return 3;
	}
}
