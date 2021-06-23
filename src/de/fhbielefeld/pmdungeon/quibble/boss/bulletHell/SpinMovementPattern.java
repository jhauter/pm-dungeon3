package de.fhbielefeld.pmdungeon.quibble.boss.bulletHell;

public class SpinMovementPattern extends ProjectileMovementPattern {
    private int counter;

    public SpinMovementPattern(ProjectileSpawner spawner) {
        super(spawner);
    }

    @Override
    public void execute() {
        counter++;
        if(counter >= 361) {
            counter = 0;
        }
        this.spawner.setFacing(spawner.getAngle() + counter);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
