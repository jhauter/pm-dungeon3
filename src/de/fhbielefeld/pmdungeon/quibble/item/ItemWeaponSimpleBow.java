package de.fhbielefeld.pmdungeon.quibble.item;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.animation.AnimationFactory;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.ArrowProjectile;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

public class ItemWeaponSimpleBow extends ItemWeaponRanged
{
	protected ItemWeaponSimpleBow()
	{
		super("Simple Bow", 15, "assets/textures/items/bow.png");
		this.renderHeight = 1.75F;
		this.renderWidth = 1F;
		this.renderOffsetX = 0.5F;
	}
	
	@Override
	public CreatureStats getAttackStats()
	{
		CreatureStats stats = new CreatureStats();
		stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2);
		return stats;
	}
	
	@Override
	public Projectile spawnProjectile(Creature user)
	{
		Projectile arrow = new ArrowProjectile(this.getDisplayName() + " Projectile", user.getX(), user.getY() + 0.5F,
			user.getCurrentStats().addCopy(getAttackStats()), user);
		return arrow;
	}
	
	@Override
	public float getProjectileSpeed()
	{
		return 0.25F;
	}
	
	@Override
	public Animation<TextureRegion> loadAnimation()
	{
		return AnimationFactory.createAnimation("assets/textures/items/bow_anim.png", 4, 4, 0.01F);
	}
}
