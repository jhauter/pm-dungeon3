package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.GroundAoe;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;

import java.util.Objects;

public class GroundEffectBossAction extends BossAction {

    private int radius;
    private Vector2 position;

    private int wait = 100;
    private int counter = 0;
    private boolean attack = false;
    private GroundAoe effect;

    /**
     * Constructs a GroundEffect that can be used by a boss
     * @param effect Effect
     * @param radius Max Radius of the GroundEffect
     * @param bossRelativePosition Position of the GroundEffect relative to the boss
     */
    public GroundEffectBossAction(GroundAoe effect, int radius, Vector2 bossRelativePosition) {
        this.effect = effect;

        this.radius = radius;
        this.duration = 140;
        this.cooldown = 120;
        this.position = bossRelativePosition;
    }

    /**
     * @param effect Effect
     * @param duration Time until action is finished
     * @param cooldown Time until next action may be executed
     * @param radius Max Radius
     * @param bossRelativePosition Position of the GroundEffect relative to the boss
     */
    public GroundEffectBossAction(GroundAoe effect, int duration, int cooldown, int radius, Vector2 bossRelativePosition) {
        this.effect = effect;

        this.radius = radius;
        this.duration = 140;
        this.cooldown = cooldown;
        this.position = bossRelativePosition;
    }

    @Override
    public void onActionBegin(BossBattle battle) {
        super.onActionBegin(battle);
        //TODO: Replace with an "onCreate" or sth in effect
        BossBattle.boss.playAttackAnimation("slam", false, 12);
    }

    @Override
    public void execute() {
        super.execute();
        counter++;

        if(counter >= wait && !attack) {
            attack = true;
            //battle.level.spawnEntity(new KnockbackGroundAOE(radius, new Vector2(
            //this.position.x + BossBattle.boss.getX(), this.position.y + BossBattle.boss.getY())));
            effect.setPosition(this.position.x + BossBattle.boss.getX(), this.position.y + BossBattle.boss.getY());
            effect.shouldDespawn = false;
            battle.level.spawnEntity(effect);
            counter = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GroundEffectBossAction that = (GroundEffectBossAction) o;
        return radius == that.radius && wait == that.wait && counter == that.counter && attack == that.attack && Objects.equals(position, that.position) && Objects.equals(effect, that.effect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), radius, position, wait, counter, attack, effect);
    }

    @Override
    public void onActionEnd() {
        counter = 0;
        attack = false;

    }
}
