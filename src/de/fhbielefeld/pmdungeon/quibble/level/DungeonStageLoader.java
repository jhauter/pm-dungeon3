package de.fhbielefeld.pmdungeon.quibble.level;


import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.DungeonConverter;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.LevelController;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

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
