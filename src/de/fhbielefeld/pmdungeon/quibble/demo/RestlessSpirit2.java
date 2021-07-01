package de.fhbielefeld.pmdungeon.quibble.demo;

import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class RestlessSpirit2 extends NPC {

    public RestlessSpirit2(float x, float y) {
        super(x,y);
        this.animationHandler.addAsDefaultAnimation("idle", 7, 0.1f, 1,7,
                "assets/textures/entity/demo/ghost-idle.png");

        this.renderHeight = 3f;
        this.renderWidth = 3f;
    }

    public RestlessSpirit2() {
        this(0f, 0f);
    }

    public String getDisplayName() {
        return "Dungeon Spirit";
    }

    @Override
    protected CreatureStats getBaseStatsForLevel(int level) {

        CreatureStats stats = new CreatureStats();

        stats.setStat(CreatureStatsAttribs.HEALTH, 6 + level);
        stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.03);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);

        return stats;
    }

    @Override
    protected BoundingBox getInitBoundingBox() {
        return new BoundingBox(-0.55f,0,0.8f,1);
    }
}
