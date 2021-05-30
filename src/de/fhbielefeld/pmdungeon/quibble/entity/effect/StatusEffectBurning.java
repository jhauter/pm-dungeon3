package de.fhbielefeld.pmdungeon.quibble.entity.effect;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.animation.AnimationFactory;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;

public class StatusEffectBurning extends StatusEffect implements DamageSource
{
	private static final Animation<TextureRegion> anim;
	static
	{
		anim = AnimationFactory.createAnimation("assets/textures/particle/burningCircle.png", 8, 8, 0.01F);
	}
	
	/**
	 * Creates a burning status effect that inflicts damage over time.
	 * @param creature the creature that is on fire
	 * @param cause the attacker
	 */
	public StatusEffectBurning(Creature creature, Creature cause)
	{
		super(creature, cause);
	}
	
	/**
	 * Creates a burning status effect that inflicts damage over time.
	 * @param creature the creature that is on fire
	 */
	public StatusEffectBurning(Creature creature)
	{
		super(creature);
	}
	
	@Override
	public void renderStatusEffect()
	{
		GameSetup.batch.draw(anim.getKeyFrame(getStateTime(), true),
			getCreature().getX() + getCreature().getRenderOffsetX() - getCreature().getRenderWidth() * 0.5F,
			getCreature().getY() + getCreature().getRenderOffsetY() - getCreature().getRenderHeight() * 0.5F,
			getCreature().getRenderPivotX(),
			getCreature().getRenderPivotY(),
			getCreature().getRenderWidth(),
			getCreature().getRenderHeight(),
			getCreature().getScaleX(),
			getCreature().getScaleY(),
			getCreature().getRotation());
	}
	
	@Override
	public void update()
	{
		super.update();
		
		//every 50 Ticks a creature will get damage
		if(getCreature().getTicks() % 60 == 0)
		{
			this.getCreature().damage(this, DamageType.MAGICAL, this.getCause(), true, false);
		}
	}
	
	@Override
	public float getX()
	{
		return 0;
	}
	
	@Override
	public float getY()
	{
		return 0;
	}
	
	@Override
	public CreatureStats getCurrentStats()
	{
		CreatureStats c = new CreatureStats();
		c.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1.0D);
		return c;
	}
}
