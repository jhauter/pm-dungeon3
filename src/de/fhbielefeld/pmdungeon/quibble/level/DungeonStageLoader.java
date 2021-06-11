package de.fhbielefeld.pmdungeon.quibble.level;


import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.chest.GoldenChest;
import de.fhbielefeld.pmdungeon.quibble.entity.*;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestDummy;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestFactory;
import de.fhbielefeld.pmdungeon.quibble.trap.TrapDamage;
import de.fhbielefeld.pmdungeon.quibble.trap.TrapTeleport;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.DungeonConverter;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.LevelController;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;

public class DungeonStageLoader {
    enum StageType {
        Normal,
        Boss
    };

    private LevelController controller;

    private int dungeonProgressCounter = 1;
    private int bossStageRequirement = 5;
    private DungeonConverter converter;

    private Random rng;

    private StageType currentStageType = StageType.Normal;

    /**
     * Loader for new Dungeonstages
     * @param controller LevelController
     */
    public DungeonStageLoader(LevelController controller) {
        this.controller = controller;
        this.converter = new DungeonConverter();
        this.rng = new Random();
    }

    /**
     * Loads the new dungeon stage
     * NOTE: (Currently only loads a random stage, extend the function to load based on indices or names as well
     */
    public void loadNextStage() {
        loadRandomStage();
    }

    public void onStageLoad(DungeonLevel level) {
        /**** Populate dungeon ****/
        for(int i = 0; i < 5; ++i)
        {
            final Point pos = level.getDungeon().getRandomPointInDungeon();
            final Creature toSpawn = switch(level.getRNG().nextInt(4))
                    {
                        case 0 -> new Demon();
                        case 1 -> new Goblin();
                        case 2 -> new Lizard();
                        case 3 -> new Chort();
                        default -> throw new IllegalArgumentException("Unexpected value [spawn entity]");
                    };
            toSpawn.setPosition(pos.x, pos.y);
            level.spawnEntity(toSpawn);
        }

        /**************************/

        //Spawn the hero at the right spot
        Coordinate startingPoint = level.getDungeon().getStartingLocation();

        var hero = DungeonStart.getDungeonMain().getPlayer();
        hero.setPosition(startingPoint.getX(), startingPoint.getY());

        level.spawnEntity(hero);

        /**
         * Spawn Chests
         */
        final int num = level.getRNG().nextInt(1) + 1;
        for(int i = 0; i < num; i++)
        {
            final Point pos2 = level.getDungeon().getRandomPointInDungeon();
            level.spawnEntity(new GoldenChest(pos2.x, pos2.y));
            LoggingHandler.logger.log(Level.INFO, "New Chest added.");
        }

        // Placing a new Trap

        final Point pos3 = level.getDungeon().getRandomPointInDungeon();
        level.spawnEntity(level.getRNG().nextInt(2) == 0 ? new TrapTeleport(pos3.x, pos3.y) : new TrapDamage(pos3.x, pos3.y, 2));

        final Point pos4 = level.getDungeon().getRandomPointInDungeon();
        level.spawnEntity(new QuestDummy(QuestFactory.getRandomQuest(), pos4.x, pos4.y));

        //Set the camera to follow the hero
        LoggingHandler.logger.log(Level.INFO, "New level loaded.");
    }

    /**
     * @return Type of the currently loaded stage
     */
    public StageType getCurrentStageType () {
        return currentStageType;
    }

    private void loadRandomStage() {
        try {
            dungeonProgressCounter+=1;

            if(dungeonProgressCounter >= bossStageRequirement) {
                currentStageType = StageType.Boss;
                controller.loadDungeon(converter.dungeonFromJson(Constants.PATHTOLEVEL + "boss_dungeon.json"));
                dungeonProgressCounter = 1;
                return;
            }
            switch (rng.nextInt(2)) {
               case 0 -> controller.loadDungeon(converter.dungeonFromJson(Constants.PATHTOLEVEL + "small_dungeon.json"));
               case 1 -> controller.loadDungeon(converter.dungeonFromJson(Constants.PATHTOLEVEL + "simple_dungeon_2.json"));
               case 2 -> controller.loadDungeon(converter.dungeonFromJson(Constants.PATHTOLEVEL + "simple_dungeon.json"));
            }
            currentStageType = StageType.Normal;
        } catch (InvocationTargetException e) {
            //TODO: Add Error handling
        } catch (IllegalAccessException e) {
            //TODO: Add Error handling
        }
    }
}
