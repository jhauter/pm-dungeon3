package de.fhbielefeld.pmdungeon.quibble;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;

public class DungeonStart extends MainController
{
	public static void main(String[] args)
	{
		DesktopLauncher.run(new DungeonStart());
	}
	
	/****************************************
	 *                GAME                  *
	 ****************************************/
	
	private Entity myHero;
	
	public DungeonStart()
	{
		
	}
	
	@Override
	protected void setup()
	{
		super.setup();
	}
	
	@Override
	public void onLevelLoad()
	{
		super.onLevelLoad();
		this.myHero = new Knight();
		this.addEntityToLevel(this.myHero);
		this.camera.follow(this.myHero);
		Gdx.app.log("GAME", "Level loaded.");
	}
	
	@Override
	protected void beginFrame()
	{
		super.beginFrame();
		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			this.myHero.setVelocity(-0.1F, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			this.myHero.setVelocity(+0.1F, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			this.myHero.setVelocity(0, +0.1F);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			this.myHero.setVelocity(0, -0.1F);
		}
	}
	
	@Override
	protected void endFrame()
	{
		super.endFrame();
	}
	
	/**
	 * This is the preferred way to add entities to a level.
	 * This ensures that all entity resources are loaded and world reference of the entity is set correctly.
	 * @param entity the entity to add to the level
	 */
	public void addEntityToLevel(Entity entity)
	{
		try
		{
			entity.loadResources();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return;
			//Don't add entity if an error occurs
		}
		this.entityController.addEntity(entity);
		entity.onSpawn(this.levelController.getDungeon());
	}
}
