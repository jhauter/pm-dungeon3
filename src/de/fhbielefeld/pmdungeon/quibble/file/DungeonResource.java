package de.fhbielefeld.pmdungeon.quibble.file;

public interface DungeonResource<T>
{
	public T getResource();
	
	public boolean isLoaded();
	
	public boolean hasError();
	
	public void load(String path) throws Exception;
}
