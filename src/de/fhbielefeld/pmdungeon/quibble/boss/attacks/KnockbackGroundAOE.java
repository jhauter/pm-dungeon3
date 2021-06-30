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

public class KnockbackGroundAOE extends GroundAoe implements DamageSource {
    private DungeonLevel level;

    public KnockbackGroundAOE() {
        super();
        this.radius = 2;
        this.animationHandler.addAsDefaultAnimation("slam",
                4, 0.1f, 1, 4, "assets/textures/entity/golem/groundAOE.png");
        this.ticksUntilAction = 10;
        this.renderScaleX = 3;
        this.renderScaleY = 3;

    }
    @Override
    protected void onRoam() {
        CamRumbleEffect.shake(0.6f, 5f);
        System.out.println(CamRumbleEffect.getTimeLeft());
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
        var entitiesInReach = level.getEntitiesInRadius(this.getPosition().x, this.getPosition().y, radius);
        entitiesInReach.stream().filter(e -> e instanceof Player).findFirst().ifPresent(p -> {
            Player player = (Player) p;
            player.damage(this, DamageType.PHYSICAL,  null);
        });
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        System.out.println("Knockback spawn");
        super.onSpawn(level);
        this.level = level;
    }
    @Override
    public CreatureStats getCurrentStats() {
        var stats = new CreatureStats();
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, 2);
        return stats;
    }
}