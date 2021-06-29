package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BossPhase {
    protected BossBattle battle;


    private int index;
    public boolean active = false;

    private List <BossAction> currentActions;
    public BossPhase() {
    }


    /**
     * Called once after switche to this phase
     * @param battle Reference to the battle
     */
    public void init(BossBattle battle) {
        this.battle = battle;
        currentActions = new ArrayList<>();
        index = 0;
        var action = getActions().get(0);
        action.onActionBegin(battle);
        currentActions.add(action);
        this.active = true;
    }

    public void nextAction() {
        int maxActions = getActions().size();
        System.out.println(index);

        if(index + 1 >= maxActions) {
            index = 0;
        } else {
            index++;
        }
        var newAction = getActions().get(index);
        if(!currentActions.contains(newAction)) {
            currentActions.add(newAction);
            newAction.onActionBegin(battle);
        }

    }


    public void removeAction(BossAction action) {
        currentActions.remove(action);
    }

    public void switchAction() {
        /*
        int maxActions = getActions().size();
        int index = 0;
        BossAction currentAction = getActions().get(index);

        if(this.index >= maxActions) {
            this.index = 0;
        }
        if(!currentAction.completed && currentAc)

        durationCounter++;
        cooldownCounter++;
        System.out.println(currentActions.toString());
        if(this.index >= getActions().size() || currentActions.isEmpty()) {
            this.index = 0;
            var a= getActions().get(index);
            durationCounter = 0;

            if(!currentActions.contains(a)) {
                currentActions.add(a);
                a.onActionBegin(battle);
            }
        }

        var iter = currentActions.listIterator();
        while (iter.hasNext()) {
            var a = iter.next();

            if(cooldownCounter % a.cooldown == 0) {
                index++;
                if(this.getActions().size() > 1) {
                    try {
                        var newAction = getActions().get(index);
                        if(!currentActions.contains(newAction)) {
                            iter.add(newAction);
                            newAction.onActionBegin(battle);
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            }
            if(!a.completed && durationCounter >= a.duration) {
                a.onActionEnd();
                a.completed = true;
                iter.remove();
            }
        }
         */
    }
    /*
        Called each frame while BossPhase is active
     */
    public void run() {
        if(active) {
            if(currentActions.isEmpty()) {
                var newAction = getActions().get(0);
                newAction.onActionBegin(battle);
                currentActions.add(newAction);
            }

            for(int i = 0; i<currentActions.size(); ++i) {
                currentActions.get(i).execute();
            }
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
