package de.fhbielefeld.pmdungeon.quibble.boss;

import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.ai.AIStrategy;

public abstract class BossAction {
    public int duration;
    public int cooldown;
    public boolean completed;

    public abstract void onActionBegin(BossBattle battle);
    public abstract void execute();
    public abstract void onActionEnd();
}
