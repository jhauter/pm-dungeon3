package de.fhbielefeld.pmdungeon.quibble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.LookingDirection;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.WalkingDirection;
import de.fhbielefeld.pmdungeon.quibble.input.InputHandler;
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
	
	private InputHandler inputHandler;
	
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
//		this.inputHandler.addInputListener(this.myHero);
		this.camera.follow(this.myHero);
		Gdx.app.log("GAME", "Level loaded.");
	}
	
	@Override
	protected void beginFrame()
	{
		super.beginFrame();
		this.listener = new KeyHandler();
		listener.addInputListener(myHero);
		listener.notityListeners(listener.updateHandler());
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
