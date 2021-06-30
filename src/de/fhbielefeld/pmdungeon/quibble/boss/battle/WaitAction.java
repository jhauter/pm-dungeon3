package de.fhbielefeld.pmdungeon.quibble.boss.battle;

public class WaitAction extends BossAction{

    public WaitAction(int duration, int cooldown) {
        this.duration = duration;
        this.cooldown = cooldown;
    }
    @Override
    public void onActionEnd() {
        System.out.println("WaitEnd");
    }
}
