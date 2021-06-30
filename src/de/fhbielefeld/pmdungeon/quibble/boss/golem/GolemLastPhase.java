package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.boss.misc.CamRumbleEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

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
    private ArrayList<BossAction> actions = new ArrayList<>();


    public GolemLastPhase() {
        var bullet = new BulletCreationFunction() {
            @Override
            public Projectile createProjectile() {
                return new GolemProjectile("def", 0, 0, new CreatureStats(), BossBattle.boss);
            }
        };
        ArrayList<ProjectileSpawner> projSpawner = new ArrayList<>();
        for(int i = 0; i<9; ++i) {
            var face = rand.nextInt(360);
            var spawner = new ProjectileSpawner(30, new CreatureStats(), new Vector2(0,0), bullet, BossBattle.boss);
            spawner.setFacing(face);
            spawner.addPattern(new SpinMovementPattern(spawner, 30));
            spawner.currentBulletSpeed = 0.08f;

            projSpawner.add(spawner);
        }

        actions.add(new ProjectileBossAction(projSpawner, 300,300));
        actions.add(new WaitAction(100, 100));
    }

    @Override
    public void run() {
        super.run();
        BossBattle.boss.playAttackAnimation("panic_idle", true, 19);
        CamRumbleEffect.shake(0.05f, 9999f);
        var cam = DungeonStart.getDungeonMain().getCamera();
        var fpos = DungeonStart.getDungeonMain().getPlayer().getPosition();

        if(CamRumbleEffect.getTimeLeft() > 0) {

            var rumble = CamRumbleEffect.update();
            cam.setFocusPoint(new Point(fpos.x + rumble.x, fpos.y + rumble.y));
        } else {
            DungeonStart.getDungeonMain().setCameraTarget(DungeonStart.getDungeonMain().getPlayer());
        }
    }

    @Override
    public void init(BossBattle battle) {
        super.init(battle);
        this.battle = battle;
        BossBattle.boss.playAttackAnimation("animPanic", false, 20);
    }

    @Override
    public void cleanStage() {
        super.cleanStage();
    }

    @Override
    protected List<BossAction> getActions() {
        return actions;
    }
}
