package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.SpawnGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BoundedMovePattern;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.ArrowProjectile;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GolemDemoSecondPhase extends BossPhase {
    private ArrayList<BossAction> actions = new ArrayList<>();

    public GolemDemoSecondPhase() {
        var bullet = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new GolemProjectile("def", 0, 0, new CreatureStats(), BossBattle.boss);
            }
        };
        var projectile = new ProjectileSpawner(15, new CreatureStats(), new Vector2(0,0), bullet, BossBattle.boss);

        projectile.addPattern(new SpinMovementPattern(projectile, 7));
        projectile.currentBulletSpeed = 0.05f;

        var testProjectileAction = new ProjectileBossAction(Arrays.asList(projectile));

        var spawnAction = new GroundEffectBossAction(new SpawnGroundAOE(2),30 , 50, 2,new Vector2(0,0));

        actions.add(spawnAction);
        actions.add(testProjectileAction);
    }

    @Override
    public void init(BossBattle battle) {
        super.init(battle);
        BossBattle.boss.playAttackAnimation("shield", false, 1);
        BossBattle.boss.physBuff = 1000;
        BossBattle.boss.updateMaxStats();
    }

    @Override
    public void run() {
        super.run();
        BossBattle.boss.playAttackAnimation("shield_idle", true, 0);
    }

    @Override
    protected List<BossAction> getActions() {
        return actions;
    }
}
