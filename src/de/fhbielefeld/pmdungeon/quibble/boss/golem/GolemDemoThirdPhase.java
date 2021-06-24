package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossPhase;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.ProjectileBossAction;
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

        projectile.addPattern(new SpinMovementPattern(projectile, 10));
        projectile.currentBulletSpeed = 0.08f;
        var testProjectileAction = new ProjectileBossAction(Arrays.asList(projectile));

        actions.add(testProjectileAction);
    }

    @Override
    protected List<BossAction> getActions() {
        return this.actions;
    }

    @Override
    public void init(BossBattle battle) {
        //Tranform Animation
        BossBattle.boss.physBuff = 50;
        BossBattle.boss.updateMaxStats();

        var arm = new GolemArmAdd();
        arm.setPosition(BossBattle.boss.getPosition());

        battle.level.spawnEntity(arm);
        super.init(battle);
    }
}
