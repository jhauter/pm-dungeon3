package de.fhbielefeld.pmdungeon.quibble.boss.bulletHell;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;


/**
 * Moves Projectilespawner back and forth given a lower and an upper bound limit
 */
public class BoundedMovePattern extends ProjectileMovementPattern {
    float lowerBounds;
    float upperBounds;
    float speed;
    float current;

    int state = 1;

    public BoundedMovePattern(ProjectileSpawner spawner, float lower, float upper, float speed) {
        super(spawner);
        this.lowerBounds = lower;
        this.upperBounds = upper;
        this.speed = speed;
        current = lower;
    }

    @Override
    void execute() {
        current += state * speed;

        if(current >= upperBounds) {
            state = -1;
        }
        else if(current <= lowerBounds) {
            state = 1;
        }
        this.spawner.offset = new Vector2(this.spawner.offset.x + current, spawner.offset.y);
    }

    @Override
    boolean isValid() {
        return false;
    }
}
