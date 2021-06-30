package de.fhbielefeld.pmdungeon.quibble.boss.slime;

import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;

public class FireSlimeAdd extends NPC {
    public FireSlimeAdd(float x, float y) {
        super(x,y);
    }
    @Override
    protected CreatureStats getBaseStatsForLevel(int level) {
        return null;
    }
}
