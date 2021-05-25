package de.fhbielefeld.pmdungeon.quibble.entity.projectile;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.animation.AnimationFactory;
import de.fhbielefeld.pmdungeon.quibble.animation.SpriteSheets;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;

public class ArrowProjectile extends ProjectilePhysical {

	/**
	 * Creates an arrow projectile.
	 * @param name text for log message. Has no other use.
	 * @param x the x position of the spawn point
	 * @param y the y position of the spawn point
	 * @param stats <code>CreatureStats</code> containing the damage attributes of the projectile
	 * @param owner owner of the projectile. May be <code>null</code>. Owner is invincible to projectile
	 * and gets exp when the projectile kills an entity
	 */
	public ArrowProjectile(String name, float x, float y, CreatureStats stats, Creature owner)
	{
		super(name, x, y, stats, owner);
	}

	@Override
	public int getTicksLasting()
	{
		return 1000;
	}

	@Override
	public float getDamageDecreaseOverTime()
	{
		return 0.0F;
	}

	@Override
	public Animation<TextureRegion> getProjectileAnimation()
	{
		return AnimationFactory.getAnimationFactory().createAnimation(SpriteSheets.ARROW);
	}
}
