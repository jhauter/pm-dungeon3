package de.fhbielefeld.pmdungeon.quibble.entity;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class Mage extends Player
{
	public Mage(float x, float y)
	{
		super(x, y);
		
		this.animationHandler.addAsDefaultAnimation(Creature.ANIM_NAME_IDLE, 4, 0.15F, 1, 4, "assets/textures/entity/mage/wizzard_m_idle.png");
		this.animationHandler.addAnimation(Creature.ANIM_NAME_RUN, 4, 0.1F, 1, 4, "assets/textures/entity/mage/wizzard_m_run.png");
		this.animationHandler.addAnimation(Creature.ANIM_NAME_HIT, 1, 0.5F, 1, 1, "assets/textures/entity/mage/wizzard_m_hit.png");
	}
	
	public Mage()
	{
		this(0.0F, 0.0F);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CreatureStats getBaseStatsForLevel(int level)
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.HEALTH, 5 + level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);
		stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.4D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.1D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 1.0D + level);
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 3.0D + level);
		stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.1D + level * 0.003D);
		stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.6D);
		stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15.0D);
		stats.setStat(CreatureStatsAttribs.FOW_SIGHT, 1.0D);
		return stats;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector2 getItemHoldOffset()
	{
		return new Vector2(0.15F, -0.2F);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.35F, 0.0F, 0.7F, 0.75F);
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
		return 3;
	}
	
	@Override
	public int getEquipmentSlots()
	{
		return 2;
	}
	
	@Override
	public String getPortraitImagePath()
	{
		return "assets/textures/entity/mage/wizzard_m_portrait.png";
	}
}
