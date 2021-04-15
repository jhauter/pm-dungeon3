package de.fhbielefeld.pmdungeon.quibble;

import com.badlogic.gdx.Gdx;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.input.InputHandler;
import de.fhbielefeld.pmdungeon.quibble.input.KeyHandler;
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
	
	private Player myHero;
	
	KeyHandler handler;
	
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
		handler = new KeyHandler();
		handler.addInputListener(myHero);
		this.camera.follow(this.myHero);
		Gdx.app.log("GAME", "Level loaded.");
	}
	
	@Override
	protected void beginFrame()
	{
		super.beginFrame();
		
		handler.notityListeners(handler.updateHandler());
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
		if(!entity.loadResources())
		{
			//Don't add entity if an error occurs
			return;
		}
		this.entityController.addEntity(entity);
		entity.onSpawn(this.levelController.getDungeon());
	}
}
