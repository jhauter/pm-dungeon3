package de.fhbielefeld.pmdungeon.quibble;

import com.badlogic.gdx.Gdx;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.input.InputHandler;
import de.fhbielefeld.pmdungeon.quibble.input.KeyHandler;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
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
	
	/**
	 * Use this level to spawn entities instead of <code>this.entityController</code>!!
	 * This <code>Level</code> object makes it easier to let entities spawn other entities by themselves.
	 */
	private Level currentLevel;
	
	public DungeonStart()
	{
		this.inputHandler = new KeyHandler();
	}
	
	@Override
	protected void setup()
	{
		super.setup();
		this.myHero = new Knight();
		this.inputHandler.addInputListener(myHero);
		Gdx.app.log("GAME", "Setup.");
	}
	
	@Override
	public void onLevelLoad()
	{
		super.onLevelLoad();
		//Clear entities from previous level
		this.entityController.getList().clear();
		//Set current level from the level controller and entity controller
		this.currentLevel = new Level(this.levelController.getDungeon(), this.entityController);
		
		//Spawn the hero at the right spot
		Coordinate startingPoint = this.levelController.getDungeon().getStartingLocation();
		this.myHero.setPosition(startingPoint.getX(), startingPoint.getY());
		this.currentLevel.spawnEntity(this.myHero);
		
		//Set the camera to follow the hero
		this.camera.follow(this.myHero);
		Gdx.app.log("GAME", "Level loaded.");
	}
	
	@Override
	protected void beginFrame()
	{
		super.beginFrame();
		
		//Don't call notify listeners here but internally.
		//updateHandler() should not return anything.
		this.inputHandler.notityListeners(this.inputHandler.updateHandler());
		
		//Check the triggeredNextLevel flag of the player
		if(this.myHero.triggeredNextLevel())
		{
			this.levelController.triggerNextStage();
			this.myHero.onNextLevelEntered();
		}
	}
	
	@Override
	protected void endFrame()
	{
		super.endFrame();
		
		//The Level class has a buffer mechanism to avoid concurrent modification
		//when spawning entities within the Entity.update() function.
		if(this.currentLevel != null && !this.currentLevel.isEntityBufferEmpty())
		{
			this.currentLevel.flushEntityBuffer();
		}
	}
}
