package de.fhbielefeld.pmdungeon.vorgaben.game.Controller;


import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.DungeonConverter;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Use this to control the level itself.
 */
public class LevelController {
    /**
     * Method to call after a new level is loaded
     */
    private final Method onLevelLoad;
    /**
     * Instance of the class that contains onLevelLoad (should be MainGameController)
     */
    private final Object klass;
    /**
     * Arguments for onLevelLoad
     */
    private final Object[] args;
    /**
     * the converter that generates the dungeon out of a json
     */
    private final DungeonConverter dungeonConverter = new DungeonConverter();
    /**
     * if this is true, the next level will get load
     */
    private boolean nextLevelTriggered = false;
    /**
     * the current level
     */
    private DungeonWorld dungeonWorld;
    /**
     * the next level
     */
    private Stage nextStage = Stage.D;


    /**
     * @param onLevelLoad Method that will be called if a new level get load
     * @param klass       Instance of the MainControllerClass
     * @param args        Arguments for onLevelLoaded
     */
    public LevelController(Method onLevelLoad, Object klass, Object[] args) {
        this.onLevelLoad = onLevelLoad;
        this.klass = klass;
        this.args = args;
    }

    /**
     * Load a new dungeon. Calls onLevelLoad
     *
     * @param dungeon DungeonWorld to load
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void loadDungeon(DungeonWorld dungeon) throws InvocationTargetException, IllegalAccessException {
        this.dungeonWorld = dungeon;
        this.nextLevelTriggered = false;
        this.dungeonWorld.makeConnections();
        onLevelLoad.invoke(klass, args);
    }

    /**
     * If next level is triggered, this will load it.
     * Also draws the level.
     */
    public void update() {
        //load next stage if triggered
        if (nextLevelTriggered) {
            try {
                nextStage();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        draw();
    }

    /**
     * Check if given Point is (rounded) a TriggerTile
     *
     * @param p point check
     * @return result of check
     */
    public boolean checkForTrigger(Point p) {
        return (int) p.x == dungeonWorld.getNextLevelTrigger().getX() && (int) p.y == dungeonWorld.getNextLevelTrigger().getY();
    }

    /**
     * Used to set the trigger if the next level should be loaded
     */
    public void triggerNextStage() {
        this.nextLevelTriggered = true;
    }

    /**
     * Return the current level.
     *
     * @return
     */
    public DungeonWorld getDungeon() {
        return dungeonWorld;
    }

    /**
     * Draws the dungeon itself.
     */
    public void draw() {
        if (dungeonWorld == null) return;
        dungeonWorld.renderFloor(GameSetup.batch);
        dungeonWorld.renderWalls(dungeonWorld.getHeight() - 1, 0, GameSetup.batch);
    }
    //Switch dungeon.

    /**
     * If next stage is triggered, change the dungeon.
     */
    private void nextStage() throws InvocationTargetException, IllegalAccessException {

        /*
        switch (nextStage) {
            case A:
                loadDungeon(dungeonConverter.dungeonFromJson(Constants.PATHTOLEVEL + "small_dungeon.json"));
                nextStage = Stage.B;
                break;
            case B:
                loadDungeon(dungeonConverter.dungeonFromJson(Constants.PATHTOLEVEL + "simple_dungeon_2.json"));
                nextStage = Stage.C;
                break;
            case C:
                loadDungeon(dungeonConverter.dungeonFromJson(Constants.PATHTOLEVEL + "simple_dungeon.json"));
                nextStage = Stage.D;
                break;
            case D:
                loadDungeon(dungeonConverter.dungeonFromJson(Constants.PATHTOLEVEL + "boss_dungeon.json"));
                nextStage = Stage.A;
                break;
        }
        */
        DungeonStart.getDungeonMain().getStageLoader().loadNextStage();
    }

    /**
     * used to manage nextStage()
     */
    enum Stage {
        A,
        B,
        C,
        D
    }
}
