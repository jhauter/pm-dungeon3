package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.misc.CamRumbleEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class ExplosionFollowGroundAOE extends GroundAoe{
    private final int ticksUntilStop;
    private int counter = 0;
    private Entity target;

    public ExplosionFollowGroundAOE() {
        this.radius = 2;
        this.animationHandler.addAsDefaultAnimation("roam", 1,0.1f,1,1,
                "assets/textures/entity/boss_general/ground_danger2.png");
        this.animationHandler.addAnimation("explosion",45, 0.1f, 1,45,
                "assets/textures/entity/boss_general/explosion3.png");

        this.renderScaleX = 3;
        this.renderScaleY = 3;
        this.ticksUntilAction = 70;
        this.ticksUntilStop = 40;
        this.ticksUntilRemove =  30;
    }
    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    public Entity getTarget() {
        return target;
    }

    @Override
    protected void onRoam() {
        System.out.println(this.target);
        counter++;
        if(counter <= ticksUntilStop) {
            this.setPosition(this.target.getPosition());
        }
    }

    @Override
    protected void onTrigger() {
        this.renderScaleX = 6;
        this.renderScaleY = 6;
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

    @Override
    public CreatureStats getCurrentStats() {
        var stats = new CreatureStats();
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, 1);
        stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2);
        return stats;
    }
}
