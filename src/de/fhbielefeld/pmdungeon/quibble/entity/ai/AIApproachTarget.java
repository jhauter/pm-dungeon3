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
 * An <code>AIStrategy</code> that makes NPCs approach a creature until they are close enough to touch it,
 * thus usually hitting it.
 * 
 * @author Andreas
 */
public class AIApproachTarget implements AIStrategy
{
	private GraphPath<Tile> currentMovement;
	
	private Creature target;
	
	private Vector2 targetLocation;
	
	private Vector2 targetLocationPrev;
	
	/**
	 * Creates an object of this <code>AIStrategy</code> which can directly be used with NPCs.
	 * NPCs with this <code>AIStrategy</code> will attempt to approach a creature until they touch it.
	 * @param target the creature that the NPC with this <code>AIStrategy</code> will approach
	 */
	public AIApproachTarget(Creature target)
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
		if(this.getTarget().isDead())
		{
			return;
		}
		DungeonWorld dungeon = entity.getLevel().getDungeon();
		if(this.currentMovement == null ||
			!GeometryUtil.pointProximity(this.targetLocation, this.targetLocationPrev, 0.01F)) //If the target changed position
		{
			this.targetLocation = new Vector2(this.target.getX(), this.target.getY());
			final Coordinate c = new Coordinate((int)this.targetLocation.x, (int)this.targetLocation.y);
			this.currentMovement = dungeon.findPath(dungeon.getTileAt((int)entity.getX(), (int)entity.getY()), dungeon.getTileAt(c));
		}
		if(entity.followPath(this.currentMovement)) //If target is close enough that the path does not reach fully
		{
			float dirX = this.targetLocation.x - entity.getX();
			float dirY = this.targetLocation.y - entity.getY();
			float angle = (float)Math.toDegrees(Math.atan2(dirY, dirX));
			entity.walk(angle, 1.0F);
		}
		this.targetLocationPrev = new Vector2(this.target.getX(), this.target.getY());
	}
}
