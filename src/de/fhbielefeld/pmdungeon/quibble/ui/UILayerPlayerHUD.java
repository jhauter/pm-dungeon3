package de.fhbielefeld.pmdungeon.quibble.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;

public class UILayerPlayerHUD extends UILayer
{
	private Label labelLevel;
	private Label labelHealth;
	private ProgressBar barLevel;
	private ProgressBar barHealth;
	
	private Image playerPortrait;
	
	private Player player;
	
	public UILayerPlayerHUD()
	{
		DungeonResource<Texture> charTex = ResourceHandler.requestResourceInstantly("assets/textures/hud/char.png", ResourceType.TEXTURE);
		
		if(!charTex.isLoaded())
		{
			return;
		}
		
		this.playerPortrait = new Image();
		this.playerPortrait.setBounds(26, 10, 76, 76);
		this.getStage().addActor(this.playerPortrait);
		this.addImage(new TextureRegion(charTex.getResource(), 0, 0, 320, 128), 16, 0, 320, 128);
		
		Pixmap transparent = new Pixmap(1, 1, Format.RGBA8888);
		transparent.setColor(0.0F, 0.0F, 0.0F, 0.0F);
		transparent.fill();
		Texture transparentTexture = new Texture(transparent);
		
		ProgressBarStyle lvStyle = new ProgressBarStyle();
		lvStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(charTex.getResource(), 320, 0, 10, 10));
		lvStyle.knob = new TextureRegionDrawable(transparentTexture);
		
		this.barLevel = new ProgressBar(0.0F, 1.0F, 0.05F, false, lvStyle);
		this.barLevel.setPosition(116, 15);
		this.barLevel.setValue(1F);
		this.barLevel.setWidth(200);
		this.getStage().addActor(this.barLevel);
		
		ProgressBarStyle healthStyle = new ProgressBarStyle();
		healthStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(charTex.getResource(), 330, 0, 21, 21));
		healthStyle.knob = new TextureRegionDrawable(transparentTexture);
		
		this.barHealth = new ProgressBar(0.0F, 1.0F, 0.05F, false, healthStyle);
		this.barHealth.setPosition(116, 50);
		this.barHealth.setValue(1F);
		this.barHealth.setWidth(200);
		this.getStage().addActor(this.barHealth);
		
		this.labelLevel = this.addText("Lv. 1", UIFonts.TITLE, 32, 10);
		this.labelHealth = this.addText("Health", UIFonts.DEFAULT, 160, 41);
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
		DungeonResource<Texture> res = ResourceHandler.requestResourceInstantly(player.getPortraitImagePath(), ResourceType.TEXTURE);
		if(res.isLoaded())
		{
			this.playerPortrait.setDrawable(new TextureRegionDrawable(res.getResource()));
		}
	}
	
	@Override
	public void draw()
	{
		super.draw();
		if(this.player == null)
		{
			return;
		}
		if(this.barHealth != null)
		{
			this.barHealth.setValue((float)Math.max(0, this.player.getCurrentHealth() / this.player.getMaxHealth()));
		}
		if(this.barLevel != null)
		{
			int nextLevelExp = this.player.totalExpFunction(this.player.getCurrentExpLevel() + 1);
			int currentLevelExp = this.player.totalExpFunction(this.player.getCurrentExpLevel());
			int progress = this.player.getTotalExp() - currentLevelExp;
			if(nextLevelExp - currentLevelExp > 0)
			{
				this.barLevel.setValue((float)progress / (nextLevelExp - currentLevelExp));
			}
		}
		this.labelLevel.setText("Lv. " + this.player.getCurrentExpLevel());
		this.labelHealth.setText("Health " + (int)this.player.getCurrentHealth() + " / " + (int)this.player.getMaxHealth());
	}
}
