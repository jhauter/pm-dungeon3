package de.fhbielefeld.pmdungeon.quibble.entity;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.ai.AIApproachTarget;
import de.fhbielefeld.pmdungeon.quibble.entity.ai.AIShootArrow;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class Chort extends NPC
{
	
	private boolean noticedPlayer;
	
	private boolean meleeMode;
	
	/**
	 * Creates a Chort instance at the given coordinates. The coordinates can be
	 * changed after creating the goblin by calling
	 * {@link Chort#setPosition(float, float)}. This way it can be placed anywhere
	 * in the dungeon.
	 * 
	 * @param x x-position
	 * @param y y-position
	 */
	public Chort(float x, float y)
	{
		super(x, y);
		// Default idle animation will always be played if no other animation is being
		// played
		// This must be added or an exception will be thrown
		this.animationHandler.addAsDefaultAnimation(Creature.ANIM_NAME_IDLE, 4, 0.1F, 1, 4, "assets/textures/entity/chort/chort_idle.png");
		
		//Other animations
		
		this.animationHandler.addAnimation(Creature.ANIM_NAME_RUN, 4, 0.07F, 1, 4, "assets/textures/entity/chort/chort_run.png");
	}
	
	/**
	 * Creates a Lizard instance at <code>x = 0</code> and <code>y = 0</code>. The
	 * coordinates can be changed after creating the knight by calling
	 * {@link Chort#setPosition(float, float)}. This way it can be placed anywhere
	 * in the dungeon.
	 */
	public Chort()
	{
		this(0.0F, 0.0F);
	}
	
	@Override
	public String getDisplayName()
	{
		return "Chort";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.35F, 0.0F, 0.7F, 0.8F);
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
		stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.1D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.1D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2.0D + level);
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 0.0D);
		stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.05D);
		stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.4D);
		stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 20.0D);
		return stats;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onEntityCollision(Entity otherEntity)
	{
		super.onEntityCollision(otherEntity);
		if(otherEntity instanceof Player) // Attack player when touched
		{
			this.attack((Player)otherEntity);
		}
	}
	
	@Override
	public void calculateCurrentBehavior()
	{
		//Shoot fire balls
		Player player = DungeonStart.getDungeonMain().getPlayer();
		if(!this.noticedPlayer && this.hasLineOfSightTo(new Vector2(player.getPosition().x, player.getPosition().y)))
		{
			this.noticedPlayer = true;
			this.setAIStrategy(new AIShootArrow(player));
		}
		
		if(this.noticedPlayer)
		{
			if(this.meleeMode && this.getPosition().dst2(player.getPosition()) > 12.25F)//3.5 tiles away
			{
				this.setAIStrategy(new AIShootArrow(player));
				this.meleeMode = false;
			}
			else if(!this.meleeMode && this.getPosition().dst2(player.getPosition()) < 7.5F)//2.75 tiles away
			{
				this.meleeMode = true;
				this.setAIStrategy(new AIApproachTarget(player));
			}
		}
	}
	
	@Override
	public int getExpDrop()
	{
		return 5;
	}
}
