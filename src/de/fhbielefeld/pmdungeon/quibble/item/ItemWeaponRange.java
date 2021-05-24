package de.fhbielefeld.pmdungeon.quibble.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleMovement;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing;
import de.fhbielefeld.pmdungeon.quibble.particle.Swing.SwingOrientation;

public abstract class ItemWeaponRange extends ItemWeapon {

	/**
	 * The superclass of the range weapons (magic spells, arrows etc. . ).
	 * 
	 * @param name        user friendly display name
	 * @param itemWidth   render width of this weapon
	 * @param itemHeight  render height of this weapon
	 * @param swingSpeed  the speed at which this weapon will swing
	 * @param visibleTime time in seconds that this weapon will be visible when used
	 * @param texture     texture used to render this item
	 */
	protected ItemWeaponRange(String name, float itemWidth, float itemHeight, float visibleTime, String texture) {
		super(name, itemWidth, itemHeight, visibleTime, texture);
	}

	@Override
	public ParticleMovement getWeaponMovement(Creature user) {
		SwingOrientation swingDir = user.getLookingDirection() == LookingDirection.RIGHT ? SwingOrientation.RIGHT
				: SwingOrientation.LEFT;
		return new Swing(swingDir, 0.0F);
	}

	/**
	 * The projectile that should be spawn if the weapon is used
	 * 
	 * @param user user of the weapon
	 * @return the new projectile that should be spawn
	 */
	public abstract Projectile spawnProjectile(Creature user);
	
	public abstract float getProjectileSpeed();
	
	@Override
	public void onUse(Creature user) {
		super.onUse(user);
		if(user.getHitCooldown() > 0.0D)
		{
			return;
		}
		
		Projectile proj = spawnProjectile(user);
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

		float dungeonX = DrawingUtil.screenToDungeonXCam(mouseX, DungeonStart.getDungeonMain().getCamPosX());
		float dungeonY = DrawingUtil.screenToDungeonYCam(mouseY, DungeonStart.getDungeonMain().getCamPosY());
		
		Vector2 dir = new Vector2(dungeonX - user.getX(), dungeonY - user.getY());
		dir.setLength(this.getProjectileSpeed());
		proj.setVelocity(dir.x, dir.y);
		user.getLevel().spawnEntity(proj);
		
		user.getCurrentStats().setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15);
	}

}
