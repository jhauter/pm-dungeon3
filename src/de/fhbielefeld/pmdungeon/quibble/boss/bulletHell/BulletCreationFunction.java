package de.fhbielefeld.pmdungeon.quibble.boss.bulletHell;

import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

@FunctionalInterface
public interface BulletCreationFunction
{
	Projectile createProjectile();
}
