package de.fhbielefeld.pmdungeon.quibble.boss;

public abstract class ProjectileMovementPattern {
    protected ProjectileSpawner spawner;


    public ProjectileMovementPattern(ProjectileSpawner spawner) {
        this.spawner = spawner;
    }

    abstract void execute();
    abstract boolean isValid();
}
