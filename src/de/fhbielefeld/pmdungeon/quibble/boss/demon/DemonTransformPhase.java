package de.fhbielefeld.pmdungeon.quibble.boss.demon;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.boss.misc.CamRumbleEffect;
import de.fhbielefeld.pmdungeon.quibble.boss.slime.FireballProjectile;
import de.fhbielefeld.pmdungeon.quibble.boss.slime.FireballProjectileLarge;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

import java.util.ArrayList;
import java.util.List;

public class DemonTransformPhase extends BossPhase {
    private ArrayList<BossAction> actions = new ArrayList<>();

    public DemonTransformPhase(BossBattle battle) {
        super(battle);
        var fireball = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new FireballProjectile("def", 0, 0, new CreatureStats(), battle.getBoss());
            }
        };

        var fireball2 = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new FireballProjectileLarge("def", 0, 0, new CreatureStats(), battle.getBoss());
            }
        };
        List<ProjectileSpawner> projSpawner = new ArrayList<>();
        for(int i = 0; i<5; ++i) {
            var face = i * 45;
            var proj = new ProjectileSpawner(20, new CreatureStats(), new Vector2((float) Math.pow(-1, i) * 2,(float) Math.pow(-1, i) * 2), fireball, battle.getBoss());
            proj.addPattern(new SpinMovementPattern(proj, 250));
            proj.currentBulletSpeed = 0.07f;
            proj.setFacing(face);
            projSpawner.add(proj);
        }

        for(int i = 0; i<5; ++i) {
            var face = i * 90;
            var proj = new ProjectileSpawner(20, new CreatureStats(), new Vector2((float) Math.pow(-1, i+1) * -2,(float) Math.pow(-1, i+1) * -2), fireball2, battle.getBoss());
            proj.addPattern(new SpinMovementPattern(proj, 250));
            proj.currentBulletSpeed = 0.07f;
            proj.setFacing(face);
            projSpawner.add(proj);
        }

        for(int i = 0; i<3; ++i) {
            var face = i * 45;
            var proj = new ProjectileSpawner(20, new CreatureStats(), new Vector2((float) Math.pow(-1, i+1) * 4,(float) Math.pow(-1, i+1) * -4), fireball, battle.getBoss());
            proj.addPattern(new SpinMovementPattern(proj, 250));
            proj.currentBulletSpeed = 0.08f;
            proj.setFacing(face);
            projSpawner.add(proj);
        }

        var knockback = new KnockbackGroundAOE();
        knockback.setStrength(0.1f);
        var knockbackAction = new GroundEffectBossAction(knockback, 1, new Vector2(0,0));
        var projAction = new ProjectileBossAction(projSpawner, 60, 50);
        var waitAction = new WaitAction(200, 200);

        actions.add(knockbackAction);
        actions.add(projAction);
        actions.add(waitAction);
    }
    public void init() {
        super.init();
        battle.getBoss().playAttackAnimation("transform", false, 14);

    }

    @Override
    public void run() {
        super.run();

        CamRumbleEffect.shake(0.1f, 9999f);
        var cam = DungeonStart.getDungeonMain().getCamera();
        var fpos = DungeonStart.getDungeonMain().getPlayer().getPosition();

        if(CamRumbleEffect.getTimeLeft() > 0) {

            var rumble = CamRumbleEffect.update();
            cam.setFocusPoint(new Point(fpos.x + rumble.x, fpos.y + rumble.y));
        } else {
            DungeonStart.getDungeonMain().setCameraTarget(DungeonStart.getDungeonMain().getPlayer());
        }

    }
    @Override
    protected List<BossAction> getActions() {
        return actions;
    }
}
