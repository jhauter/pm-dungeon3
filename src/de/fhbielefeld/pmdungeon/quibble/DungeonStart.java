package de.fhbielefeld.pmdungeon.quibble;

import java.util.List;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureStatChangeEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.input.DungeonInputHandler;
import de.fhbielefeld.pmdungeon.quibble.input.InputHandler;
import de.fhbielefeld.pmdungeon.quibble.particle.ParticleSystem;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;

public class DungeonStart extends MainController implements EntityEventHandler
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
	
	/**
	 * Use this level to spawn entities instead of <code>this.entityController</code>!!
	 * This <code>Level</code> object makes it easier to let entities spawn other entities by themselves.
	 */
	private DungeonLevel currentLevel;
	
	private long lastFrameTimeStamp;
	
	public DungeonStart()
	{
		this.inputHandler = new DungeonInputHandler();
		LoggingHandler.logger.setLevel(Level.CONFIG);
	}
	
	@Override
	protected void setup()
	{
		super.setup();
		this.myHero = new Knight();
		this.myHero.addEntityEventHandler(this);
		this.inputHandler.addInputListener(myHero);
		ParticleSystem.loadTextures();
		this.lastFrameTimeStamp = System.currentTimeMillis();
		Gdx.app.getGraphics().setResizable(true);
		LoggingHandler.logger.log(Level.INFO, "Setup done.");
	}
	
	@Override
	public void onLevelLoad()
	{
		super.onLevelLoad();
		//Clear entities from previous level
		this.entityController.getList().clear();
		//Set current level from the level controller and entity controller
		this.currentLevel = new DungeonLevel(this.levelController.getDungeon(), this.entityController);
		
		//Spawn the hero at the right spot
		Coordinate startingPoint = this.levelController.getDungeon().getStartingLocation();
		this.myHero.setPosition(startingPoint.getX(), startingPoint.getY());
		
		this.currentLevel.spawnEntity(this.myHero);
		
		//Set the camera to follow the hero
		this.camera.follow(this.myHero);
		LoggingHandler.logger.log(Level.INFO, "New level loaded.");
	}
	
	@Override
	protected void beginFrame()
	{
		super.beginFrame();
		
		//Don't call notify listeners here but internally.
		//updateHandler() should not return anything.
		this.inputHandler.updateHandler();
		
		//Check the triggeredNextLevel flag of the player
		if(this.myHero.triggeredNextLevel())
		{
			this.levelController.triggerNextStage();
			this.myHero.onNextLevelEntered();
			
			LoggingHandler.logger.log(Level.INFO, "Player entered new level.");
		}
		
		this.currentLevel.getParticleSystem().update((System.currentTimeMillis() - this.lastFrameTimeStamp) / 1000.0F);
		this.lastFrameTimeStamp = System.currentTimeMillis();
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
		
		List<IEntity> entityList = this.entityController.getList();
		Entity currentEntity;
		for(int i = 0; i < entityList.size(); ++i)
		{
			currentEntity = (Entity)entityList.get(i);
			if(currentEntity.deleteableWorkaround())
			{
				entityList.remove(i);
				--i;
			}
		}
		
		this.currentLevel.getParticleSystem().draw(this.camera.position.x, this.camera.position.y);
	}

	@Override
	public void handleEvent(EntityEvent event) //There are events for myHero
	{
		if(event.getEventID() == Creature.EVENT_ID_STAT_CHANGE)
		{
			final CreatureStatChangeEvent statEvent = (CreatureStatChangeEvent)event;
			final Creature hero = statEvent.getEntity(); //In this case its hero
			if(statEvent.getStat() == CreatureStatsAttribs.HEALTH)
			{
				LoggingHandler.logger.log(Level.INFO, "Health [" + statEvent.getNewValue() + "/" + hero.getMaxHealth() + "]");
			}
			
		}
		
	}
}
