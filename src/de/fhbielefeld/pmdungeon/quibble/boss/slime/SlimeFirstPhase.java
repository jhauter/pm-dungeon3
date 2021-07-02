package de.fhbielefeld.pmdungeon.quibble.boss.slime;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.ExplosionGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.PuddleFireAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SlimeFirstPhase extends BossPhase {
    private final ArrayList<BossAction> actions = new ArrayList<>();

    public SlimeFirstPhase(BossBattle battle) {
        super(battle);
        var bullet = new BulletCreationFunction() {

            @Override
            public Projectile createProjectile() {
                var projectileStats = new CreatureStats();
                projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 4);
                return new SlimeProjectile("def", 0, 0, projectileStats, battle.getBoss());
            }
        };

        List<ProjectileSpawner> projSpawner = new ArrayList<>();
        for(int i = 0; i<4; ++i) {
            var face = i * 20;
            var proj = new ProjectileSpawner(70, new CreatureStats(), new Vector2(0,0), bullet, battle.getBoss());
            proj.addPattern(new SpinMovementPattern(proj, 250));
            proj.currentBulletSpeed = 0.07f;
            proj.setFacing(face);
            projSpawner.add(proj);
        }

        var projSpawner2 = new ProjectileSpawner(70, new CreatureStats(), new Vector2(0,0), bullet, battle.getBoss());
        projSpawner2.addPattern(new SpinMovementPattern(projSpawner2, 30));
        projSpawner2.currentBulletSpeed = 0.09f;
        var testProjectileAction = new ProjectileBossAction(projSpawner, 60, 50);
        var testProjectileAction2 = new ProjectileBossAction(Collections.singletonList(projSpawner2), 40, 30);

        var waitAction = new WaitAction(50, 70);
        var waitAction2 = new WaitAction(70, 100);
        var puddleAction = new GroundEffectBossAction(new PuddleFireAOE(), 1, new Vector2(0,0));

        var hero = DungeonStart.getDungeonMain().getPlayer();
        for(int i = 0; i<4; ++i) {
            var action = new GroundEffectBossAction(new ExplosionGroundAOE(), 10, 10, new Vector2(i%2==0? -i : i,i%2==0? -i : i), hero);
            actions.add(action);
        }
        actions.add(new WaitAction(200, 100));
        actions.add(testProjectileAction);
        actions.add(new WaitAction(200, 100));
        actions.add(waitAction);
        actions.add(puddleAction);
        actions.add(testProjectileAction2);
        actions.add(waitAction2);
    }

    @Override
    public void run() {
        super.run();
//        System.out.println("Running first phase");
    }

    @Override
    protected List<BossAction> getActions() {
        return actions;
    }

    @Override
    public void cleanStage() {
        super.cleanStage();
    }

    @Override
    public void init() {
        super.init();
    }
}
