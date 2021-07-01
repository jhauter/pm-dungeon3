package de.fhbielefeld.pmdungeon.quibble.level;


import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.boss.*;
import de.fhbielefeld.pmdungeon.quibble.boss.golem.GolemBossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.slime.SlimeBossBattle;
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
        Golem,
        Slime
    };

    private LevelController controller;

    private int dungeonProgressCounter = 1;
    private int golemStageRequirement = 5;
    private int slimeStageRequirement = 10;

    private int currentStageNum = 1;

    private DungeonConverter converter;
    private Random rng;

    private StageType currentStageType = StageType.Normal;

    /**
     * Custom loader for DungeonStages
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

    private void placeDungeonEnemies(DungeonLevel level) {
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
    }

    private void placeHero(DungeonLevel level) {
        Coordinate startingPoint = level.getDungeon().getStartingLocation();

        var hero = DungeonStart.getDungeonMain().getPlayer();
        hero.setPosition(startingPoint.getX(), startingPoint.getY());

        level.spawnEntity(hero);

    }

    private void placeMiscEntities(DungeonLevel level) {
        final int num = level.getRNG().nextInt(1) + 1;
        for(int i = 0; i < num; i++)
        {
            final Point pos2 = level.getDungeon().getRandomPointInDungeon();
            level.spawnEntity(new GoldenChest(pos2.x, pos2.y));
            LoggingHandler.logger.log(Level.INFO, "New Chest added.");
        }

        //Place Traps
        final Point pos3 = level.getDungeon().getRandomPointInDungeon();
        level.spawnEntity(level.getRNG().nextInt(2) == 0 ? new TrapTeleport(pos3.x, pos3.y) : new TrapDamage(pos3.x, pos3.y, 2));

        final Point pos4 = level.getDungeon().getRandomPointInDungeon();
        level.spawnEntity(new QuestDummy(QuestFactory.getRandomQuest(), pos4.x, pos4.y));

        //NOTE(Jonathan) Right now this is only for testing purposes and assumes that we will be using the default
        // "boss-map" which we won't!
        if(getCurrentStageType() == StageType.Golem) {
            Trigger t = new Trigger(new GolemBossBattle(level));
            level.spawnEntity(t);
        }
        else if(getCurrentStageType() == StageType.Slime) {
            Trigger t = new Trigger(new SlimeBossBattle(level));
            level.spawnEntity(t);
        }
    }

    public void onStageLoad(DungeonLevel level) {
        placeDungeonEnemies(level);
        placeHero(level);
        placeMiscEntities(level);

        LoggingHandler.logger.log(Level.INFO, "New level loaded.");
    }

    /**
     * @return Type of the currently loaded stage
     */
    public StageType getCurrentStageType() {
        return currentStageType;
    }

    private void loadRandomStage() {
        try {
            dungeonProgressCounter+=1;
//            System.out.println(dungeonProgressCounter);

            if(dungeonProgressCounter == golemStageRequirement) {
                currentStageType = StageType.Golem;
                controller.loadDungeon(converter.dungeonFromJson(Constants.PATHTOLEVEL + "boss_dungeon.json"));
            } else if(dungeonProgressCounter == slimeStageRequirement) {
                currentStageType = StageType.Slime;
                controller.loadDungeon(converter.dungeonFromJson(Constants.PATHTOLEVEL + "boss_dungeon.json"));
            } else {
                switch (rng.nextInt(2)) {
                    case 0 -> controller.loadDungeon(converter.dungeonFromJson(Constants.PATHTOLEVEL + "small_dungeon.json"));
                    case 1 -> controller.loadDungeon(converter.dungeonFromJson(Constants.PATHTOLEVEL + "simple_dungeon_2.json"));
                    case 2 -> controller.loadDungeon(converter.dungeonFromJson(Constants.PATHTOLEVEL + "simple_dungeon.json"));
                }
                currentStageType = StageType.Normal;
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
