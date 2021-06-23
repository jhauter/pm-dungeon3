package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;

public abstract class GroundAoe extends Entity implements DamageSource {
    protected int ticksUntilAction;
    protected int actionCounter;

    private boolean shouldDespawn;

    protected abstract void onRoam();
    protected abstract void onTrigger();

    public GroundAoe() {
    }

    @Override
    protected void updateLogic() {
        super.updateLogic();
        actionCounter++;

        if(actionCounter >= ticksUntilAction) {
            onTrigger();
        } else {
            onRoam();
        }
    }

    @Override
    public CreatureStats getCurrentStats() {
        return null;
    }

    @Override
    public boolean shouldDespawn() {
        return this.shouldDespawn;
    }

}
