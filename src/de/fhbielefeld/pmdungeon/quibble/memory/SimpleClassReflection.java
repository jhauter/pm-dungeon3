package de.fhbielefeld.pmdungeon.quibble.memory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SimpleClassReflection
{
	
	Class<?> newClass = null;
	Constructor<?> cTor = null;
	
	/**
	 * Class to outsource Reflection Constructor will be init by creating this
	 * class.
	 * 
	 * @param newClass TypeName of Class, which should be reflected
	 * @param accessible true to use private Methods 
	 */
	public SimpleClassReflection(String newClass, boolean accessible)
	{
		try
		{
			this.newClass = Class.forName(newClass);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		initConstructor(accessible);
	}
	
	public Method getParameterMethod(String methodName, boolean accessible, Class<?>... parameterTypes)
	{
		Method method = null;
		try
		{
			method = newClass.getMethod(methodName, parameterTypes);
		}
		catch(NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		method.setAccessible(accessible);
		return method;
	}
	
	public Method getMethod(String methodName, boolean accessible)
	{
		Method method = null;
		try
		{
			method = newClass.getMethod(methodName);
		}
		catch(NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		method.setAccessible(accessible);
		return method;
	}
	
	private void initConstructor(boolean accessible)
	{
		try
		{
			this.cTor = newClass.getDeclaredConstructor();
		}
		catch(NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		this.cTor.setAccessible(accessible);
	}
	
	public Constructor<?> getConstructor()
	{
		return cTor;
	}
	
	public Class<?> getNewClass()
	{
		return newClass;
	}
	
}
