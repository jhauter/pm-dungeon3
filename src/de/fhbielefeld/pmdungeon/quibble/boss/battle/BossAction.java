package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;

public abstract class BossAction {
    public int duration;
    public int cooldown;
    public boolean completed = false;

    public void onActionBegin(BossBattle battle) {
        this.completed = false;

    }
    public abstract void execute();
    public abstract void onActionEnd();
}
