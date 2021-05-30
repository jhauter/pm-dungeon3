package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;

import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

import java.util.ArrayList;


/**
 * Handles every entity in the dungeon.
 */
public class EntityController {

    /**
     * Contains all the entities this controller handles.
     */
    private final ArrayList<IEntity> dungeonEntities;

    public EntityController() {
        this.dungeonEntities = new ArrayList<>();
    }

    /**
     * calls the update method for every entity in the list.
     * removes entity if deletable is set true
     */
    public void update() {
        dungeonEntities.removeIf(obj -> obj.deleteable());
        dungeonEntities.forEach(obj -> obj.update());
    }

    /**
     * add an entity to the list
     *
     * @param entity
     */
    public void addEntity(IEntity entity) {
        if (!dungeonEntities.contains(entity))
            this.dungeonEntities.add(entity);
    }

    /**
     * removes entity from the list
     *
     * @param entity
     */
    public void removeEntity(IEntity entity) {
        if (dungeonEntities.contains(entity))
            this.dungeonEntities.remove(entity);
    }

    /**
     * removes all entities from the list
     */
    public void removeAll() {
        this.dungeonEntities.clear();
    }

    /**
     * removes all instances of the class c from the list
     *
     * @param c referenz Class (use Class.forName("PACKAGE.CLASSNAME") )
     */
    public void removeAllFrom(Class<?> c) {
        dungeonEntities.removeIf(obj -> c.isInstance(obj));
    }

    /**
     * returns entity list
     */
    public ArrayList<IEntity> getList() {
        return this.dungeonEntities;
    }
}
