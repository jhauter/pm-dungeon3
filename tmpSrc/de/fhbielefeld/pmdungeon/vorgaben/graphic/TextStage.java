package de.fhbielefeld.pmdungeon.vorgaben.graphic;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

/**
 * Helps to draw text on the screen
 */
public class TextStage extends Stage {

    private final ArrayList<Label> labels;

    public TextStage(SpriteBatch batch) {
        super(new ScreenViewport(), batch);
        labels = new ArrayList<>();
    }

    /**
     * Draws a given text on the screen.
     *
     * @param text     text to draw
     * @param fontPath font to use
     * @param color    color to use
     * @param size     font size to use
     * @param width    width of the text box
     * @param height   height of the text box
     * @param x        x-position in pixel
     * @param y        y-position in pixel
     * @return Label (use this to alter text or remove the text later)
     */
    public Label drawText(String text, String fontPath, Color color, int size, int width, int height, int x, int y) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderWidth = 1;
        parameter.color = color;
        BitmapFont font24 = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font24;
        Label label = new Label(text, labelStyle);
        label.setSize(width, height);
        label.setPosition(x, y);

        labels.add(label);
        this.addActor(label);
        return label;
    }

    /**
     * removes given label from the screen
     *
     * @param label label to remove
     */
    public void removeText(Label label) {
        labels.remove(label);
        clear();
        labels.forEach(l -> addActor(l));
    }

    @Override
    public void draw() {
        super.act();
        super.draw();
    }

    @Override
    public void clear() {
        super.clear();
    }
}
