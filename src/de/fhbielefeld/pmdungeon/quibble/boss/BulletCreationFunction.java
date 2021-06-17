package de.fhbielefeld.pmdungeon.quibble.boss;

import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

@FunctionalInterface
public interface BulletCreationFunction {
    Projectile createProjectile();
}
