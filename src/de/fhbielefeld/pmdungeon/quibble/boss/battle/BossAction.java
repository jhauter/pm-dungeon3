package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;

public abstract class BossAction {

    /**
     * Amount of ticks that the BossAction is supposed to be executed
     */
    public int duration;

    /**
     * Amount of ticks until next action can be executed
     */
    public int cooldown;

    /**
     * Indicates whether the action has finised
     */
    public boolean completed = false;


    /** Called each time when switched to this action*
     * @param battle BossBattle instance
     */
    public void onActionBegin(BossBattle battle) {
        this.completed = false;

    }

    /**
     * Called each frame while action is executed
     */
    public abstract void execute();

    /**
     * Called when action is finished
     */
    public abstract void onActionEnd();
}
