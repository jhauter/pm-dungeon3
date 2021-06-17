package de.fhbielefeld.pmdungeon.quibble.boss;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandlerImpl;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;

import java.util.ArrayList;

public class Boss extends NPC {
    public BossDifficulty difficulty;
    private CreatureStats _baseStats;

    //Not yet used
    private Boss child;

    public Boss(BossBuilder builder) {
        super(0, 0);
        this.build(builder);
    }

    public void build(BossBuilder builder) {

        setPosition(0,0);

        var scale = builder.renderScale
                .orElseGet(() -> new Vector2(1,1));
        this.renderScaleX = scale.x;
        this.renderScaleY = scale.y;

        //NOTE: Maybe only give it to context instead
        this.difficulty = builder.difficulty
                .orElseGet(() -> BossDifficulty.Easy);

        var stats = new CreatureStats();

        //NOTE: These are weird defaults REPLACE
        stats.setStat(CreatureStatsAttribs.HEALTH, 500);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, 100);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, 5);
        stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.0D);
        stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.25D);
        stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.1D);
        stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 2.0D);
        stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 3.0D);
        stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.04D);
        stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.4D);
        stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 20.0D);

        this._baseStats = builder.baseStats.orElseGet(() -> stats);

        this.child = builder.child.orElseGet(() -> null);

        ArrayList<AnimationHandlerImpl.AnimationInfo> animDefaultList = new ArrayList<>();
        animDefaultList.add(new AnimationHandlerImpl.AnimationInfo("idle",
                4,0, 0.15F, 1, 4,
                "assets/textures/entity/demon/big_demon_idle.png"));

        var animList = builder.animations.orElseGet(() -> animDefaultList);
        this.animationHandler.addAsDefaultAnimation(animList.get(0));

        for(int i  = 1; i<animList.size(); ++i) {
            this.animationHandler.addAnimation(animList.get(i));
        }

    }

    @Override
    protected boolean useHitAnimation() {
        return true;
    }
    @Override
    public int getExpDrop() {
        return 50;
    }

    @Override
    protected CreatureStats getBaseStatsForLevel(int level) {
        CreatureStats stats = new CreatureStats();
        stats.setStat(CreatureStatsAttribs.HEALTH, 100 + 2 * level);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_PHYS, level);
        stats.setStat(CreatureStatsAttribs.RESISTANCE_MAGIC, level);
        stats.setStat(CreatureStatsAttribs.MISS_CHANCE, 0.0D);
        stats.setStat(CreatureStatsAttribs.CRIT_CHANCE, 0.1D);
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, 0.25D);
        stats.setStat(CreatureStatsAttribs.KNOCKBACK_RES, 0.3D);
        stats.setStat(CreatureStatsAttribs.DAMAGE_PHYS, 3.0D);
        stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1.0D);
        stats.setStat(CreatureStatsAttribs.WALKING_SPEED, 0.02D);
        stats.setStat(CreatureStatsAttribs.HIT_REACH, 0.4D);
        stats.setStat(CreatureStatsAttribs.HIT_COOLDOWN, 15.0D);
        return stats;
    }
}
