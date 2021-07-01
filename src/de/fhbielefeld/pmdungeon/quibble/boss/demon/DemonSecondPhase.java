package de.fhbielefeld.pmdungeon.quibble.boss.demon;


import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.SpawnGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.boss.slime.FireSlimeAdd;
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

        List<ProjectileSpawner> projSpawner1 = new ArrayList<>();
        for(int i = 0; i<8; ++i) {
            var face = i * 20;
            var proj = new ProjectileSpawner(70, new CreatureStats(), new Vector2(0,0), bullet, battle.getBoss());
            proj.addPattern(new SpinMovementPattern(proj, 250));
            proj.currentBulletSpeed = 0.06f;
            proj.setFacing(face);
            projSpawner1.add(proj);
        }
        var projAction1 = new ProjectileBossAction(projSpawner1, 60, 50);

        List<ProjectileSpawner> projSpawner2 = new ArrayList<>();
        for(int i = 0; i<5; ++i) {
            var face = i * 90;
            var proj = new ProjectileSpawner(20, new CreatureStats(), new Vector2((float) Math.pow(-1, i+1) * -2,(float) Math.pow(-1, i+1) * -2), bullet, battle.getBoss());
            proj.addPattern(new SpinMovementPattern(proj, 250));
            proj.currentBulletSpeed = 0.06f;
            proj.setFacing(face);
            projSpawner2.add(proj);
        }
        var projAction2 = new ProjectileBossAction(projSpawner2, 60, 50);

        List<ProjectileSpawner> projSpawner3 = new ArrayList<>();
        for(int i = 0; i<3; ++i) {
            var face = i * 45;
            var proj = new ProjectileSpawner(20, new CreatureStats(), new Vector2((float) Math.pow(-1, i+1) * 4,(float) Math.pow(-1, i+1) * -4), bullet, battle.getBoss());
            proj.addPattern(new SpinMovementPattern(proj, 100));
            proj.currentBulletSpeed = 0.06f;
            proj.setFacing(face);
            projSpawner3.add(proj);
        }
        var projAction3 = new ProjectileBossAction(projSpawner3, 60, 50);


        actions.add(projAction1);
        actions.add(new WaitAction(40,30));
        actions.add(projAction2);
        actions.add(new WaitAction(60,50));
        actions.add(projAction3);

        var spawnGround = new SpawnGroundAOE();
        spawnGround.setTarget(new FireSlimeAdd());
        var spawnAction = new GroundEffectBossAction(spawnGround, 1, new Vector2(0,0));
        actions.add(spawnAction);
        actions.add(new WaitAction(100,90));
    }

    public void run() {
        super.run();
    }
    @Override
    protected List<BossAction> getActions() {
        return actions;
    }
}
