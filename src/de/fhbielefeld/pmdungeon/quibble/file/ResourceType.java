package de.fhbielefeld.pmdungeon.quibble.file;

import java.util.function.Supplier;

public enum ResourceType
{
	/**
	 * Represents a texture resource.
	 */
	TEXTURE(TextureResource::new);
	
	private final Supplier<DungeonResource<?>> resourceClassGenerator;
	
	private ResourceType(Supplier<DungeonResource<?>> resourceClassGenerator)
	{
		this.resourceClassGenerator = resourceClassGenerator;
	}
	
	/**
	 * @return a new instance of the resource type
	 */
	protected DungeonResource<?> newResourceInstance()
	{
		return this.resourceClassGenerator.get();
	}
}
