package de.fhbielefeld.pmdungeon.quibble.demo;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.ai.AIStrategy;

public class GhostStrategy1 implements AIStrategy {
    private int lightDuration = 60;
    private int lightCooldown = 60;

    private int durationCounter = 0;
    private int cooldownCounter = 0;

    @Override
    public void executeStrategy(NPC entity) {
        cooldownCounter++;
        if(cooldownCounter >= lightCooldown) {
            durationCounter++;
            if(durationCounter < lightDuration) {
                System.out.println("Lightup");
                showLight(entity, (float)durationCounter / 60);
            } else {
                cooldownCounter = 0;
                durationCounter = 0;
            }
        }
    }

    private void showLight(NPC entity, float intensity) {
        entity.getLevel().getFogOfWarController()
                .light(entity.getX(), entity.getY(), intensity);
    }
}
