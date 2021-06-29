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
        var bullet = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new GolemProjectile("def", 0, 0, new CreatureStats(), BossBattle.boss);
            }
        };
        for(int i = 0; i<3; ++i) {
            var face = rand.nextInt(360);
            var spawner = new ProjectileSpawner(3000, new CreatureStats(), new Vector2(0,0), bullet, BossBattle.boss);
            spawner.setFacing(face);
            spawner.addPattern(new SpinMovementPattern(spawner, 200));
            spawner.currentBulletSpeed = 0.08f;

            projSpawner.add(spawner);
        }

        actions.add(new ProjectileBossAction(projSpawner, 60,100 ));
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    public void init(BossBattle battle) {
        super.init(battle);
        this.battle = battle;
    }

    @Override
    protected List<BossAction> getActions() {
        return actions;
    }
}
