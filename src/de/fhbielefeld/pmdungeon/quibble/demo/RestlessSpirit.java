package de.fhbielefeld.pmdungeon.quibble.demo;

import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class RestlessSpirit extends NPC {

    public RestlessSpirit(float x, float y) {
        super(x,y);
        this.animationHandler.addAsDefaultAnimation("idle", 7, 0.1f, 1,7,
                "assets/textures/entity/demo/ghost-idle.png");

    }

    public RestlessSpirit() {
        this(0f, 0f);
    }

    public String getDisplayName() {
        return "Dungeon Spirit";
    }

    @Override
    protected CreatureStats getBaseStatsForLevel(int level) {
        CreatureStats stats = new CreatureStats();
        return stats;
    }

}
