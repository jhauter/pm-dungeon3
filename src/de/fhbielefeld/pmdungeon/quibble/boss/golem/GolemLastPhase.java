package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
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
import java.util.Random;

public class GolemLastPhase extends BossPhase {
    private int teleportCD = 200;
    private int teleportCDCounter = 0;
    private Entity mage = new GolemMageAdd(new Vector2(0, 0));
    private Random rand = new Random();
    private BossBattle battle;
    private ArrayList<ProjectileSpawner> projSpawner = new ArrayList<>();
    private ArrayList<BossAction> actions = new ArrayList<>();


    public GolemLastPhase() {
        refreshActions();
    }

    private void refreshActions() {
        var bullet = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new GolemProjectile("def", 0, 0, new CreatureStats(), BossBattle.boss);
            }
        };
        projSpawner.forEach(s -> s.despawnFlag = true);
        projSpawner.clear();

        for(int i = 0; i<5; ++i) {
            var face = rand.nextInt(360);
            var spawner = new ProjectileSpawner(35, new CreatureStats(), new Vector2(0,0), bullet, BossBattle.boss);
            spawner.currentBulletSpeed = 0.1f;

            spawner.setFacing(face);
            projSpawner.add(spawner);
        }

        actions.clear();
        //actions.add(new GroundEffectBossAction(new KnockbackGroundAOE(2), 2, new Vector2(0, -1)));
        actions.add(new ProjectileBossAction(projSpawner, 800, 100));
    }

    @Override
    public void run() {
        super.run();
        teleportCDCounter++;
        System.out.println(mage.getPosition());
        System.out.println(BossBattle.boss.getPosition());

        if(teleportCDCounter >= teleportCD) {
            mage.setPosition(BossBattle.boss.getPosition());
            teleportCDCounter = 0;
            refreshActions();
        }
    }

    @Override
    public void init(BossBattle battle) {
        super.init(battle);
        this.battle = battle;
        battle.level.spawnEntity(mage);
    }

    @Override
    protected List<BossAction> getActions() {
        return actions;
    }
}
