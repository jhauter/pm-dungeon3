package de.fhbielefeld.pmdungeon.vorgaben.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;

/**
 * ApplicationListener that delegates to the MainGameController.
 * Just some setup.
 */
public class GameSetup extends Game {

    /**
     * This INSTANCE is necessary to draw ALL the stuff. Every Object that uses draw need to know THIS INSTANCE
     */
    public static SpriteBatch batch;
    private final MainController mc;

    public GameSetup(MainController mc) {
        this.mc = mc;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        this.setScreen(mc);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


}
