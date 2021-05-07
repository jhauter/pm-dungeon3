package de.fhbielefeld.pmdungeon.quibble;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.chest.GoldenChest;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Demon;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Goblin;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureStatChangeEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerOpenChestEvent;
import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;
import de.fhbielefeld.pmdungeon.quibble.hud.HUDGroup;
import de.fhbielefeld.pmdungeon.quibble.hud.HUDManager;
import de.fhbielefeld.pmdungeon.quibble.hud.InventoryItemHUD;
import de.fhbielefeld.pmdungeon.quibble.input.DungeonInput;
import de.fhbielefeld.pmdungeon.quibble.input.DungeonInputHandler;
import de.fhbielefeld.pmdungeon.quibble.input.InputHandler;
import de.fhbielefeld.pmdungeon.quibble.input.InputListener;
import de.fhbielefeld.pmdungeon.quibble.inventory.BagInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.interfaces.IEntity;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class DungeonStart extends MainController implements EntityEventHandler, InputListener
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
	
	private HUDManager hudManager;
	
	private Map<String, HUDGroup> shownHUDGroups;
	
	public DungeonStart()
	{
		this.inputHandler = new DungeonInputHandler();
		this.shownHUDGroups = new HashMap<>();
		LoggingHandler.logger.setLevel(Level.CONFIG);
	}
	
	@Override
	protected void setup()
	{
		super.setup();
		this.myHero = new Knight();
		this.myHero.getEquippedItems().addItem(Item.SWORD_BLUE);
		this.myHero.addEntityEventHandler(this);
		this.inputHandler.addInputListener(myHero);
		this.inputHandler.addInputListener(this);
		this.hudManager = new HUDManager();
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
		
		/**** Populate dungeon ****/
		
		for(int i = 0; i < 10; ++i)
		{
			final Point pos = this.currentLevel.getDungeon().getRandomPointInDungeon();
			final Creature toSpawn = this.currentLevel.getRNG().nextInt(2) == 0 ? new Demon() : new Goblin();
			toSpawn.setPosition(pos);
			this.currentLevel.spawnEntity(toSpawn);
		}
		
		/**************************/
		
		//Spawn the hero at the right spot
		Coordinate startingPoint = this.levelController.getDungeon().getStartingLocation();
		this.myHero.setPosition(startingPoint.getX(), startingPoint.getY());
		
		this.currentLevel.spawnEntity(this.myHero);
		
		
		BagInventoryItem<Item, Item> bag =  Item.BAG_DEFAULT.createInventoryItem();
		bag.getBagItems().addItem(Item.POTION_RED_BIG);
		bag.getBagItems().addItem(Item.SWORD_BLUE);
		bag.getBagItems().addItem(Item.POTION_YELLOW_BIG);
		this.myHero.getEquippedItems().addItem(bag);
		

		this.showInventory("inv", this.myHero.getInventory(), 32, 92);
		this.showInventory("eqip", this.myHero.getEquippedItems(), 250, 16);
		
		
		/**
		 * Spawn Chest's
		 */
		final int  num = currentLevel.getRNG().nextInt(1) + 1;
		for (int i = 0; i < num; i++) {
			final Point pos2 = this.currentLevel.getDungeon().getRandomPointInDungeon();
//			goldenChest = new GoldenChest(pos2.x, pos2.y);
			this.currentLevel.spawnEntity(new GoldenChest(pos2.x, pos2.y));
			LoggingHandler.logger.log(Level.INFO, "New Chest added.");
		}
		
		//Set the camera to follow the hero
		this.camera.follow(this.myHero);
		LoggingHandler.logger.log(Level.INFO, "New level loaded.");
	}
	

	@Override
	protected void beginFrame()
	{
		super.beginFrame();
		
		ResourceHandler.processQueue();
		
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
		
		this.hudManager.update();
		
		this.currentLevel.getParticleSystem().draw(this.camera.position.x, this.camera.position.y);
		
		//Controls HUD
		final DungeonResource<Texture> res = ResourceHandler.requestResourceInstantly("assets/textures/hud/controls.png", ResourceType.TEXTURE);
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
		batch.draw(res.getResource(), 0, 0);
		batch.end();
		batch.flush();
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
				if(statEvent.getNewValue() <= 0.0D)
				{
					LoggingHandler.logger.log(Level.INFO, "Game over!");
				}
			}
		}
		else if(event.getEventID() == Creature.EVENT_ID_HIT_TARGET_POST)
		{
			final CreatureHitTargetPostEvent hitEvent = (CreatureHitTargetPostEvent)event;
			if(hitEvent.getTarget().getCurrentHealth() <= 0.0D && hitEvent.getTarget().getDeadTicks() == 0)
			{
				//Dead ticks must be 0 so that this doesn't get triggered
				//when the enemy is hit when it's already dead
				LoggingHandler.logger.log(Level.INFO, "Killed " + hitEvent.getTarget().getClass().getSimpleName());
				hitEvent.getEntity().heal(1.0D);
				LoggingHandler.logger.log(Level.INFO, "Healed by 1.0");
			}
			
		}
		else if(event.getEventID() == PlayerOpenChestEvent.EVENT_ID)
		{
			final PlayerOpenChestEvent chestEvent = (PlayerOpenChestEvent)event;
			
			this.showInventory("chest", chestEvent.getChest().getInv(), 32, 300);
		}
	}
	
	private void showInventory(String id, Inventory<Item> inv, int x, int y)
	{
		this.closeInventory(id);
		HUDGroup g = this.generateInventoryHUD(inv, x, y);
		this.hudManager.addGroup(g);
		this.shownHUDGroups.put(id, g);
	}
	
	private void closeInventory(String id)
	{
		HUDGroup g = this.shownHUDGroups.get(id);
		if(g != null)
		{
			this.hudManager.removeGroup(g);
			this.hudManager.setElementOnMouse(null);
		}
	}
	
	@SuppressWarnings("unchecked")
	private HUDGroup generateInventoryHUD(Inventory<Item> inv, int x, int y)
	{
		HUDGroup g = new HUDGroup();
		this.hudManager.setElementOnMouse(null);
		int nextItemX = x;
		for(int i = 0; i < inv.getCapacity(); ++i)
		{
			if(inv.getItem(i) instanceof BagInventoryItem<?, ?>)
			{
				g.addHUDElement(new InventoryItemHUD(inv, i, nextItemX, y, 64, 64));
				nextItemX += 72;
				
				BagInventoryItem<?, ?> bag = (BagInventoryItem<?, ?>)inv.getItem(i);
				Inventory<?> bagItems = bag.getBagItems();
				for(int j = 0; j < bagItems.getCapacity(); ++j)
				{
					g.addHUDElement(new InventoryItemHUD((Inventory<Item>)bagItems, j, nextItemX, y + 32 * (j % 2), 32, 32));
					nextItemX += 36 * (j % 2);
				}
			}
			else
			{
				g.addHUDElement(new InventoryItemHUD(inv, i, nextItemX, y, 64, 64));
				nextItemX += 72;
			}
		}
		return g;
	}

	@Override
	public void onInputRecieved(DungeonInput key)
	{
		if(key == DungeonInput.CLOSE)
		{
			this.closeInventory("chest");
		}
	}
}
