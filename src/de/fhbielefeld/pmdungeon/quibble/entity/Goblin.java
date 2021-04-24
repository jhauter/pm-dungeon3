package de.fhbielefeld.pmdungeon.quibble.entity;

import com.badlogic.gdx.ai.pfa.GraphPath;

import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class Goblin extends Creature
{
	private GraphPath<Tile> currentMovement;
	
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
		this.animationHandler.addAsDefaultAnimation(Creature.ANIM_NAME_IDLE_R, 4, 5, "assets/textures/entity/goblin", "goblin");
		
		//Other animations
		this.animationHandler.addAnimation(Creature.ANIM_NAME_IDLE_L, 4, 5, "assets/textures/entity/goblin", "goblin");
		
		this.animationHandler.addAnimation(Creature.ANIM_NAME_RUN_R, 4, 2, "assets/textures/entity/goblin", "goblin");
		this.animationHandler.addAnimation(Creature.ANIM_NAME_RUN_L, 4, 2, "assets/textures/entity/goblin", "goblin");
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
	protected BoundingBox getInitBoundingBox()
	{
		return new BoundingBox(-0.35F, -0.5F, 0.7F, 1.0F);
	}

	@Override
	protected CreatureStats getBaseStatsForLevel(int level)
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.HEALTH, 4 + level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
		stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);
		stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.25D);
		stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.1D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 1.0D);
		stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1.0D);
		stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.05D);
		stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.4D);
		stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15.0D);
		return stats;
	}

	@Override
	public Point getWeaponHoldOffset()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void updateLogic()
	{
		super.updateLogic();
		DungeonWorld dungeon = this.level.getDungeon();
		if(this.currentMovement == null || this.followPath(this.currentMovement))
		{
			Coordinate moveTarget = dungeon.getRandomLocationInDungeon();
			this.currentMovement = dungeon.findPath(dungeon.getTileAt((int)this.getX(), (int)this.getY()), dungeon.getTileAt(moveTarget));
		}
	}
}
