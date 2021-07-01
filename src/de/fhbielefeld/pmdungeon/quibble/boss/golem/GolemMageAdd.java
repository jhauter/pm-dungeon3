package de.fhbielefeld.pmdungeon.quibble.boss.golem;

import com.badlogic.gdx.math.Vector2;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;

public class GolemMageAdd extends Entity {
    public GolemMageAdd(Vector2 position) {
        this.setPosition(position);

        this.animationHandler.addAsDefaultAnimation("idle", 4, 99, 1, 4,
                "assets/textures/entities/mage/wizard_m_idle.png");
       this.renderScaleX = 2;
       this.renderScaleY = 2;

    }
}
