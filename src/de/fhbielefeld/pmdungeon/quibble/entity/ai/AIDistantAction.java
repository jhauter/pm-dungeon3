package de.fhbielefeld.pmdungeon.quibble.entity.ai;

import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.util.GeometryUtil;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;

/**
 * An <code>AIStrategy</code> that makes NPCs approach a creature until they are close enough.
 * The specific distance can be set by overriding the {@link #getAimDistanceSq()} method.
 * Once the distance between the NPC and the target is small enough and the NPC has a
 * line of sight to the target, the NPC will stop following the target and perform an action in an
 * interval. The action can be defined by overriding {@link #onAction(NPC)}. Actions can for example
 * be shooting projectiles. The interval can also be defined by overriding {@link #getTicksBetweenActions()}.
 * 
 * @author Andreas
 */
public abstract class AIDistantAction implements AIStrategy
{
	private GraphPath<Tile> currentMovement;
	
	private Creature target;
	
	private Vector2 targetLocation;
	
	private Vector2 targetLocationPrev;
	
	private int ticksUntilNextAction;
	
	/**
	 * Creates an object of this <code>AIStrategy</code> which can directly be used with NPCs.
	 * NPCs with this <code>AIStrategy</code> will attempt to approach a creature until they are close enough.
	 * Once they are close enough, they will do an action which can be defined in a sub class.
	 * This action is performed in an interval which can also be set.
	 * @param target the creature that the NPC with this <code>AIStrategy</code> will approach and target
	 */
	public AIDistantAction(Creature target)
	{
		this.setTarget(target);
	}
	
	/**
	 * @return the target that the NPC with this <code>AIStrategy</code> approaches
	 */
	public Creature getTarget()
	{
		return this.target;
	}
	
	/**
	 * Sets the target that the NPC with this <code>AIStrategy</code> should approach
	 * @param target the target to approach
	 */
	public void setTarget(Creature target)
	{
		this.target = target;
		this.targetLocation = new Vector2(this.target.getX(), this.target.getY());
		this.targetLocationPrev = new Vector2(this.targetLocation.x, this.targetLocation.y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeStrategy(NPC entity)
	{
		DungeonWorld dungeon = entity.getLevel().getDungeon();
		if(this.currentMovement == null ||
			!GeometryUtil.pointProximity(this.targetLocation, this.targetLocationPrev, 0.01F)) //If the target changed position
		{
			this.targetLocation = new Vector2(this.target.getX(), this.target.getY());
			final Coordinate c = new Coordinate((int)this.targetLocation.x, (int)this.targetLocation.y);
			this.currentMovement = dungeon.findPath(dungeon.getTileAt((int)entity.getX(), (int)entity.getY()), dungeon.getTileAt(c));
		}
		if(!entity.hasLineOfSightTo(targetLocation) || Vector2.dst2(entity.getX(), entity.getY(), targetLocation.x, targetLocation.y) > this.getAimDistanceSq())
		{
			//Follow the target until it is in sight and the distance is low enough
			entity.followPath(this.currentMovement);
		}
		else
		{
			//Now if the distance is low enough, we can attack the target
			if(this.ticksUntilNextAction <= 0)
			{
				this.onAction(entity);
				this.ticksUntilNextAction = this.getTicksBetweenActions();
			}
			else
			{
				--this.ticksUntilNextAction;
			}
		}
		this.targetLocationPrev = new Vector2(this.target.getX(), this.target.getY());
	}
	
	/**
	 * This is called once the NPC is close enough to the target and has a line of sight to the target.
	 * The method {@link #getTicksBetweenActions()} defines the interval between calls of this method.
	 * @param entity the entity that is using this <code>AIStrategy</code>
	 */
	public abstract void onAction(NPC entity);
	
	/**
	 * Determined how many ticks to wait between calls of {@link #onAction(NPC)} while the NPC
	 * is close enough to the target and has a line of sight to the target.
	 * @return the ticks between calls of {@link #onAction(NPC)}
	 */
	public abstract int getTicksBetweenActions();
	
	/**
	 * Returns the distance that the NPC that uses this <code>AIStrategy</code> tries to close until it
	 * can perform actions. Note that this method should return the squared distance.
	 * So for example if the distance should be <code>5</code>, then this method must return <code>25</code>.
	 * @return the squared distance that the NPC tries to close until it performs the action
	 */
	public abstract float getAimDistanceSq();
}
