package de.fhbielefeld.pmdungeon.vorgaben.interfaces;

import com.badlogic.gdx.graphics.Texture;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.Animation;


/**
 * Should be implemented by all objects that have an animation
 */
public interface IAnimatable extends IDrawable {

    /**
     * @return the current active animation (example idle or run)
     */
    Animation getActiveAnimation();

    @Override
    default Texture getTexture() {
        return this.getActiveAnimation().getNextAnimationTexture();
    }

}
