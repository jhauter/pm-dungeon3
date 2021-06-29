package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.SpawnGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GolemDemoThirdPhase extends BossPhase {
    private ArrayList<BossAction> actions = new ArrayList<>();

    public GolemDemoThirdPhase() {
        var bullet = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new GolemProjectile("def", 0, 0, new CreatureStats(), BossBattle.boss);
            }
        };

        var projectile = new ProjectileSpawner(15, new CreatureStats(), new Vector2(0,0), bullet, BossBattle.boss);
        var projectile2 = new ProjectileSpawner(15, new CreatureStats(), new Vector2(0,0), bullet, BossBattle.boss);

        var projectile3 = new ProjectileSpawner(20, new CreatureStats(), new Vector2(3,0), bullet, BossBattle.boss);
        var projectile4 = new ProjectileSpawner(20, new CreatureStats(), new Vector2(-3,0), bullet, BossBattle.boss);

        var projectile5 = new ProjectileSpawner(20, new CreatureStats(), new Vector2(0,3), bullet, BossBattle.boss);
        var projectile6 = new ProjectileSpawner(20, new CreatureStats(), new Vector2(0,-3), bullet, BossBattle.boss);

        projectile2.setFacing(90);
        projectile.addPattern(new SpinMovementPattern(projectile, 40));
        projectile2.addPattern(new SpinMovementPattern(projectile, 40));
        projectile3.addPattern(new SpinMovementPattern(projectile, 40));
        projectile4.addPattern(new SpinMovementPattern(projectile, 40));
        projectile5.addPattern(new SpinMovementPattern(projectile, 40));
        projectile6.addPattern(new SpinMovementPattern(projectile, 40));

        projectile.currentBulletSpeed = 0.2f;
        projectile2.currentBulletSpeed = 0.2f;
        projectile3.currentBulletSpeed = 0.2f;
        projectile4.currentBulletSpeed = 0.2f;
        projectile5.currentBulletSpeed = 0.2f;
        projectile6.currentBulletSpeed = 0.2f;

        var spawnAction = new GroundEffectBossAction(new SpawnGroundAOE(2),230 , 200, 2,new Vector2(0,0));

        var testProjectileAction = new ProjectileBossAction(Arrays.asList(projectile, projectile2, projectile3, projectile4, projectile5, projectile6), 200, 100);
        var knockbackAction = new GroundEffectBossAction(new KnockbackGroundAOE(2),  140, 120, 2, new Vector2(0,0));

        actions.add(spawnAction);
        actions.add(knockbackAction);
        actions.add(testProjectileAction);
    }

    @Override
    protected List<BossAction> getActions() {
        return this.actions;
    }

    @Override
    public void init(BossBattle battle) {
        //Tranform Animation
        BossBattle.boss.physBuff = 3;
        BossBattle.boss.updateMaxStats();

        //BossBattle.boss.setPosition(battle.getInitialBossPosition());

        BossBattle.boss.playAttackAnimation("arm_transform", false, 1);

        var arm = new GolemArmAdd(BossBattle.boss.getPosition().x, BossBattle.boss.getPosition().y);

        battle.level.spawnEntity(arm);
        super.init(battle);
    }
}
