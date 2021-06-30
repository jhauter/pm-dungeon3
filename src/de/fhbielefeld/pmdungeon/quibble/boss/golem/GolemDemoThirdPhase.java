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
import java.util.Collections;
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
        projectile.addPattern(new SpinMovementPattern(projectile, 200));
        projectile.currentBulletSpeed = 0.2f;

        var knockbackAction = new GroundEffectBossAction(new KnockbackGroundAOE(2),  300, 200, 2, new Vector2(0,0));
        var spawnAction = new GroundEffectBossAction(new SpawnGroundAOE(2),100 , 20, 2,new Vector2(0,0));

        var testProjectileAction = new ProjectileBossAction(Collections.singletonList(projectile), 150, 20);

        //TODO: THIS STILL DOES NOT WORK
        actions.add(spawnAction);
        actions.add(knockbackAction);
        actions.add(testProjectileAction);
    }

    @Override
    protected List<BossAction> getActions() {
        return this.actions;
    }
    @Override
    public void run() {
        super.run();

    }
    @Override
    public void init(BossBattle battle) {
        //Tranform Animation
        BossBattle.boss.physBuff = 3;
        BossBattle.boss.updateMaxStats();

        //BossBattle.boss.setPosition(battle.getInitialBossPosition());

        BossBattle.boss.playAttackAnimation("arm_transform", false, 12);

        var arm = new GolemArmAdd(BossBattle.boss.getPosition().x, BossBattle.boss.getPosition().y);

        battle.level.spawnEntity(arm);
        super.init(battle);
    }
}
