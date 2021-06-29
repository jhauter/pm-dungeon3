package de.fhbielefeld.pmdungeon.quibble.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class UILayerCredits extends UILayer
{
	private class CreditsSection
	{
		private Label titleLabel;
		
		private Label[] contentLabels;
		
		private float height;
		
		private final float gapTitle;
		private final float gapLines;
		
		private CreditsSection(String title, String[] lines, float gapTitle, float gapLines)
		{
			this.gapTitle = gapTitle;
			this.gapLines = gapLines;
			this.titleLabel = UILayerCredits.this.addText(title, UIFonts.TITLE, 0, 0);
			this.titleLabel.pack();
			this.height += this.titleLabel.getGlyphLayout().height;
			if(lines.length > 0)
			{
				this.height += gapTitle;
			}
			
			this.contentLabels = new Label[lines.length];
			for(int i = 0; i < lines.length; ++i)
			{
				this.contentLabels[i] = UILayerCredits.this.addText(lines[i], UIFonts.SUBTITLE, 0, 0);
				this.contentLabels[i].pack();
				this.height += this.contentLabels[i].getGlyphLayout().height;
				if(i < lines.length - 1)
				{
					this.height += gapLines;
				}
			}
		}
		
		private void updatePosition(float y)
		{
			float nextY = y;
			
			for(int i = this.contentLabels.length - 1; i >= 0; --i)
			{
				this.contentLabels[i].setY(nextY);
				this.centerLabel(this.contentLabels[i]);
				nextY += this.contentLabels[i].getGlyphLayout().height;
				if(i == 0)
				{
					nextY += this.gapTitle;
				}
				else
				{
					nextY += this.gapLines;
				}
			}
			
			this.titleLabel.setY(nextY);
			this.centerLabel(this.titleLabel);
		}
		
		private void centerLabel(Label label)
		{
			label.setX(Gdx.graphics.getWidth() / 2F - label.getGlyphLayout().width / 2);
		}
	}
	
	private static final String[] textDevelopment = new String[]{
		"Jonathan Hauter",
		"Vivien Traue",
		"Fabius Dölling",
		"Andreas Wegner",
		"Malte Kanders",
		"Mathis Grabert",
		"Adrian Koß",
		"Paul Nagel",
		"Michael Herber"
	};
	
	private static final String[] textDungeonApi = new String[]{
		"André Matutat",
		"Carsten Gips"
	};
	
	private int sectionGap = 200;
	
	private CreditsSection[] sections = new CreditsSection[]{
		new CreditsSection("CREDITS", new String[0], 0, 0),
		new CreditsSection("DEVELOPMENT", textDevelopment, 50, 20),
		new CreditsSection("PM DUNGEON API", textDungeonApi, 50, 20)
	};
	
	private float totalHeight;
	
	private Image background;
	
	private Texture defaultBackground;
	
	private float backgroundAlpha;
	
	private boolean isPlaying;
	
	private int phase = -1;
	
	private float scrollY;
	
	private static final int PHASE_TRANSITION_IN = 0;
	private static final int PHASE_SCROLL = 1;
	private static final int PHASE_TRANSITION_OUT = 2;
	
	public UILayerCredits()
	{
		Pixmap black = new Pixmap(1, 1, Format.RGB888);
		this.defaultBackground = new Texture(black);
		black.dispose();
		this.background = this.addImage(new TextureRegion(this.defaultBackground), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.background.toBack();
		this.totalHeight = this.calculateTotalHeight();
		this.stop();
	}
	
	/**
	 * Starts the credits by transitioning to a black screen, then showing the credits.
	 * If the credits have already started, then this method has no effect.
	 */
	public void play()
	{
		this.isPlaying = true;
		this.phase = PHASE_TRANSITION_IN;
	}
	
	/**
	 * Resets the state of the credits UI layer to the state before starting the credits.
	 * If the credits have not started yet, then this method has no effect.
	 */
	public void stop()
	{
		this.isPlaying = false;
		this.phase = -1;
		this.scrollY = -this.totalHeight - 50.0F;
		this.updateSectionsPosition(this.scrollY);
		this.backgroundAlpha = 0.0F;
		this.background.setColor(1.0F, 1.0F, 1.0F, 0.0F);
	}
	
	/**
	 * @return <code>true</code> if the credits are being shown, <code>false</code> otherwise.
	 */
	public boolean areCreditsPlaying()
	{
		return this.isPlaying;
	}
	
	@Override
	public void draw()
	{
		if(!this.isPlaying)
		{
			return;
		}
		super.draw();
		if(this.phase == PHASE_TRANSITION_IN)
		{
			this.backgroundAlpha += 0.02F;
			if(this.backgroundAlpha >= 1.0F)
			{
				this.backgroundAlpha = 1.0F;
				this.phase = PHASE_SCROLL;
			}
		}
		else if(this.phase == PHASE_SCROLL)
		{
			this.scrollY += 1.5F;
			this.updateSectionsPosition(this.scrollY);
			if(this.scrollY > Gdx.graphics.getHeight() + 50.0F)
			{
				this.phase = PHASE_TRANSITION_OUT;
			}
		}
		else if(this.phase == PHASE_TRANSITION_OUT)
		{
			this.backgroundAlpha -= 0.02F;
			if(this.backgroundAlpha <= 0.0F)
			{
				this.backgroundAlpha = 0.0F;
				this.stop();
			}
		}
		this.background.setColor(1.0F, 1.0F, 1.0F, this.backgroundAlpha);
		this.background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		this.defaultBackground.dispose();
	}
	
	private void updateSectionsPosition(float y)
	{
		for(int i = this.sections.length - 1; i >= 0; --i)
		{
			this.sections[i].updatePosition(y);
			y += this.sections[i].height + this.sectionGap;
		}
	}
	
	private float calculateTotalHeight()
	{
		float h = 0.0F;
		for(int i = 0; i < this.sections.length; ++i)
		{
			h += this.sections[i].height;
			if(i < this.sections.length - 1)
			{
				h += this.sectionGap;
			}
		}
		return h;
	}
	
	@Override
	public boolean handleClick(InputEvent event, float x, float y, int pointer, int button)
	{
		return this.isPlaying;
	}
}
