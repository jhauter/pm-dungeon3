package de.fhbielefeld.pmdungeon.quibble.file;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;

public class ResourceHandler
{
	private static class LoadingData
	{
		private final String path;
		private final DungeonResource<?> resourceInstance;
		
		public LoadingData(String path, DungeonResource<?> resourceInstance)
		{
			this.path = path;
			this.resourceInstance = resourceInstance;
		}
	}
	
	private static Map<String, DungeonResource<?>> resources = new HashMap<>();
	
	private static Deque<LoadingData> loadingQueue = new ArrayDeque<>();
	
	/**
	 * Returns a {@link DungeonResource} of the specified path.
	 * The <code>type</code> parameter determines the type of the returned <code>DungeonResource</code>.
	 * If the resource with the specified path has not been loaded yet and is is not already in the loading queue,
	 * this will return a resource whose loading state can be observed to get access to the resource when it is loaded.
	 * If the resource has been loaded yet, then this will return the already loaded resource.
	 * So this can be called over again to get the same resource.
	 * @param <T> the class of the actual resource
	 * @param path the path where the resource needs to be loaded from
	 * @param type the type of the resource which determines the return type
	 * @return a resource container that can be used to observe the loading state and to access the actual resource
	 * when loading is done
	 */
	@SuppressWarnings("unchecked")
	public static <T> DungeonResource<T> requestResource(String path, ResourceType type)
	{
		DungeonResource<?> res = resources.get(path);
		if(res != null)
		{
			return (DungeonResource<T>)res;
		}
		res = type.newResourceInstance();
		loadingQueue.push(new LoadingData(path, res));
		return (DungeonResource<T>)res;
	}
	
	/**
	 * Returns a {@link DungeonResource} of the specified path.
	 * The <code>type</code> parameter determines the type of the returned <code>DungeonResource</code>.
	 * This will not put the resource into the loading queue but instead load it immediately.
	 * Do not use this if the same resource is already in the loading queue.
	 * @param <T> the class of the actual resource
	 * @param path the path where the resource needs to be loaded from
	 * @param type the type of the resource which determines the return type
	 * @return a resource container that can be used to access the actual resource
	 * when loading is done
	 */
	@SuppressWarnings("unchecked")
	public static <T> DungeonResource<T> requestResourceInstantly(String path, ResourceType type)
	{
		DungeonResource<?> res = resources.get(path);
		if(res != null)
		{
			return (DungeonResource<T>)res;
		}
		res = type.newResourceInstance();
		loadResource(res, path);
		return (DungeonResource<T>)res;
	}
	
	/**
	 * Checks whether a resource on the specified path has been loaded yet.
	 * @param id the path
	 * @return whether the specified resource has been loaded yet
	 */
	public static boolean isLoaded(String id)
	{
		DungeonResource<?> res = resources.get(id);
		if(res != null)
		{
			return res.isLoaded();
		}
		return false;
	}
	
	/**
	 * Loads all resources currently in the queue and thus empties it.
	 */
	public static void processQueue()
	{
		LoadingData currentObj;
		while(!loadingQueue.isEmpty())
		{
			currentObj = loadingQueue.pop();
			loadResource(currentObj.resourceInstance, currentObj.path);
		}
	}
	
	private static void loadResource(DungeonResource<?> resourceInstance, String path)
	{
		try
		{
			resourceInstance.load(path);
		}
		catch(Exception e)
		{
			LoggingHandler.logger.log(Level.SEVERE, "Failed to load a resource: " + e);
		}
		//Put it in, even if exceptions are thrown because programs can get the resource and check with hasError()
		resources.put(path, resourceInstance);
	}
}
