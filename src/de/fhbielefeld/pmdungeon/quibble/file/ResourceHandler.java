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
	
	public static boolean isLoaded(String id)
	{
		DungeonResource<?> res = resources.get(id);
		if(res != null)
		{
			return res.isLoaded();
		}
		return false;
	}
	
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
