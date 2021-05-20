package de.fhbielefeld.pmdungeon.quibble.entity.ai;

import com.badlogic.gdx.ai.pfa.GraphPath;

import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class AIApproachTarget implements AIStrategy
{
	private GraphPath<Tile> currentMovement;
	
	private Creature target;
	
	private Point targetLocation;
	
	private Point targetLocationPrev;
	
	public AIApproachTarget(Creature target)
	{
		this.setTarget(target);
	}
	
	public Creature getTarget()
	{
		return this.target;
	}
	
	public void setTarget(Creature target)
	{
		this.target = target;
		this.targetLocation = new Point(this.target.getX(), this.target.getY());
		this.targetLocationPrev = new Point(this.targetLocation.x, this.targetLocation.y);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeStrategy(NPC entity)
	{
		DungeonWorld dungeon = entity.getLevel().getDungeon();
		if(this.currentMovement == null ||
			!this.pointEquals(this.targetLocation, this.targetLocationPrev)) //If the target changed position
		{
			this.targetLocation = new Point(this.target.getX(), this.target.getY());
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
		this.targetLocationPrev = new Point(this.target.getX(), this.target.getY());
	}
	
	private boolean pointEquals(Point a, Point b)
	{
		boolean b1 = Math.abs(b.x - a.x) < 0.01F && Math.abs(b.y - a.y) < 0.01F;
		return b1;
	}
}
