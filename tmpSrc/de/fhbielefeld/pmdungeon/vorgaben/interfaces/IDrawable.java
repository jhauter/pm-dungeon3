package de.fhbielefeld.pmdungeon.vorgaben.interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;


/**
 * Should be implement by all objects that are drawable but have no animation
 */
public interface IDrawable {

    /**
     * @return the exact position in the dungeon of this instance
     */
    Point getPosition();

    /**
     * @return the (current)texture of the object.
     */
    Texture getTexture();

    /**
     * Draws the instance based on its position.
     *
     * @param xOffset  sometimes it can be helpful to use a small offset
     * @param yOffset  sometimes it can be helpful to use a small offset
     * @param xScaling scaling x-axis
     * @param yScaling scaling y-axis
     */
    default void draw(float xOffset, float yOffset, float xScaling, float yScaling) {
        Texture texture = this.getTexture();
        Sprite sprite = new Sprite(texture);
        //this will resize the texture. this is setuped for the textures used in the thesis
        sprite.setSize(xScaling, yScaling);
        //where to draw the sprite
        Point position = this.getPosition();
        sprite.setPosition(position.x + xOffset, position.y + yOffset);

        //need to be called before drawing
        GameSetup.batch.begin();
        //draw sprite
        sprite.draw(GameSetup.batch);
        //need to be called after drawing
        GameSetup.batch.end();
    }

    /**
     * Draws the instance based on its position with default offset and default scaling.
     */
    default void draw() {
        //found offset by try and error
        Texture texture = this.getTexture();
        this.draw(-0.85f, -0.5f, 1, ((float) texture.getHeight() / (float) texture.getWidth()));
    }

    /**
     * Draws the instance based on its position with default offset and specific scaling.
     *
     * @param xScaling scaling x-axis
     * @param yScaling scaling y-axis
     */
    default void drawWithScaling(float xScaling, float yScaling) {
        this.draw(-0.85f, -0.5f, xScaling, yScaling);
    }

    /**
     * Draws the instance based on its position with default scaling and specific offset
     *
     * @param xOffset offset x-axis
     * @param yOffset offset y-axis
     */
    default void draw(float xOffset, float yOffset) {
        Texture texture = this.getTexture();
        this.draw(xOffset, yOffset, 1, ((float) texture.getHeight() / (float) texture.getWidth()));
    }

}
