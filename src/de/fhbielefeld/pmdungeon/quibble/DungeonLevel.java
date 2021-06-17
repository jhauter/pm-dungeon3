package de.fhbielefeld.pmdungeon.quibble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.SpatialHashGrid.Handle;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleSystem;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;

public class DungeonLevel
{
	private final DungeonWorld world;
	
	private final ParticleSystem particleSystem;
	
	private final List<Entity> entities;
	
	private final List<Entity> newEntityBuffer;
	
	private Random rng;
	
	private SpatialHashGrid<Entity> spatialHashGrid;
	
	/**
	 * Creates a Level that wraps a <code>DungeonWorld</code> and contains all entities in the level.
	 * The <code>MainController.entityController</code> should not be used anymore as all entities are now in
	 * <code>DungeonLevel</code>.
	 * @param world dungeon reference
	 */
	public DungeonLevel(DungeonWorld world, int shgRow, int shgCol, float shgWidth, float shgHeight)
	{
		this.world = world;
		this.particleSystem = new ParticleSystem();
		this.entities = new ArrayList<Entity>();
		this.newEntityBuffer = new ArrayList<Entity>();
		this.rng = new Random();
		this.spatialHashGrid = new SpatialHashGrid<>(shgRow, shgCol, shgWidth, shgHeight);
	}
	
	/**
	 * @return the dungeon of this level
	 */
	public DungeonWorld getDungeon()
	{
		return this.world;
	}
	
	public void update()
	{
		if(!this.isEntityBufferEmpty())
		{
			this.flushEntityBuffer();
		}
		
		Entity current;
		for(int i = 0; i < this.entities.size(); ++i)
		{
			current = this.entities.get(i);
			if(current.shouldDespawn())
			{
				this.removeEntity(i);
				--i;
				continue;
			}
			current.update();
			final BoundingBox entityBB = current.getBoundingBox().offset(current.getX(), current.getY());
			this.spatialHashGrid.update(current.getSpatialHashGridHandle(), entityBB);
		}
	}
	
	/**
	 * Handles <code>onDespawn()</code>, removes from the list and removes from hash grid.
	 * @param index the position of the entity in the list
	 */
	private void removeEntity(int index)
	{
		this.entities.get(index).onDespawn();
		this.spatialHashGrid.remove(this.entities.get(index).getSpatialHashGridHandle());
		
		this.entities.set(index, this.entities.get(this.entities.size() - 1));
		this.entities.remove(this.entities.size() - 1);
	}

	private void removeEntity(Entity entity) {
		entity.onDespawn();
		this.spatialHashGrid.remove(entity.getSpatialHashGridHandle());
		this.entities.remove(entity);
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
		LoggingHandler.logger.log(Level.FINE, "Added entity to spawn queue: " + entity);
	}
	
	/**
	 * Flushes the entity buffer which holds entities that should be added and adds them to the level.
	 */
	public void flushEntityBuffer()
	{
		for(Entity e : this.newEntityBuffer)
		{
			EntityEvent spawnEvent = e.fireEvent(new EntityEvent(Entity.EVENT_ID_SPAWN, e));
			if(spawnEvent.isCancelled())
			{
				//Don't spawn entity if event is cancelled
				return;
			}
			this.entities.add(e);
			e.onSpawn(this);
			Handle<Entity> h = this.spatialHashGrid.add(e.getBoundingBox().offset(e.getX(), e.getY()), e);
			e.setSpationHashGridHandle(h);
		}
		this.newEntityBuffer.clear();
	}
	
	/**
	 * Whether the entity buffer is empty, i.e. whether any entities have been requested to spawn
	 * since the last flush.
	 * @return whether the entity buffer is empty
	 */
	private boolean isEntityBufferEmpty()
	{
		return this.newEntityBuffer.isEmpty();
	}
	
	/**
	 * @return the particle system
	 */
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
		return this.entities.size();
	}
	
	/**
	 * Returns the entity at the specified index in the entity list of this level.
	 * @param index index of entity 
	 * @return the entity at that index
	 */
	public Entity getEntity(int index)
	{
		return this.entities.get(index);
	}
	
	/**
	 * Removes all entities from the level.
	 */
	public void clearEntities()
	{
		while(!this.entities.isEmpty())
		{
			//Can use 0 index because we use swap with last anyway
			this.removeEntity(0); //Important because onDespawn and to remove from hash grid
		}
	}

	/**
	 * @return the <code>Random</code> instance of this level
	 */
	public Random getRNG()
	{
		return this.rng;
	}
	
	/**
	 * @return the spatial hash grid that this level uses
	 */
	public SpatialHashGrid<Entity> getSpatialHashGrid()
	{
		return this.spatialHashGrid;
	}
	
	/**
	 * Returns a list of all entities whose bounding boxes overlap with the specified area.
	 * @param area area that specifies which entities should be returned
	 * @return entities in the specified area
	 */
	public List<Entity> getEntitiesInArea(BoundingBox area)
	{
		List<Entity> entitiesInArea = new ArrayList<Entity>();
		Entity currentEntity;
		for(int i = 0; i < this.entities.size(); ++i)
		{
			currentEntity = this.entities.get(i);
			if(currentEntity.getBoundingBox().offset(currentEntity.getX(), currentEntity.getY()).intersects(area))
			{
				entitiesInArea.add(currentEntity);
			}
		}
		return entitiesInArea;
	}
	
	/**
	 * Returns a list of all entities in a radius around the specified coordinate.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param radius the radius around the specified coordinate
	 * @param exclude entities to exclude when they are inside the radius
	 * @return entities in the specified radius
	 */
	public List<Entity> getEntitiesInRadius(float x, float y, float radius, Entity... exclude)
	{
		return this.getEntitiesInRadius(x, y, radius, Entity.class, exclude);
	}
	
	/**
	 * Returns a list of all entities in a radius around the specified coordinate that are of
	 * the specified type.
	 * @param <T> the entity type that the returned list should have
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param radius the radius around the specified coordinate
	 * @param type the type that the entities must match
	 * @param exclude entities to exclude when they are inside the radius
	 * @return entities in the specified radius
	 */
	@SuppressWarnings("unchecked")
	public <T extends Entity> List<T> getEntitiesInRadius(float x, float y, float radius, Class<T> type, Entity... exclude)
	{
		List<T> entitiesInRadius = new ArrayList<>();
		Entity currentEntity;
		for(int i = 0; i < this.entities.size(); ++i)
		{
			currentEntity = this.entities.get(i);
			if(!type.isInstance(currentEntity))
			{
				continue;
			}
			if(Math.pow(currentEntity.getX() - x, 2) + Math.pow(currentEntity.getY() - y, 2) <= Math.pow(radius + currentEntity.getRadius(), 2)
				&& !Arrays.asList(exclude).contains(currentEntity))
			{
				entitiesInRadius.add((T)currentEntity);
			}
		}
		return entitiesInRadius;
	}
	
	/**
	 * Returns a list of all entities that are of the specified type.
	 * @param <T> the entity type that the returned list should have
	 * @param type the type that the entities must match
	 * @param exclude entities to exclude from the list
	 * @return all entities that are of the specified type
	 */
	public <T extends Entity> List<T> getAllEntitiesOf(Class<T> type, Entity... exclude)
	{
		return this.getEntitiesInRadius(0.0F, 0.0F, 9999F, type, exclude);
	}
}
