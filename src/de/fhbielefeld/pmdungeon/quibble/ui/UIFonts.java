package de.fhbielefeld.pmdungeon.quibble.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public enum UIFonts
{
	TITLE("assets/textures/font/arial.ttf", 24),
	SUBTITLE("assets/textures/font/arial.ttf", 20),
	DEFAULT("assets/textures/font/arial.ttf", 16);
	
	static
	{
		loadFonts();
	}
	
	private static boolean isLoaded;
	
	private static void loadFonts()
	{
		if(isLoaded)
		{
			throw new IllegalStateException("fonts are already loaded");
		}
		FreeTypeFontGenerator fontGenerator;
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		for(UIFonts font : values())
		{
			fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal(font.ttfPath));
			param.size = font.loadSize;
			param.borderWidth = 1.0F;
			
			font.font = fontGenerator.generateFont(param);
			
			fontGenerator.dispose();
		}
		isLoaded = true;
	}
	
	private BitmapFont font;
	
	private final String ttfPath;
	
	private final int loadSize;
	
	private UIFonts(String ttfPath, int size)
	{
		this.ttfPath = ttfPath;
		this.loadSize = size;
	}
	
	public String getTTFPath()
	{
		return this.ttfPath;
	}
	
	public int getSizeLoaded()
	{
		return this.loadSize;
	}
	
	public BitmapFont getFont()
	{
		return this.font;
	}
}
