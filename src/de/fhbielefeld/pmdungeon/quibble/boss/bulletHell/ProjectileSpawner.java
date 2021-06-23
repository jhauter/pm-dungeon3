package de.fhbielefeld.pmdungeon.quibble.boss.bulletHell;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.boss.bulletHell.BoundedMovePattern;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.projectile.Projectile;

import java.util.ArrayList;
import java.util.List;

public class ProjectileSpawner extends Entity {
    private DungeonLevel level;

    private int shootingIntervall;
    private int shootingIntervallCounter;

    private int stateSwitchIntervall;
    private int getStateSwitchIntervallCounter;

    private CreatureStats parentStats;

    private List<ProjectileMovementPattern> patterns;

    private Vector2 facing = new Vector2(1,1);

    private float projectileSpeed = 0.5F;
    private SpinMovementPattern test;
    private SpinBoundsPattern test2;
    private BoundedMovePattern test3;
    public Entity parent;

    public Vector2 offset;

    public boolean despawnFlag = false;
    public BulletCreationFunction bulletCreationFunction;

    public ProjectileSpawner(int shootingIntervall, CreatureStats parentStats, Vector2 position, BulletCreationFunction func) {
        this.shootingIntervall = shootingIntervall;
        this.parentStats = parentStats;

        this.facing = this.facing.setAngleDeg(370);
        this.setPosition(position);
        this.patterns = new ArrayList<>();
        this.offset = position;
        this.bulletCreationFunction = func;
    }

    public ProjectileSpawner(int shootingIntervall, CreatureStats parentStats, Vector2 position, BulletCreationFunction func, Entity parent) {
        this.shootingIntervall = shootingIntervall;
        this.parentStats = parentStats;

        this.facing = this.facing.setAngleDeg(370);
        this.offset = position;
        this.setPosition(parent.getX() + position.x, parent.getY() + position.y);
        this.patterns = new ArrayList<>();

        this.bulletCreationFunction = func;
        this.parent = parent;
    }
    public void addPattern(ProjectileMovementPattern pattern) {
        patterns.add(pattern);
    }

    @Override
    protected void updateBegin() {

    }

    @Override
    protected void updateLogic() {

        super.updateLogic();
        //patterns.peek().execute();
        //Switch State
        //Dequeue
        this.shootingIntervallCounter++;

        for(var a: patterns) {
            a.execute();
        }
        if(parent != null) {
            this.setPosition(offset.x + parent.getX(), offset.y + parent.getY());
        }
        if(shootingIntervallCounter >= shootingIntervall) {
            shoot();
            shootingIntervallCounter = 0;
        }
    }

    public void shoot() {
        //Projectile proj = new ArrowProjectile("Arrow", this.getX(), this.getY(), parentStats, BossBattle.boss);
        Projectile proj = bulletCreationFunction.createProjectile();
        proj.setPosition(this.getX(), this.getY());
        Vector2 dir = facing;
        dir.setLength(projectileSpeed);
        proj.setVelocity(dir.x, dir.y);
        level.spawnEntity(proj);
    }
    @Override
    protected void updateEnd() {
        super.updateEnd();
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        super.onSpawn(level);
        this.level = level;
    }

    @Override
    protected BoundingBox getInitBoundingBox() {
        return new BoundingBox(0,0,0.5f, 0.5f);
    }

    @Override
    public boolean useAnimationHandler() {
        return false;
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    public void setFacing(float angle) {
        this.facing = facing.setAngleDeg(angle);
    }

    @Override
    public boolean shouldDespawn() {
        return despawnFlag;
    }


}
