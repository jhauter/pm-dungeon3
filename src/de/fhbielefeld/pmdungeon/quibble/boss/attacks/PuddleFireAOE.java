package de.fhbielefeld.pmdungeon.quibble.boss.attacks;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStats;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.DamageType;

/**
 * GroundAOE that leaves a small fire effect that hurts the player when touched
 */
public class PuddleFireAOE extends GroundAoe{

    private DungeonLevel level;
    private int dmgCD = 30;
    private int counter = 0;

    public PuddleFireAOE() {
        super();
        this.radius = 1;
        this.animationHandler.addAsDefaultAnimation("burn", 8, 0.1f, 1, 8,
                "assets/textures/entity/boss_slime/flame_aoe.png");
        this.ticksUntilAction = 150;
        this.renderScaleX = 1;
        this.renderScaleY = 1;
    }

    @Override
    public void onSpawn(DungeonLevel level) {
        super.onSpawn(level);
        this.level = level;
    }

    @Override
    protected void onRoam() {
        counter++;
        var entitiesInReach = level.getEntitiesInRadius(this.getPosition().x, this.getPosition().y, radius);
        entitiesInReach.stream().filter(e -> e instanceof Player).findFirst().ifPresent(p -> {
            Player player = (Player) p;

            if(counter >= dmgCD) {
                player.damage(this, DamageType.MAGICAL,  null);
                counter = 0;
            }
        });
    }

    @Override
    protected void onTrigger() {

    }

    @Override
    public CreatureStats getCurrentStats() {
        var stats = new CreatureStats();
        stats.setStat(CreatureStatsAttribs.DAMAGE_MAGIC, 1.2);
        return stats;
    }
}
