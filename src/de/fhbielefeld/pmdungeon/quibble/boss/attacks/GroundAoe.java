package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;

public abstract class GroundAoe extends Entity implements DamageSource {
    protected int ticksUntilAction;
    protected int ticksUntilRemove = 20;

    protected boolean finished = false;

    protected int actionCounter = 0;
    protected int radius = 1;

    public boolean shouldDespawn = false;
    protected Entity target = null;

    //TODO: I hate my life
    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    protected abstract void onRoam();
    protected abstract void onTrigger();

    @Override
    protected void updateLogic() {
        super.updateLogic();
        actionCounter++;
        if(finished) {
            if(actionCounter >= ticksUntilRemove) {
                this.shouldDespawn = true;
            } else {
                return;
            }
        }
        if(actionCounter >= ticksUntilAction) {
            onTrigger();
            actionCounter = 0;
            this.finished = true;
        } else {
            onRoam();
        }
    }

    @Override
    public CreatureStats getCurrentStats() {
        //?
        return new CreatureStats();
    }

    @Override
    public boolean shouldDespawn() {
        return this.shouldDespawn;
    }

}
