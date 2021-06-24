package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.ArrowProjectile;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GolemStartPhase extends BossPhase {
    private ArrayList<BossAction> actions = new ArrayList<>();
    public GolemStartPhase() {
        var bullet = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new GolemProjectile("def", 0, 0, new CreatureStats(), BossBattle.boss);
            }
        };

        var projectile1 = new ProjectileSpawner(50, new CreatureStats(), new Vector2(-3,0), bullet, BossBattle.boss);
        var projectile2 = new ProjectileSpawner(50, new CreatureStats(), new Vector2(3,0), bullet, BossBattle.boss);
        var projectile3 = new ProjectileSpawner(50, new CreatureStats(), new Vector2(0,-3), bullet, BossBattle.boss);
        var projectile4 = new ProjectileSpawner(50, new CreatureStats(), new Vector2(0,3), bullet, BossBattle.boss);

        var projectile5 = new ProjectileSpawner(50, new CreatureStats(), new Vector2(0,5), bullet, BossBattle.boss);
        var projectile6 = new ProjectileSpawner(50, new CreatureStats(), new Vector2(0,-5), bullet, BossBattle.boss);

        projectile2.setFacing(10);
        projectile4.setFacing(180);

        projectile1.currentBulletSpeed = 0.1f;
        projectile2.currentBulletSpeed = 0.1f;
        projectile3.currentBulletSpeed = 0.1f;
        projectile4.currentBulletSpeed = 0.1f;

        projectile1.addPattern(new SpinMovementPattern(projectile1, 2));
        projectile2.addPattern(new SpinMovementPattern(projectile2, 1));
        projectile3.addPattern(new SpinMovementPattern(projectile3,2 ));
        projectile4.addPattern(new SpinMovementPattern(projectile4, 1));
        projectile5.addPattern(new SpinMovementPattern(projectile5, 2));
        projectile6.addPattern(new SpinMovementPattern(projectile6, 1));

        var testProjectileAction = new ProjectileBossAction(new ArrayList<>(Arrays.asList(projectile1, projectile2,
                projectile3, projectile4, projectile5, projectile6)));

        var knockbackAction = new GroundEffectBossAction(new KnockbackGroundAOE(2), 2, new Vector2(0, -1));

        actions.add(testProjectileAction);
        actions.add(knockbackAction);
    }

    @Override
    public void init(BossBattle battle) {
        super.init(battle);
    }

    @Override
    protected List<BossAction> getActions() {
        return actions;

    }
}