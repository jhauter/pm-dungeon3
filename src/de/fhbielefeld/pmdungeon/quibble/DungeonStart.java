package de.fhbielefeld.pmdungeon.quibble;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.chest.GoldenChest;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Demon;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.Lizard;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureExpEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureStatChangeEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerOpenChestEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerQuestsChangedEvent;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.hud.ExpBarHUD;
import de.fhbielefeld.pmdungeon.quibble.hud.HUDGroup;
import de.fhbielefeld.pmdungeon.quibble.hud.HUDManager;
import de.fhbielefeld.pmdungeon.quibble.hud.HealthDisplayHUD;
import de.fhbielefeld.pmdungeon.quibble.hud.InventoryHUDSwitchListener;
import de.fhbielefeld.pmdungeon.quibble.hud.InventoryItemHUD;
import de.fhbielefeld.pmdungeon.quibble.hud.QuestHUD;
import de.fhbielefeld.pmdungeon.quibble.input.DungeonInput;
import de.fhbielefeld.pmdungeon.quibble.input.DungeonInputHandler;
import de.fhbielefeld.pmdungeon.quibble.input.InputHandler;
import de.fhbielefeld.pmdungeon.quibble.input.InputListener;
import de.fhbielefeld.pmdungeon.quibble.inventory.BagInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestDummy;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestTypes;
import de.fhbielefeld.pmdungeon.quibble.trap.TrapHealth;
import de.fhbielefeld.pmdungeon.quibble.trap.TrapTeleport;
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

	public static final String INV_NAME_DEFAULT = "inv";
	public static final String INV_NAME_EQUIP = "equip";
	public static final String INV_NAME_CHEST = "chest";
	
	public static final String FONT_ARIAL = "assets/textures/font/arial.ttf";

	private InventoryHUDSwitchListener invSwitchNormal;
	private InventoryHUDSwitchListener invSwitchEquip;
	
	private Player myHero;
	
	private InputHandler inputHandler;
	
	/**
	 * Use this level to spawn entities instead of <code>this.entityController</code>!!
	 * This <code>Level</code> object makes it easier to let entities spawn other entities by themselves.
	 */
	private DungeonLevel currentLevel;
	
	private long lastFrameTimeStamp;
	
	private HUDManager hudManager;
	
	private ExpBarHUD expBarHUD;
	private HealthDisplayHUD healthHUD;
	
	private QuestHUD questHUD;
	
	private Label expLabel;
	private Label healthLabel;
	
	private Map<String, HUDGroup> shownHUDGroups;
	private Map<String, Label> shownLabels;
	
	public DungeonStart()
	{
		this.inputHandler = new DungeonInputHandler();
		this.shownHUDGroups = new HashMap<>();
		this.shownLabels = new HashMap<>();
		LoggingHandler.logger.setLevel(Level.CONFIG);
	}
	
	@Override
	protected void setup()
	{
		super.setup();
		this.myHero = new Knight();
		this.myHero.getEquippedItems().addItem(Item.SWORD_BLUE);
		this.myHero.addEntityEventHandler(this);
		this.invSwitchNormal = new InventoryHUDSwitchListener(this, INV_NAME_DEFAULT, this.myHero.getInventory(), "Inventory", 16, 176);
		this.invSwitchEquip = new InventoryHUDSwitchListener(this, INV_NAME_EQUIP, this.myHero.getEquippedItems(), "Equipment", 16, 76);
		this.myHero.getInventory().addInventoryListener(this.invSwitchNormal);
		this.myHero.getEquippedItems().addInventoryListener(this.invSwitchEquip);
		this.expBarHUD = new ExpBarHUD(this.myHero, 16, 432);
		this.healthHUD = new HealthDisplayHUD(this.myHero, 16, 368);
		this.inputHandler.addInputListener(myHero);
		this.inputHandler.addInputListener(this);
		this.hudManager = new HUDManager();
		this.hudManager.addElement(this.expBarHUD);
		this.hudManager.addElement(this.healthHUD);
		this.lastFrameTimeStamp = System.currentTimeMillis();
		Gdx.app.getGraphics().setResizable(false);
		
		this.expLabel = this.textHUD.drawText("Level: 0", FONT_ARIAL, Color.WHITE, 24, 200, 64, 16, 432);
		this.healthLabel = this.textHUD.drawText("Health", FONT_ARIAL, Color.WHITE, 24, 200, 64, 16, 384);
		
		this.questHUD = new QuestHUD(400, 400, this.textHUD);
		this.questHUD.setQuests(this.myHero.getQuestList());
		this.hudManager.addElement(questHUD);
		
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
			final Creature toSpawn = this.currentLevel.getRNG().nextInt(2) == 0 ? new Demon() : new Lizard();
			toSpawn.setPosition(pos);
			this.currentLevel.spawnEntity(toSpawn);
		}
		
		/**************************/
		
		//Spawn the hero at the right spot
		Coordinate startingPoint = this.levelController.getDungeon().getStartingLocation();
		this.myHero.setPosition(startingPoint.getX(), startingPoint.getY());
		
		this.currentLevel.spawnEntity(this.myHero);
		
		this.invSwitchNormal.show();
		this.invSwitchEquip.show();
		
		/**
		 * Spawn Chest's
		 */
		final int  num = currentLevel.getRNG().nextInt(1) + 1;
		for (int i = 0; i < num; i++) {
			final Point pos2 = this.currentLevel.getDungeon().getRandomPointInDungeon();
			this.currentLevel.spawnEntity(new GoldenChest(pos2.x, pos2.y));
			LoggingHandler.logger.log(Level.INFO, "New Chest added.");
		}
		
		// Placing a new Trap
		
		final Point pos3 = this.currentLevel.getDungeon().getRandomPointInDungeon();
		this.currentLevel.spawnEntity(currentLevel.getRNG().nextInt(2) == 0 ? new TrapTeleport(pos3.x, pos3.y, true) : new TrapHealth(pos3.x, pos3.y, 2, true));
	
		final Point pos4 = this.currentLevel.getDungeon().getRandomPointInDungeon();
		QuestTypes type = QuestTypes.values()[this.currentLevel.getRNG().nextInt(QuestTypes.values().length)];
		this.currentLevel.spawnEntity(new QuestDummy(type, pos4.x, pos4.y));
		
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
		
		SpriteBatch entityCustomRenderBatch = new SpriteBatch();
		List<IEntity> entityList = this.entityController.getList();
		Entity currentEntity;
		
		entityCustomRenderBatch.begin();
		
		for(int i = 0; i < entityList.size(); ++i)
		{
			currentEntity = (Entity)entityList.get(i);
			if(currentEntity.deleteableWorkaround())
			{
				entityList.remove(i);
				--i;
			}
			else if(!currentEntity.isInvisible())
			{
				currentEntity.doCustomRendering(entityCustomRenderBatch,
					DrawingUtil.dungeonToScreenXCam(currentEntity.getX(), this.camera.position.x),
					DrawingUtil.dungeonToScreenYCam(currentEntity.getY(), this.camera.position.y));
			}
		}
		entityCustomRenderBatch.end();
		entityCustomRenderBatch.flush();
		
		this.hudManager.update();
		
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
			
			this.showInventory(INV_NAME_CHEST, chestEvent.getChest().getInv(), "Chest", 16, 288);
		}
		else if(event.getEventID() == Creature.EVENT_ID_EXP_CHANGE)
		{
			final CreatureExpEvent expEvent = (CreatureExpEvent)event;

			int oldLevel = expEvent.getEntity().expLevelFunction(expEvent.getPreviousTotalExp());
			int newLevel = expEvent.getEntity().expLevelFunction(expEvent.getNewTotalExp());
			if(newLevel > oldLevel)
			{
				LoggingHandler.logger.log(Level.INFO, "New Level: " + newLevel);
				this.expLabel.setText("Level: " + newLevel);
			}
		}
		else if(event.getEventID() == PlayerQuestsChangedEvent.EVENT_ID)
		{
			this.questHUD.refreshQuests();
		}
	}
	
	public void showInventory(String id, Inventory<Item> inv, String name, int x, int y)
	{
		this.closeInventory(id);
		HUDGroup g = this.generateInventoryHUD(inv, x, y - 60);
		this.hudManager.addGroup(g);
		this.shownHUDGroups.put(id, g);
		
		Label label = this.shownLabels.get(id);
		if(label == null)
		{
			label = this.textHUD.drawText(name, FONT_ARIAL, Color.WHITE, 24, 200, 32, x, y);
			this.shownLabels.put(id, label);
		}
		else
		{
			label.setText(name);
		}
	}
	
	public void closeInventory(String id)
	{
		HUDGroup g = this.shownHUDGroups.get(id);
		if(g != null)
		{
			this.hudManager.removeGroup(g);
			this.hudManager.setElementOnMouse(null);
		}
		Label label = this.shownLabels.get(id);
		if(label != null)
		{
			label.setText("");
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
					g.addHUDElement(new InventoryItemHUD((Inventory<Item>)bagItems, j, nextItemX, y + 32 * (j % 2), 32, 32, true));
					nextItemX += 36 * (j % 2);
				}
				nextItemX += 36 * (bagItems.getCapacity() % 2);
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
			this.closeInventory(INV_NAME_CHEST);
		}
	}
	
	public float getCamaraPositionX() {
		return this.camera.position.x;
	}
}
