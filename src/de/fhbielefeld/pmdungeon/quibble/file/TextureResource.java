package de.fhbielefeld.pmdungeon.quibble.file;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TextureResource implements DungeonResource<Texture>
{
	private Texture texture;
	
	private boolean error;
	
	/**
	 * Creates a new <code>TextureResource</code> that represents a texture.
	 */
	protected TextureResource()
	{
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Texture getResource()
	{
		return this.texture;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLoaded()
	{
		return this.texture != null;
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
			this.texture = new Texture(path);
		}
		catch(GdxRuntimeException e)
		{
			this.error = true;
			throw e;
		}
	}
}
