package de.fhbielefeld.pmdungeon.quibble.file;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TextureRegionResource implements DungeonResource<TextureRegion>
{
	private TextureRegion region;
	
	private boolean error;
	
	/**
	 * Creates a new <code>TextureRegionResource</code> that represents a texture region.
	 */
	protected TextureRegionResource()
	{
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public TextureRegion getResource()
	{
		return this.region;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLoaded()
	{
		return this.region != null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasError()
	{
		return this.error;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load(String path)
	{
		try
		{
			this.region = new TextureRegion(new Texture(path));
		}
		catch(GdxRuntimeException e)
		{
			this.error = true;
			throw e;
		}
	}
}
