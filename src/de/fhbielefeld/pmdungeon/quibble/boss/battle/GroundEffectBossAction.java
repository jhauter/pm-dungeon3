package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.GroundAoe;
import de.fhbielefeld.pmdungeon.quibble.boss.attacks.KnockbackGroundAOE;

public class GroundEffectBossAction extends BossAction {

    private int radius;
    private Vector2 position;

    private int wait = 20;
    private int counter = 0;
    private boolean attack = false;
    private BossBattle battle;
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
        this.duration = 30;
        this.cooldown = 40;
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
        this.duration = duration;
        this.cooldown = cooldown;
        this.position = bossRelativePosition;
    }

    @Override
    public void onActionBegin(BossBattle battle) {
        super.onActionBegin(battle);
        //TODO: Replace with an "onCreate" or sth in effect
        BossBattle.boss.playAttackAnimation("slam", false, 1);
        this.battle = battle;

    }

    @Override
    public void execute() {
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
    public void onActionEnd() {
        counter = 0;
        attack = false;

    }
}
