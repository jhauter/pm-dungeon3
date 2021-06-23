package de.fhbielefeld.pmdungeon.quibble.boss.battle;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;
import de.fhbielefeld.pmdungeon.quibble.ui.UIFonts;
import de.fhbielefeld.pmdungeon.quibble.ui.UILayer;

public class UIBossBar extends UILayer {
    private Label labelHealth;
    private ProgressBar barHealth;
    private NPC boss;

    public UIBossBar()
    {
        int barWidth = 256;
        int barHeight = 16;
        int posX = 220;
        int posY = 450;

        DungeonResource<Texture> charTex = ResourceHandler.requestResourceInstantly("assets/textures/hud/expbar.png", ResourceType.TEXTURE);

        if(!charTex.isLoaded())
        {
            return;
        }

        this.addImage(new TextureRegion(charTex.getResource(), 0, 0, barWidth, barHeight), posX, posY, barWidth, barHeight);

        Pixmap transparent = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        transparent.setColor(0.0F, 0.0F, 0.0F, 0.0F);
        transparent.fill();
        Texture transparentTexture = new Texture(transparent);

        ProgressBar.ProgressBarStyle healthStyle = new ProgressBar.ProgressBarStyle();
        healthStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(charTex.getResource(), 330, 0, 21, 16));
        healthStyle.knob = new TextureRegionDrawable(transparentTexture);
        //
        this.barHealth = new ProgressBar(0.0F, 1.0F, 0.05F, false, healthStyle);
        this.barHealth.setPosition(posX+2, posY);
        this.barHealth.setValue(1F);
        this.barHealth.setWidth(barWidth-2);
        this.getStage().addActor(this.barHealth);

        this.labelHealth = this.addText("Boss", UIFonts.DEFAULT, 230, 445);
    }

    public void setBoss(NPC boss)
    {
        this.boss = boss;
    }

    @Override
    public void draw()
    {
        super.draw();
        if(this.boss == null)
        {
            return;
        }
        if(this.barHealth != null)
        {
            this.barHealth.setValue((float)Math.max(0, this.boss.getCurrentHealth() / this.boss.getMaxHealth()));
        }
        System.out.println(this.boss.getCurrentHealth());

        this.labelHealth.setText(this.boss.getDisplayName()+" " + (int)this.boss.getCurrentHealth() + " / " + (int)this.boss.getMaxHealth());
    }
}
