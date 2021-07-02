package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.ExplosionGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.SpawnGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GolemDemoThirdPhase extends BossPhase {
    private ArrayList<BossAction> actions = new ArrayList<>();

    public GolemDemoThirdPhase(BossBattle battle) {
        super(battle);
        var bullet = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                var projectileStats = new CreatureStats();
                projectileStats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 7f);
                return new GolemProjectile("def", 0, 0, projectileStats, battle.getBoss());
            }
        };

        var projectile = new ProjectileSpawner(15, new CreatureStats(), new Vector2(0,0), bullet, battle.getBoss());
        projectile.addPattern(new SpinMovementPattern(projectile, 200));
        projectile.currentBulletSpeed = 0.2f;

        var knockbackAction = new GroundEffectBossAction(new KnockbackGroundAOE(),  300, 200, 2, new Vector2(0,0));
        var spawnAction = new GroundEffectBossAction(new SpawnGroundAOE(),100 , 20, 2,new Vector2(0,0));

        var testProjectileAction = new ProjectileBossAction(Collections.singletonList(projectile), 150, 20);
        var testExplosionAction = new GroundEffectBossAction(new ExplosionGroundAOE(), 2, new Vector2(0,0), DungeonStart.getDungeonMain().getPlayer());

        for(int i = 0; i<4; ++i) {
            var action = new GroundEffectBossAction(new ExplosionGroundAOE(), 2, new Vector2((float)Math.pow(-1, i)*2, (float)Math.pow(-1, i+1)*2));
            actions.add(action);
        }

        actions.add(testExplosionAction);
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
    public void init() {
        //Tranform Animation
        battle.getBoss().physBuff = 12;
        battle.getBoss().magBuff = 3;
        battle.getBoss().updateMaxStats();

        //BossBattle.boss.setPosition(battle.getInitialBossPosition());

        battle.getBoss().playAttackAnimation("arm_transform", false, 12);

        var arm = new GolemArmAdd(battle.getBoss().getPosition().x,battle.getBoss().getPosition().y);

        battle.level.spawnEntity(arm);
        super.init();
    }
}
