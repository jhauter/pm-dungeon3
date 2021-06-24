package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;

public abstract class GroundAoe extends Entity implements DamageSource {
    protected int ticksUntilAction;
    protected int actionCounter = 0;
    protected int radius;

    public boolean shouldDespawn = false;

    protected abstract void onRoam();
    protected abstract void onTrigger();

    public GroundAoe(int radius) {
        this.radius = radius;
    }

    @Override
    protected void updateLogic() {
        super.updateLogic();
        actionCounter++;

        if(actionCounter >= ticksUntilAction) {
            onTrigger();
            actionCounter = 0;
            this.shouldDespawn = true;
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
