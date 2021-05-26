package de.fhbielefeld.pmdungeon.quibble.entity.effect;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationFactory;
import de.fhbielefeld.pmdungeon.quibble.animation.SpriteSheets;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;

public class StatusEffectBurning extends StatusEffect {

	private Creature cause;

	/**
	 * A creature that is on fire will receive more damage the higher its magical
	 * abilities
	 * 
	 * @param creature that is set on fire
	 * @param cause    that will get the Exp
	 */
	public StatusEffectBurning(Creature creature, Creature cause) {
		super(creature);
		this.cause = cause;
	}

	/**
	 * Simple Damage Methode
	 */
	private void damging() {
		this.getCreature().damage(getCreature(), DamageType.MAGICAL, cause, true);
	}

	@Override
	public void renderStatusEffect(Batch batch, float x, float y) {

		Animation<TextureRegion> onFire = AnimationFactory.getAnimationFactory()
				.createAnimation(SpriteSheets.BURNING_CIRCLE);

		batch.draw(onFire.getKeyFrame(this.getStateTime() * 10F, true), x - this.getCreature().getBoundingBox().width * 36.0F, y, 72.0F, 72.0F);
	}

	@Override
	public void update() {
		super.update();

		//every 50 Ticks a creature will get damage
		if (getCreature().getTicks() % 50 == 0)
			damging();
	}
}
