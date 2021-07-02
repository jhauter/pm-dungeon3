package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.misc.CamRumbleEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class ExplosionGroundAOE extends GroundAoe{

    public ExplosionGroundAOE() {
        this.radius = 2;
        this.animationHandler.addAsDefaultAnimation("roam", 1,0.1f,1,1,
                "assets/textures/entity/boss_general/ground_danger.png");
        this.animationHandler.addAnimation("explosion",16, 0.1f, 4,4,
                "assets/textures/entity/boss_general/explosion2.png");
        this.renderScaleX = 3;
        this.renderScaleY = 3;
        this.ticksUntilAction = 60;
        this.ticksUntilRemove =  20;
        this.boundingBox.grow(2,2);
    }

    @Override
    protected void onRoam() {

    }


    @Override
    protected void onTrigger() {
        animationHandler.playAnimation("explosion", 99, false);
        CamRumbleEffect.shake(0.4f, 7f);
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

        var entitiesInReach = level.getEntitiesInRadius(this.getPosition().x, this.getPosition().y, radius);
        entitiesInReach.stream().filter(e -> e instanceof Player).findFirst().ifPresent(p -> {
            Player player = (Player) p;
            player.damage(this, DamageType.PHYSICAL,  null);
        });
    }

    public CreatureStats getCurrentStats() {
        var stats = new CreatureStats();
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, 2);
        stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 1);
        return stats;
    }
}
