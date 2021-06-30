package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.SpawnGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GolemDemoSecondPhase extends BossPhase {
    private final ArrayList<BossAction> actions = new ArrayList<>();

    public GolemDemoSecondPhase(BossBattle battle) {
        super(battle);
        var bullet = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new GolemProjectile("def", 0, 0, new CreatureStats(), battle.getBoss());
            }
        };
        var projectile = new ProjectileSpawner(100, new CreatureStats(), new Vector2(0,0), bullet, battle.getBoss());

        projectile.addPattern(new SpinMovementPattern(projectile, 10));
        projectile.currentBulletSpeed = 0.05f;

        var testProjectileAction = new ProjectileBossAction(Arrays.asList(projectile), 100, 100);

        var spawnAction = new GroundEffectBossAction(new SpawnGroundAOE(),180 , 140, 2,new Vector2(0,0));

        actions.add(spawnAction);
        actions.add(testProjectileAction);
    }

    @Override
    public void init() {
        super.init();
        battle.getBoss().playAttackAnimation("shield", false, 14);
        battle.getBoss().physBuff = 1000;
        battle.getBoss().updateMaxStats();
    }

    @Override
    public void run() {
        super.run();
        battle.getBoss().playAttackAnimation("shield_idle", true, 20);
    }


    @Override
    protected List<BossAction> getActions() {
        return actions;
    }

    @Override
    public void cleanStage() {
        super.cleanStage();
    }
}
