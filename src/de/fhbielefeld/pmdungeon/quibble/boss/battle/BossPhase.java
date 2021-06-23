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
        index = 0;
    }

    public void switchAction() {
        durationCounter++;
        cooldownCounter++;

        if(this.index >= getActions().size()) {
            System.out.println("Re");
            this.index = 0;
            currentAction = getActions().get(index);
            currentAction.onActionBegin(battle);
            durationCounter = 0;
            cooldownCounter = 0;
        }

        if(!currentAction.completed && durationCounter >= currentAction.duration) {
            currentAction.completed = true;
            currentAction.onActionEnd();
            durationCounter = 0;
        }

        if(cooldownCounter >= currentAction.cooldown) {
                index++;
                if(this.getActions().size() > 1) {
                    currentAction = getActions().get(index);
                    currentAction.onActionBegin(battle);
                    cooldownCounter = 0;
                }
        }
    }
    public void run() {
        if(active) {
            switchAction();
            currentAction.execute();
        }
    }

    public void cleanStage() {
        var actions = getActions();

        for (var a : actions) {
            a.onActionEnd();
        }
    }

    protected abstract List<BossAction> getActions();
}
