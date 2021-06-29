package de.fhbielefeld.pmdungeon.quibble.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class UILayerGameOverScreen extends UILayer
{
	private Image overlayImage;
	private Image bgImage;
	
	private Texture bgTexture;
	
	private float overlayAlpha;
	private float bgAlpha;
	
	private int phase;

	private static final int PHASE_TRANSITION_OVERLAY = 0;
	private static final int PHASE_TRANSITION_COLOR = 1;
	
	public UILayerGameOverScreen(Color fillColor)
	{
		Pixmap color = new Pixmap(1, 1, Format.RGB888);
		color.setColor(fillColor);
		color.fill();
		this.bgTexture = new Texture(color);
		color.dispose();
		this.bgImage = this.addImage(new TextureRegion(this.bgTexture), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.overlayImage = this.addImage("assets/textures/hud/game_over.png", 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.overlayImage.setColor(1.0F, 1.0F, 1.0F, 0.0F);
		this.bgImage.setColor(1.0F, 1.0F, 1.0F, 0.0F);
		this.reset();
	}
	
	public void play()
	{
		this.phase = PHASE_TRANSITION_OVERLAY;
	}
	
	public void reset()
	{
		this.phase = -1;
		this.overlayAlpha = 0.0F;
		this.bgAlpha = 0.0F;
	}
	
	@Override
	public void draw()
	{
		super.draw();
		if(this.phase == PHASE_TRANSITION_OVERLAY)
		{
			this.overlayAlpha += 0.04F * this.overlayAlpha + 0.001F;
			if(this.overlayAlpha >= 1.0F)
			{
				this.overlayAlpha = 1.0F;
				this.phase = PHASE_TRANSITION_COLOR;
			}
		}
		else if(this.phase == PHASE_TRANSITION_COLOR)
		{
			this.bgAlpha += 0.025F;
			if(this.bgAlpha >= 1.0F)
			{
				this.bgAlpha = 1.0F;
				this.phase = -1;
			}
		}
		this.bgImage.setColor(1.0F, 1.0F, 1.0F, this.bgAlpha);
		this.overlayImage.setColor(1.0F, 1.0F, 1.0F, this.overlayAlpha);
		this.bgImage.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.overlayImage.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		this.bgTexture.dispose();
	}
	
	@Override
	public boolean handleClick(InputEvent event, float x, float y, int pointer, int button)
	{
		return this.phase != -1;
	}
}
