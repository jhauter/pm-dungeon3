package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.entity.*;

import java.util.Random;

public class SpawnGroundAOE extends GroundAoe{
    private DungeonLevel level;

    private int spawnCD = 15;
    private int spawnCDCounter = 0;

    private boolean activated = false;
    private Random rand = new Random();

    public SpawnGroundAOE(int radius) {
        super(radius);

        animationHandler.addAsDefaultAnimation("idle", 1, 0.3f, 1, 5,
                "assets/textures/entity/golem/ground_fire.png");
        animationHandler.addAnimation("spawn", 5, 0.3f, 1, 5,
                "assets/textures/entity/golem/ground_fire.png");

        this.ticksUntilAction = 30;
    }

    @Override
    protected void onRoam() {
        spawnCDCounter++;
        if(spawnCDCounter >= spawnCD && !activated) {
            animationHandler.playAnimation("spawn", 1, false);
            activated = true;
            spawnCDCounter = 0;
        }
    }

    @Override
    protected void onTrigger() {
        var r = rand.nextInt(2);
        NPC enemy = null;
        switch (r) {
            case 0 -> enemy = new Goblin();
            case 1 -> enemy = new Chort();
            case 2 -> enemy = new Lizard();
        }

        System.out.println("Enemy Spawn");
        level.spawnEntity(enemy);
        enemy.setPosition(this.getPosition());

        spawnCDCounter = 0;
        activated = false;
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        this.level = level;
        super.onSpawn(level);
    }
}
