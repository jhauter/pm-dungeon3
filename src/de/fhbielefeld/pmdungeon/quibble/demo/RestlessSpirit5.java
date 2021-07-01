package de.fhbielefeld.pmdungeon.quibble.demo;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.entity.*;
import de.fhbielefeld.pmdungeon.quibble.entity.ai.AIShootFireball;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

public class RestlessSpirit5 extends NPC {

    private boolean noticedPlayer = false;
    private boolean lightIdle = false;

    public RestlessSpirit5(float x, float y) {
        super(x,y);
        this.animationHandler.addAsDefaultAnimation("idle", 7, 0.1f, 1,7,
                "assets/textures/entity/demo/ghost-idle.png");
        this.animationHandler.addAnimation(Creature.ANIM_NAME_ATTACK,4 , 0.1f, 1, 4,
                "assets/textures/entity/demo/ghost_hit.png");

        this.renderHeight = 3f;
        this.renderWidth = 3f;
    }

    public RestlessSpirit5() {
        this(0f, 0f);
    }

    public String getDisplayName() {
        return "Dungeon Spirit";
    }

    @Override
    protected CreatureStats getBaseStatsForLevel(int level) {

        CreatureStats stats = new CreatureStats();

        stats.setStat(CreatureStatsAttribs.HEALTH, 6 + level);
        stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.03);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);
        stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.4);
        stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2.0D);
        stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 3.0D);
        stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 20.0D);
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.25D);
        return stats;
    }

    @Override
    public void calculateCurrentBehavior() {
        Player player = DungeonStart.getDungeonMain().getPlayer();

        if(!this.noticedPlayer)
        {
            if(this.hasLineOfSightTo(new Vector2(player.getPosition().x, player.getPosition().y))) {
                System.out.println("Line of sight");
                this.noticedPlayer = true;
                this.setAIStrategy(new AIShootFireball(player));
                this.lightIdle = false;

            } else if (!this.lightIdle) {
                this.lightIdle = true;
                System.out.println("Light Strategy");
                this.setAIStrategy(new GhostStrategy1());
            }
        }
    }

    @Override
    protected BoundingBox getInitBoundingBox() {
        return new BoundingBox(-0.55f,0,0.8f,1);
    }

    @Override
    protected boolean useHitAnimation() {
        return true;
    }

    @Override
    protected boolean useAttackAnimation() {
        return true;
    }

    @Override
    protected void onEntityCollision(Entity otherEntity) {
        super.onEntityCollision(otherEntity);
        if(otherEntity instanceof Player) {
            this.attack((Player)otherEntity);
        }
    }
}
