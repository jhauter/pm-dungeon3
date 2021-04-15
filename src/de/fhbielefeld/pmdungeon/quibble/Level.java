package de.fhbielefeld.pmdungeon.quibble;

import java.util.ArrayList;
import java.util.List;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.EntityController;

public class Level
{
	private final DungeonWorld world;
	
	private final EntityController entityController;
	
	private List<Entity> newEntityBuffer;
	
	/**
	 * Creates a Level that contains a <code>DungeonWorld</code> and an <code>EntityController</code>.
	 * The parameters should be copied from the main controller as this class is basically a convenience class.
	 * An entity buffer is also created.
	 * @param world dungeon reference
	 * @param entityController entity controller reference
	 */
	
	public Level(DungeonWorld world, EntityController entityController)
	{
		this.world = world;
		this.entityController = entityController;
		this.newEntityBuffer = new ArrayList<Entity>();
	}
	
	/**
	 * @return the dungeon of this level
	 */
	public DungeonWorld getDungeon()
	{
		return this.world;
	}
	
	/**
	 * This is the preferred way to add entities to a level.
	 * This ensures that all entity resources are loaded and level reference of the entity is set correctly.
	 * @param entity the entity to add to the level
	 */
	public void spawnEntity(Entity entity)
	{
		if(!entity.isLoadedResources() && !entity.loadResources())
		{
			//Don't add entity if an error occurs
			return;
		}
		this.newEntityBuffer.add(entity);
		entity.onSpawn(this); //Entity is not actually spawned but still in buffer but makes little difference
	}
	
	/**
	 * Flushes the entity buffer which holds entities that should be added and adds them to the level.
	 */
	public void flushEntityBuffer()
	{
		this.newEntityBuffer.forEach(e -> this.entityController.addEntity(e));
		this.newEntityBuffer.clear();
	}
	
	/**
	 * Whether the entity buffer is empty, i.e. whether any entities have been requested to spawn
	 * since the last flush.
	 * @return whether the entity buffer is empty
	 */
	public boolean isEntityBufferEmpty()
	{
		return this.newEntityBuffer.isEmpty();
	}
}
