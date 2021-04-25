package de.fhbielefeld.pmdungeon.quibble.file;

import java.util.function.Supplier;

public enum ResourceType
{
	TEXTURE(TextureResource::new);
	
	private final Supplier<DungeonResource<?>> resourceClassGenerator;
	
	private ResourceType(Supplier<DungeonResource<?>> resourceClassGenerator)
	{
		this.resourceClassGenerator = resourceClassGenerator;
	}
	
	public DungeonResource<?> newResourceInstance()
	{
		return this.resourceClassGenerator.get();
	}
}
