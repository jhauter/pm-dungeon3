package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.misc.CamRumbleEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectKnockback;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

/**
 * Groundaoe that knocks the player back when touched
 */
public class KnockbackGroundAOE extends GroundAoe implements DamageSource {
    private DungeonLevel level;
    private float strength;

    public KnockbackGroundAOE() {
        super();
        this.radius = 2;
        this.strength = 0.6f;
        this.animationHandler.addAsDefaultAnimation("slam",
                4, 0.1f, 1, 4, "assets/textures/entity/golem/groundAOE.png");
        this.animationHandler.addAnimation("exp",8, 0.1f, 1, 8, "assets/textures/entity/boss_general/explosion.png");
        this.ticksUntilAction = 10;
        this.ticksUntilRemove = 17;
        this.renderScaleX = 3;
        this.renderScaleY = 3;
    }
    public void setStrength(float strength) {
        this.strength = strength;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    @Override
    protected void onRoam() {
        CamRumbleEffect.shake(strength, 5f);
        var cam = DungeonStart.getDungeonMain().getCamera();
        //Let's just assert that we'll always follow the player
        //var fpos= cam.getFollowedObject().getPosition();
        var fpos = DungeonStart.getDungeonMain().getPlayer().getPosition();
        if(CamRumbleEffect.getTimeLeft() > 0) {

            var rumble = CamRumbleEffect.update();
            cam.setFocusPoint(new Point(fpos.x + rumble.x, fpos.y + rumble.y));
        } else {
            DungeonStart.getDungeonMain().setCameraTarget(DungeonStart.getDungeonMain().getPlayer());
        }
    }

    @Override
    protected void onTrigger() {
        animationHandler.playAnimation("exp", 10, false);
        var entitiesInReach = level.getEntitiesInRadius(this.getPosition().x, this.getPosition().y, radius);
        entitiesInReach.stream().filter(e -> e instanceof Player).findFirst().ifPresent(p -> {
            Player player = (Player) p;
            player.damage(this, DamageType.PHYSICAL,  null);
        });
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        super.onSpawn(level);
        this.level = level;
    }
    @Override
    public CreatureStats getCurrentStats() {
        var stats = new CreatureStats();
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, strength * 3);
        return stats;
    }
}