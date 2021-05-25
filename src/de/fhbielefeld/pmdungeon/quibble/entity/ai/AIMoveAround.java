package de.fhbielefeld.pmdungeon.quibble.entity.ai;

import com.badlogic.gdx.ai.pfa.GraphPath;

import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;

public class AIMoveAround implements AIStrategy
{
	private GraphPath<Tile> currentMovement;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void executeStrategy(NPC entity)
	{
		//Always finds a new tile to move to
		DungeonWorld dungeon = entity.getLevel().getDungeon();
		if(this.currentMovement == null || entity.followPath(this.currentMovement))
		{
			Coordinate moveTarget = dungeon.getRandomLocationInDungeon();
			Tile startTile = dungeon.getTileAt((int)entity.getX(), (int)entity.getY());
			Tile endTile = dungeon.getTileAt(moveTarget);
			if(startTile == null || endTile == null)
			{
				return;
			}
			this.currentMovement = dungeon.findPath(startTile, endTile);
		}
	}
}
