package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import java.util.List;

public abstract class BossPhase {
    protected BossBattle battle;


    private int index;
    public boolean active = false;

    private BossAction currentAction;
    private int durationCounter;
    private int cooldownCounter;

    public BossPhase() {

    }

    public void init(BossBattle battle) {
        this.battle = battle;
        currentAction = getActions().get(0);
        currentAction.onActionBegin(battle);
        active = true;
    }

    public void switchAction() {
        if(this.getActions().size() > 1) {
            durationCounter++;
            cooldownCounter++;

            if(durationCounter >= currentAction.duration) {
                currentAction.completed = true;
                currentAction.onActionEnd();
            }

            if(cooldownCounter >= currentAction.cooldown) {
                index++;
                currentAction = getActions().get(index);
                currentAction.onActionBegin(battle);
            }
        }
    }
    public void run() {
        switchAction();
        currentAction.execute();
    }

    public void cleanStage() {
        var actions = getActions();
        for (var a : actions) {
            a.onActionEnd();

        }
    }

    protected abstract List<BossAction> getActions();
}
