package de.fhbielefeld.pmdungeon.quibble.boss;

public class SpinBoundsPattern extends SpinMovementPattern{
    float lowerBounds;
    float upperBounds;
    float speed;
    float current;

    int state = 1;
    public SpinBoundsPattern(ProjectileSpawner spawner, float lower, float upperBounds, float speed) {
        super(spawner);
        this.lowerBounds = lower;
        this.upperBounds = upperBounds;
        this.speed = speed;
        current = lowerBounds;
    }

    @Override
    public void execute() {
        current += state * speed;

        if(current >= upperBounds) {
            state = -1;
        }
        else if(current <= lowerBounds) {
            state = 1;
        }
        this.spawner.setFacing(current);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
