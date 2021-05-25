package de.fhbielefeld.pmdungeon.quibble.entity;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.entity.ai.AIShootFireball;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.item.Item;

public class Lizard extends NPC{

	private boolean noticedPlayer;
	
	/**
	 * Creates a Lizard instance at the given coordinates. The coordinates can be
	 * changed after creating the goblin by calling
	 * {@link Lizard#setPosition(float, float)}. This way it can be placed anywhere
	 * in the dungeon.
	 * 
	 * @param x x-position
	 * @param y y-position
	 */
	public Lizard(float x, float y) {
		super(x, y);
		// Default idle animation will always be played if no other animation is being
		// played
		// This must be added or an exception will be thrown
		this.animationHandler.addAsDefaultAnimation(Creature.ANIM_NAME_IDLE_R, 4, 5,
				"assets/textures/entity/lizard/lizard_m_idle_right_anim_f.png", 4);

		// Other animations
		this.animationHandler.addAnimation(Creature.ANIM_NAME_IDLE_L, 4, 5,
				"assets/textures/entity/lizard/lizard_m_idle_left_anim_f.png", 4);

		this.animationHandler.addAnimation(Creature.ANIM_NAME_RUN_R, 4, 2,
				"assets/textures/entity/lizard/lizard_m_run_right_anim_f.png", 4);
		this.animationHandler.addAnimation(Creature.ANIM_NAME_RUN_L, 4, 2,
				"assets/textures/entity/lizard/lizard_m_run_left_anim_f.png", 4);
		
		getEquippedItems().addItem(Item.SIMPLE_BOW);
	}

	/**
	 * Creates a Lizard instance at <code>x = 0</code> and <code>y = 0</code>. The
	 * coordinates can be changed after creating the knight by calling
	 * {@link Lizard#setPosition(float, float)}. This way it can be placed anywhere
	 * in the dungeon.
	 */
	public Lizard() {
		this(0.0F, 0.0F);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected BoundingBox getInitBoundingBox() {
		return new BoundingBox(-0.35F, 0.0F, 0.7F, 0.8F);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CreatureStats getBaseStatsForLevel(int level) {
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.HEALTH, 6 + level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);
		stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.0D);
		stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.25D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.1D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2.0D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 3.0D);
		stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.05D);
		stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.4D);
		stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 20.0D);
		return stats;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onEntityCollision(Entity otherEntity) {
		super.onEntityCollision(otherEntity);
		if (otherEntity instanceof Player) // Attack player when touched
		{
			this.attack((Player) otherEntity);
		}
	}
	
	@Override
	public void calculateCurrentBehavior()
	{
		//Shoot fire balls
		List<Player> players = this.level.getPlayers();
		if(players.isEmpty())
		{
			//Can happen if the player dies?
			return;
		};
		if(!this.noticedPlayer && this.hasLineOfSightTo(new Vector2(players.get(0).getPosition().x, players.get(0).getPosition().y)))
		{
			this.noticedPlayer = true;
			this.setAIStrategy(new AIShootFireball(players.get(0)));
		}
	}

	@Override
	public int getExpDrop() {
		return 7;
	}

	@Override
	public int getEquipmentSlots() {
		return 1;
	}
}
