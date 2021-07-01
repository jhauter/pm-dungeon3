package de.fhbielefeld.pmdungeon.quibble.boss;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.demon.DemonBossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.golem.GolemBossBattle;
import de.fhbielefeld.pmdungeon.quibble.boss.slime.SlimeBossBattle;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;

public class Trigger extends Entity {
    private boolean triggered = false;
    private DungeonLevel level;
    private BossBattle battle;

    public Trigger(float x, float y) {
        super(x,y);
    }

    public Trigger(BossBattle battle) {
        this.battle = battle;
    }
    @Override
    protected void updateBegin() {

    }

    @Override
    protected void updateLogic() {
        super.updateLogic();
    }

    @Override
    protected void updateEnd() {
        super.updateEnd();
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        this.level = level;
        this.setPosition(15, 23);
        super.onSpawn(level);
    }

    @Override
    protected BoundingBox getInitBoundingBox() {
        return new BoundingBox(0, 0, 0.5F, 5);
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
        if(otherEntity.getDisplayName().equals("Knight") && !triggered) {

            //fireEvent(new EntityEvent(EntityEvent.genEventID(), otherEntity));

            //var battle = new GolemBossBattle(this.level);
            //var battle = new SlimeBossBattle(this.level);
            //var battle = new DemonBossBattle(this.level);
            battle.start();
            triggered = true;

        }

    }
}
