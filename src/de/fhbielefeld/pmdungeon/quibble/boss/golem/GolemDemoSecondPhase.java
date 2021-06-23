package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossPhase;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.ProjectileBossAction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BoundedMovePattern;
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

public class GolemDemoSecondPhase extends BossPhase {
    private ArrayList<BossAction> actions = new ArrayList<>();

    public GolemDemoSecondPhase() {
        var bullet = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new GolemProjectile("def", 0, 0, new CreatureStats(), BossBattle.boss);
            }
        };
        var projectile = new ProjectileSpawner(10, new CreatureStats(), new Vector2(3,3), bullet, BossBattle.boss);
        var projectile2 = new ProjectileSpawner(10, new CreatureStats(), new Vector2(1,1), bullet, BossBattle.boss);

        projectile.addPattern(new SpinMovementPattern(projectile));

        projectile2.addPattern(new SpinMovementPattern(projectile));

        var testProjectileAction = new ProjectileBossAction(Arrays.asList(projectile, projectile2));
        actions.add(testProjectileAction);
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
