package de.fhbielefeld.pmdungeon.quibble.boss.slime;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.SpawnGroundAOE;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.*;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BulletCreationFunction;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.ProjectileSpawner;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.SpinMovementPattern;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.List;

public class SlimeSecondPhase extends BossPhase {
    private BossBattle battle;
    private ArrayList<BossAction> actions;

    public SlimeSecondPhase(BossBattle battle) {
        super(battle);
        this.battle = battle;
        this.actions = new ArrayList<>();
        var bullet = new BulletCreationFunction() {

            @Override
            public Projectile createProjectile() {
                return new SlimeProjectile("def", 0, 0, new CreatureStats(), battle.getBoss());
            }
        };

        List<ProjectileSpawner> projSpawner = new ArrayList<>();
        for(int i = 0; i<6; ++i) {
            var face = i * 25;
            var proj = new ProjectileSpawner(40, new CreatureStats(), new Vector2(0,0), bullet, battle.getBoss());
            proj.addPattern(new SpinMovementPattern(proj, 250));
            proj.currentBulletSpeed = 0.09f;
            proj.setFacing(face);
            projSpawner.add(proj);
        }
        var waitAction = new WaitAction(120, 100);
        var spawnAction = new GroundEffectBossAction(new SpawnGroundAOE(new FireSlimeAdd()),1, new Vector2(0,0));
        var projAction = new ProjectileBossAction(projSpawner, 60, 50);

        actions.add(projAction);
        //actions.add(waitAction);
        actions.add(spawnAction);
    }

    public void run() {
        super.run();
    }

    @Override
    protected List<BossAction> getActions() {
        return actions;
    }
    public void init() {
        super.init();
    }
}
