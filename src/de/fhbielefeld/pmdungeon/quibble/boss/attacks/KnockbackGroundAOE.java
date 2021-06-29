package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.boss.battle.BossBattle;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageSource;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffectKnockback;

public class KnockbackGroundAOE extends GroundAoe implements DamageSource {
    private DungeonLevel level;

    public KnockbackGroundAOE(int radius) {
        super(radius);
        this.animationHandler.addAsDefaultAnimation("slam",
                4, 0.01f, 1, 4, "assets/textures/entity/golem/groundAOE.png");
        this.ticksUntilAction = 2;
        this.renderScaleX = 3;
        this.renderScaleY = 3;

    }
    @Override
    protected void onRoam() {
        //Nix
    }

    @Override
    protected void onTrigger() {
        var entitiesInReach = level.getEntitiesInRadius(this.getPosition().x, this.getPosition().y, radius);
        entitiesInReach.stream().filter(e -> e instanceof Player).findFirst().ifPresent(p -> {
            Player player = (Player) p;
            player.damage(this, DamageType.PHYSICAL,  null);
        });
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        super.onSpawn(level);
        this.level = level;
    }
    @Override
    public CreatureStats getCurrentStats() {
        var stats = new CreatureStats();
        stats.setStat(CreatureStatsAttribs.KNOCKBACK, 2);
        return stats;
    }
}