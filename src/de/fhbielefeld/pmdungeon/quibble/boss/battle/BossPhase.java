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


    /**
     * Called once after switche to this phase
     * @param battle Reference to the battle
     */
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
            this.index = 0;
            currentAction = getActions().get(index);
            currentAction.onActionBegin(battle);
            durationCounter = 0;
            cooldownCounter = 0;
        }

        if(!currentAction.completed && durationCounter >= currentAction.duration) {
            currentAction.onActionEnd();
            currentAction.completed = true;
            durationCounter = 0;
        }
        if(cooldownCounter >= currentAction.cooldown) {
            index++;
            if(this.getActions().size() > 1) {
                try {
                    currentAction = getActions().get(index);
                    currentAction.onActionBegin(battle);
                    cooldownCounter = 0;
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }
    }
    /*
        Called each frame while BossPhase is active
     */
    public void run() {
        if(active) {
            currentAction.execute();
            switchAction();
        }
    }

    /*
        Called on end of fight or if switching to another phase
     */
    public void cleanStage() {
        var actions = getActions();

        for (var a : actions) {
            a.onActionEnd();
        }
    }

    protected abstract List<BossAction> getActions();
}
