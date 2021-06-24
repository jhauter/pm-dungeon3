package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.boss.golem.GolemBossBattle;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;

import static com.badlogic.gdx.math.MathUtils.lerp;

public class CutsceneCamera extends Entity {
    private boolean triggered = false;
    private DungeonLevel level;
    private Entity target;
    private Entity hero;
    private boolean deleteFlag;
    private float zoom = 2.0f;
    private boolean camReturn = false;
    private float speed = 0.1f;

    public CutsceneCamera(float x, float y) {
        super(x,y);
        this.deleteFlag = false;
    }

    public void setHero(Entity hero) {
        this.hero = hero;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }

    @Override
    protected void updateBegin() {

    }

    @Override
    protected void updateLogic() {
        super.updateLogic();

        //var cam = DungeonStart.getDungeonMain().getCamera();
        //cam.zoom = this.zoom;

        //TODO replace with path
        float x = this.getX()>target.getX() ? speed*-1 : speed;
        float y = this.getY()>target.getY() ? speed*-1 : speed;

        //this.move(lerp(this.getX(),this.target.getX(),this.speed),lerp(this.getY(),this.target.getY(),this.speed));
        this.move(x,y);
    }


    @Override
    public boolean shouldDespawn()
    {
        return deleteFlag;
    }

    @Override
    protected void updateEnd() {
        super.updateEnd();
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        this.level = level;
        super.onSpawn(level);
        DungeonStart.getDungeonMain().setCameraTarget(this);

    }

    @Override
    protected BoundingBox getInitBoundingBox() {
        return new BoundingBox(0, 0, 0.01f, 0.01f);
    }

    @Override
    public boolean useAnimationHandler() {
        return false;
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    @Override
    protected void onEntityCollision(Entity otherEntity) {
        super.onEntityCollision(otherEntity);
        if(otherEntity instanceof Player && camReturn)
        {
            System.out.println("Collision Player");
            this.deleteFlag = true;
            DungeonStart.getDungeonMain().setCameraTarget(this.hero);
        }
        if(otherEntity instanceof Boss)
        {
            System.out.println("Collision Boss");
            this.setTarget(hero);
            this.camReturn = true;
            this.speed = 1f;

        }

    }
}
