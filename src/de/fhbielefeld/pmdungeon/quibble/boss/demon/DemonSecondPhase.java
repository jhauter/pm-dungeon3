package de.fhbielefeld.pmdungeon.quibble.boss.demon;


import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.ExplosionGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.SpawnGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.boss.slime.FireSlimeAdd;
import de.fhbielefeld.pmdungeon.quibble.boss.slime.FireballProjectile;
import de.fhbielefeld.pmdungeon.quibble.boss.slime.SlimeProjectile;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.List;

public class DemonSecondPhase extends BossPhase {
    public ArrayList<BossAction> actions = new ArrayList<>();

    public DemonSecondPhase(BossBattle battle) {
        super(battle);
        var bullet = new BulletCreationFunction() {

            @Override
            public Projectile createProjectile() {
                var projectileStats = new CreatureStats();
                projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 4);
                return new SlimeProjectile("def", 0, 0, projectileStats, battle.getBoss());
            }
        };

        var fireball = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                var projectileStats = new CreatureStats();
                projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 2);
                return new FireballProjectile("def", 0, 0,projectileStats , battle.getBoss());
            }
        };

        List<ProjectileSpawner> projSpawner1 = new ArrayList<>();
        for(int i = 0; i<6; ++i) {
            var face = i * 40;
            var proj = new ProjectileSpawner(70, new CreatureStats(), new Vector2(0,0), bullet, battle.getBoss());
            var proj2 = new ProjectileSpawner(70, new CreatureStats(), new Vector2(0,0), bullet, battle.getBoss());
            proj2.addPattern(new SpinMovementPattern(proj2, 40));
            proj.addPattern(new SpinMovementPattern(proj, 40));
            proj.currentBulletSpeed = 0.06f;
            proj2.currentBulletSpeed = 0.05f;
            proj.setFacing(face);
            proj2.setFacing(face+i*5);
            projSpawner1.add(proj);
            projSpawner1.add(proj2);
        }
        var projAction1 = new ProjectileBossAction(projSpawner1, 100, 80);

        List<ProjectileSpawner> projSpawner2 = new ArrayList<>();
        for(int i = 0; i<3; ++i) {
            var face = i * 100;
            var proj = new ProjectileSpawner(60, new CreatureStats(), new Vector2((float) Math.pow(-1, i+1) * -2,(float) Math.pow(-1, i+1) * -2), bullet, battle.getBoss());
            var proj2 = new ProjectileSpawner(60, new CreatureStats(), new Vector2((float) Math.pow(-1, i+1) * -2,(float) Math.pow(-1, i+1) * -2), fireball, battle.getBoss());
            proj2.addPattern(new SpinMovementPattern(proj2, 40));
            proj.addPattern(new SpinMovementPattern(proj, 40));
            proj.currentBulletSpeed = 0.04f;
            proj2.currentBulletSpeed = 0.04f;
            proj.setFacing(face+i*5);
            projSpawner2.add(proj);
            projSpawner2.add(proj2);
        }
        var projAction2 = new ProjectileBossAction(projSpawner2, 100, 99);

        List<ProjectileSpawner> projSpawner3 = new ArrayList<>();
        for(int i = 0; i<3; ++i) {
            var face = i * 60;
            var proj = new ProjectileSpawner(60, new CreatureStats(), new Vector2((float) Math.pow(-1, i+1) * 4,(float) Math.pow(-1, i+1) * -4), bullet, battle.getBoss());
            proj.addPattern(new SpinMovementPattern(proj, 100));
            proj.currentBulletSpeed = 0.04f;
            proj.setFacing(face);
            projSpawner3.add(proj);
        }
        var projAction3 = new ProjectileBossAction(projSpawner3, 100, 99);

        var testExplosionAction = new GroundEffectBossAction(new ExplosionGroundAOE(), 5,2, new Vector2(0,0), DungeonStart.getDungeonMain().getPlayer());
        var testExplosionAction2 = new GroundEffectBossAction(new ExplosionGroundAOE(), 5,2, new Vector2(2,0), DungeonStart.getDungeonMain().getPlayer());
        var testExplosionAction3 = new GroundEffectBossAction(new ExplosionGroundAOE(), 5,2, new Vector2(0,2), DungeonStart.getDungeonMain().getPlayer());
        var testExplosionAction4 = new GroundEffectBossAction(new ExplosionGroundAOE(), 5, 2,new Vector2(-2,0), DungeonStart.getDungeonMain().getPlayer());

        actions.add(testExplosionAction);
        actions.add(testExplosionAction2);
        actions.add(testExplosionAction3);
        actions.add(testExplosionAction4);

        actions.add(new WaitAction(60,50));
        actions.add(projAction1);
        actions.add(new WaitAction(60,50));
        actions.add(projAction2);
        actions.add(new WaitAction(60,50));
        actions.add(projAction3);
        actions.add(new WaitAction(60,50));

        var spawnGround = new SpawnGroundAOE();
        spawnGround.setTarget(new FireSlimeAdd());
        var spawnAction = new GroundEffectBossAction(spawnGround, 1, new Vector2(0,0));
        var knockbackAction = new GroundEffectBossAction(new KnockbackGroundAOE(), 3, new Vector2(0, 0));
        actions.add(spawnAction);
        actions.add(new WaitAction(100,70));
        actions.add(knockbackAction);
        actions.add(new WaitAction(120,100));

    }

    public void run() {
        super.run();
    }
    @Override
    protected List<BossAction> getActions() {
        return actions;
    }
}
