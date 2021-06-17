package de.fhbielefeld.pmdungeon.quibble.boss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationHandlerImpl;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BossBuilder {

    public Optional<BossDifficulty> difficulty = Optional.empty();
    public Optional<CreatureStats> baseStats = Optional.empty();
    public Optional<Vector2> renderScale = Optional.empty();
    public List<BossAction> actions = new ArrayList<BossAction>();

    //TODO: Mehrere Animationen erlauben?
    public Optional<List<AnimationHandlerImpl.AnimationInfo>> animations = Optional.empty();

    public Optional<Boss> child = Optional.empty();

    /*
    public BossBuilder setLevel(Integer lv) {
        this.level = Optional.ofNullable(lv);
        return this;
    }
    */

    public BossBuilder setDifficulty(BossDifficulty diff) {
        this.difficulty = Optional.ofNullable(diff);
        return this;
    }
    public BossBuilder setMaxHP(CreatureStats stats) {
        this.baseStats = Optional.ofNullable(stats);
        return this;
    }

    public BossBuilder setRenderScale(Vector2 renderScale) {
        this.renderScale= Optional.ofNullable(renderScale);
        return this;
    }

    public BossBuilder setAnimation(List<AnimationHandlerImpl.AnimationInfo> animations) {
        this.animations = Optional.ofNullable(animations);

        return this;
    }

    public BossBuilder setChild(Boss child) {
        this.child = Optional.ofNullable(child);
        return this;
    }
}
