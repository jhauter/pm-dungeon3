package de.fhbielefeld.pmdungeon.quibble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleSystem;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.EntityController;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public class DungeonLevel
{
	private final DungeonWorld world;
	
	private final EntityController entityController;
	
	private final ParticleSystem particleSystem;
	
	private final List<Entity> newEntityBuffer;
	
	private Random rng;
	
	/**
	 * Creates a Level that contains a <code>DungeonWorld</code> and an <code>EntityController</code>.
	 * The parameters should be copied from the main controller as this class is basically a convenience class.
	 * An entity buffer is also created.
	 * @param world dungeon reference
	 * @param entityController entity controller reference
	 */
	
	public DungeonLevel(DungeonWorld world, EntityController entityController)
	{
		this.world = world;
		this.entityController = entityController;
		this.particleSystem = new ParticleSystem();
		this.newEntityBuffer = new ArrayList<Entity>();
		this.rng = new Random();
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
		EntityEvent spawnEvent = entity.fireEvent(new EntityEvent(Entity.EVENT_ID_SPAWN, entity));
		if(spawnEvent.isCancelled())
		{
			//Don't spawn entity if event is cancelled
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
	
	public ParticleSystem getParticleSystem()
	{
		return this.particleSystem;
	}
	
	/**
	 * Returns the number of entities actually in the level. (not in the buffer)
	 * @return amount of entities in the level
	 */
	public int getNumEntities()
	{
		return this.entityController.getList().size();
	}
	
	public Entity getEntity(int index)
	{
		//We can cast because we only add "our" entity
		return (Entity)this.entityController.getList().get(index);
	}
	
	public Random getRNG()
	{
		return this.rng;
	}
	
	public List<Entity> getEntitiesInArea(BoundingBox area)
	{
		List<IEntity> entityList = this.entityController.getList();
		List<Entity> entitiesInArea = new ArrayList<Entity>();
		Entity currentEntity;
		for(int i = 0; i < entityList.size(); ++i)
		{
			currentEntity = (Entity)entityList.get(i);
			if(currentEntity.getBoundingBox().offset(currentEntity.getX(), currentEntity.getY()).intersects(area))
			{
				entitiesInArea.add(currentEntity);
			}
		}
		return entitiesInArea;
	}
	
	public List<Entity> getEntitiesInRadius(float x, float y, float radius, Entity... exclude)
	{
		List<IEntity> entityList = this.entityController.getList();
		List<Entity> entitiesInRadius = new ArrayList<Entity>();
		Entity currentEntity;
		for(int i = 0; i < entityList.size(); ++i)
		{
			currentEntity = (Entity)entityList.get(i);
			if(Math.pow(currentEntity.getX() - x, 2) + Math.pow(currentEntity.getY() - y, 2) <= Math.pow(radius + currentEntity.getRadius(), 2)
				&& !Arrays.asList(exclude).contains(currentEntity))
			{
				entitiesInRadius.add(currentEntity);
			}
		}
		return entitiesInRadius;
	}
}
