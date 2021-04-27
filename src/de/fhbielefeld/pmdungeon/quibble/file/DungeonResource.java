package de.fhbielefeld.pmdungeon.quibble.file;

/**
 * This interface represents a loadable resource.
 * The resource can be anything, for example a texture.
 * @author Andreas
 *
 * @param <T> the resource type
 */
public interface DungeonResource<T>
{
	/**
	 * @return the resource object if it has been loaded
	 */
	public T getResource();
	
	/**
	 * @return whether the resource has been successfully loaded
	 */
	public boolean isLoaded();
	
	/**
	 * Returns whether an error has occurred when loading. If this returns <code>true</code> then
	 * {@link #isLoaded()} should not return <code>true</code>.
	 * @return whether an error has occurred when loading
	 */
	public boolean hasError();
	
	/**
	 * Loads the resource. Exceptions should not be caught as the <code>ResourceHandler</code> uses the exception
	 * to log the exception message.
	 * @param path the path to the resource
	 * @throws Exception if any error occurs while loading
	 */
	public void load(String path) throws Exception;
}
