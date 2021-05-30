package de.fhbielefeld.pmdungeon.quibble;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.SpatialHashGrid.Handle;
import de.fhbielefeld.pmdungeon.quibble.chest.GoldenChest;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Chort;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Demon;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Goblin;
import de.fhbielefeld.pmdungeon.quibble.entity.Lizard;
import de.fhbielefeld.pmdungeon.quibble.entity.Mage;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureExpEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureStatChangeEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerOpenChestEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerQuestsChangedEvent;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.hud.ExpBarHUD;
import de.fhbielefeld.pmdungeon.quibble.hud.HUDElement;
import de.fhbielefeld.pmdungeon.quibble.hud.HUDGroup;
import de.fhbielefeld.pmdungeon.quibble.hud.HUDManager;
import de.fhbielefeld.pmdungeon.quibble.hud.HealthDisplayHUD;
import de.fhbielefeld.pmdungeon.quibble.hud.InventoryHUDSwitchListener;
import de.fhbielefeld.pmdungeon.quibble.hud.InventoryItemHUD;
import de.fhbielefeld.pmdungeon.quibble.hud.QuestHUD;
import de.fhbielefeld.pmdungeon.quibble.input.DungeonInputHandler;
import de.fhbielefeld.pmdungeon.quibble.input.InputHandler;
import de.fhbielefeld.pmdungeon.quibble.input.strategy.InputStrategyCloseChest;
import de.fhbielefeld.pmdungeon.quibble.inventory.BagInventoryItem;
import de.fhbielefeld.pmdungeon.quibble.inventory.Inventory;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestDummy;
import de.fhbielefeld.pmdungeon.quibble.trap.TrapHealth;
import de.fhbielefeld.pmdungeon.quibble.trap.TrapTeleport;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class DungeonStart extends MainController implements EntityEventHandler
{
	public static void main(String[] args)
	{
		DesktopLauncher.run(new DungeonStart());
	}
	
	public static DungeonStart getDungeonMain()
	{
		return instance;
	}
	
	public static SpriteBatch getGameBatch()
	{
		return GameSetup.batch;
	}
	
	/****************************************
	 *                GAME                  *
	 ****************************************/
	
	private static DungeonStart instance;
	
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
	
	private int currentlyMarkedEquipSlot;
	
	private Map<String, HUDGroup> shownHUDGroups;
	private Map<String, Label> shownLabels;
	
	private ShapeRenderer debugRenderer;
	
	/**************DEBUG UTILS*************/
	
	private boolean drawBoundingBoxes = false;
	private boolean drawSHGCells = false;
	private boolean drawSHGCNearby = false;
	
	/**************************************/
	
	private Entity cameraTarget;
	
	//Prevents you from using entityController >:D -- use currentLevel instead!
	@SuppressWarnings("unused")
	private Object entityController;
	
	private DungeonStart() //Private constructor because singleton
	{
		if(instance != null)
		{
			throw new IllegalStateException("can only instantiate DungeonStart once");
		}
		instance = this;
		
		this.inputHandler = new DungeonInputHandler();
		this.shownHUDGroups = new HashMap<>();
		this.shownLabels = new HashMap<>();
		LoggingHandler.logger.setLevel(Level.CONFIG);
	}
	
	@Override
	protected void setup()
	{
		super.setup();
		//HudManager have to be made before the Player
		// cause InputStrategy would got a null HUDManager
		this.hudManager = new HUDManager();
		this.myHero = new Mage();
		this.myHero.getEquippedItems().addItem(Item.SWORD_BLUE);
		this.myHero.addEntityEventHandler(this);
		
		//Planning to remove these weird switches and replace them with a proper UI system
		this.invSwitchNormal = new InventoryHUDSwitchListener(this, INV_NAME_DEFAULT, this.myHero.getInventory(), "Inventory", 16, 176);
		this.invSwitchEquip = new InventoryHUDSwitchListener(this, INV_NAME_EQUIP, this.myHero.getEquippedItems(), "Equipment", 16, 76);
		
		this.myHero.getInventory().addInventoryListener(this.invSwitchNormal);
		this.myHero.getEquippedItems().addInventoryListener(this.invSwitchEquip);
		
		this.expBarHUD = new ExpBarHUD(this.myHero, 16, 432);
		this.healthHUD = new HealthDisplayHUD(this.myHero, 16, 368);
		
		this.inputHandler.addInputListener(myHero);
		
		this.getHudManager().addElement(this.expBarHUD);
		this.getHudManager().addElement(this.healthHUD);
		
		this.lastFrameTimeStamp = System.currentTimeMillis();
		Gdx.app.getGraphics().setResizable(false);
		
		this.expLabel = this.textHUD.drawText("Level: 1", FONT_ARIAL, Color.WHITE, 24, 200, 64, 16, 432);
		this.healthLabel = this.textHUD.drawText("Health", FONT_ARIAL, Color.WHITE, 24, 200, 64, 16, 384);
		
		this.questHUD = new QuestHUD(400, 400, this.textHUD);
		this.questHUD.setQuests(this.myHero.getQuestList());
		this.getHudManager().addElement(questHUD);
		
		this.debugRenderer = new ShapeRenderer();
		this.debugRenderer.setAutoShapeType(true);
		
		LoggingHandler.logger.log(Level.INFO, "Setup done.");
	}
	
	@Override
	public void onLevelLoad()
	{
		super.onLevelLoad();
		//Clear entities from previous level
		if(this.currentLevel != null) //For the first level its null
		{
			this.currentLevel.clearEntities();
		}
		//Set current level from the level controller and entity controller
		this.currentLevel = new DungeonLevel(this.levelController.getDungeon(), 15, 15, 30, 30);
		
		/**** Populate dungeon ****/
		
		for(int i = 0; i < 8; ++i)
		{
			final Point pos = this.currentLevel.getDungeon().getRandomPointInDungeon();
			final Creature toSpawn = switch(currentLevel.getRNG().nextInt(4))
			{
				case 0 -> new Demon();
				case 1 -> new Goblin();
				case 2 -> new Lizard();
				case 3 -> new Chort();
				default -> throw new IllegalArgumentException("Unexpected value [spawn entity]");
			};
			toSpawn.setPosition(pos.x, pos.y);
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
		 * Spawn Chests
		 */
		final int num = currentLevel.getRNG().nextInt(1) + 1;
		for(int i = 0; i < num; i++)
		{
			final Point pos2 = this.currentLevel.getDungeon().getRandomPointInDungeon();
			this.currentLevel.spawnEntity(new GoldenChest(pos2.x, pos2.y));
			LoggingHandler.logger.log(Level.INFO, "New Chest added.");
		}
		
		// Placing a new Trap
		
		final Point pos3 = this.currentLevel.getDungeon().getRandomPointInDungeon();
		this.currentLevel.spawnEntity(currentLevel.getRNG().nextInt(2) == 0 ? new TrapTeleport(pos3.x, pos3.y, true) : new TrapHealth(pos3.x, pos3.y, 2, true));
		
		final Point pos4 = this.currentLevel.getDungeon().getRandomPointInDungeon();
		int i = this.currentLevel.getRNG().nextInt(3);
		this.currentLevel.spawnEntity(new QuestDummy(i, pos4.x, pos4.y));
		
		//Set the camera to follow the hero
		this.cameraTarget = this.myHero;
		LoggingHandler.logger.log(Level.INFO, "New level loaded.");
		
	}
	
	@Override
	protected void beginFrame()
	{
		super.beginFrame();
		
		ResourceHandler.processQueue();
		
		this.inputHandler.updateHandler();
		
		this.currentLevel.update();
		
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
		
		if(this.cameraTarget != null)
		{
			this.camera.setFocusPoint(new Point(this.cameraTarget.getX(), this.cameraTarget.getY()));
		}
		
		getGameBatch().begin();
		
		Entity currentEntity;
		for(int i = 0; i < currentLevel.getNumEntities(); ++i)
		{
			currentEntity = currentLevel.getEntity(i);
			
			if(!currentEntity.isInvisible())
			{
				currentEntity.render();
				
				if(currentEntity instanceof Creature)
				{
					this.drawCreatureStatusEffects((Creature)currentEntity);
				}
			}
		}
		getGameBatch().end();
		
		this.currentLevel.getParticleSystem().draw(this.camera.position.x, this.camera.position.y);
		
		this.doDebugDrawing();
		
		this.getHudManager().update(); //Draw HUD last
	}
	
	private void doDebugDrawing()
	{
		this.debugRenderer.begin();
		
		if(this.drawSHGCells) //Draw spatial hash grid cells of the player
		{
			final Handle<Entity> h = this.myHero.getSpatialHashGridHandle();
			final SpatialHashGrid<Entity> shg = this.currentLevel.getSpatialHashGrid();
			float colsize = shg.getWidth() / shg.getColumns();
			float rowsize = shg.getHeight() / shg.getRows();
			for(Vector2 cell : h.cellsArray()) //All cells that the player touches
			{
				this.debugRenderer.setColor(Color.YELLOW);
				this.drawBoundingBox(new BoundingBox(cell.x * colsize, cell.y * rowsize, colsize, rowsize));
				
				if(this.drawSHGCNearby) //Draw entity BBs that touch the player's cells
				{
					this.debugRenderer.setColor(Color.ORANGE);
					final Set<Entity> nearby = shg.nearby(h);
					for(Entity e : nearby)
					{
						this.drawBoundingBox(e.getBoundingBox().offset(e.getX(), e.getY()));
					}
				}
			}
		}
		
		this.debugRenderer.setColor(Color.GREEN);
		Entity e;
		for(int i = 0; i < currentLevel.getNumEntities(); ++i)
		{
			e = currentLevel.getEntity(i);
			if(this.drawBoundingBoxes)
			{
				this.drawBoundingBox(e.getBoundingBox().offset(e.getX(), e.getY()));
			}
		}
		
		this.debugRenderer.end();
	}
	
	private void drawBoundingBox(BoundingBox bb)
	{
		this.debugRenderer.rect(
			DrawingUtil.dungeonToScreenXCam(bb.x, this.camera.position.x),
			DrawingUtil.dungeonToScreenYCam(bb.y, this.camera.position.y),
			DrawingUtil.dungeonToScreenX(bb.width),
			DrawingUtil.dungeonToScreenY(bb.height));
	}
	
	private void drawCreatureStatusEffects(Creature c)
	{
		StatusEffect e;
		for(int i = 0; i < c.getNumStatusEffects(); ++i)
		{
			e = c.getStatusEffect(i);
			e.renderStatusEffect();
		}
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
		
		new InputStrategyCloseChest(myHero);
		HUDGroup g = this.generateInventoryHUD(inv, x, y - 60);
		this.getHudManager().addGroup(g);
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
	
	@SuppressWarnings("unchecked")
	private HUDGroup generateInventoryHUD(Inventory<Item> inv, int x, int y)
	{
		HUDGroup g = new HUDGroup();
		this.getHudManager().setElementOnMouse(null);
		int nextItemX = x;
		InventoryItemHUD currentHUDElement;
		for(int i = 0; i < inv.getCapacity(); ++i)
		{
			if(inv.getItem(i) instanceof BagInventoryItem<?, ?>)
			{
				currentHUDElement = new InventoryItemHUD(inv, i, nextItemX, y, 64, 64);
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
				currentHUDElement = new InventoryItemHUD(inv, i, nextItemX, y, 64, 64);
				nextItemX += 72;
			}
			if(inv == this.myHero.getEquippedItems() && i == this.myHero.getSelectedEquipSlot())
			{
				currentHUDElement.setMarked(true);
			}
			g.addHUDElement(currentHUDElement);
		}
		return g;
	}
	
	/**
	 * Sets the slot that is marked in the hotbar.
	 * @param slot the slot index
	 */
	public void setMarkedEquipSlot(int slot)
	{
		HUDElement e;
		for(int i = 0; i < this.getHudManager().getNumElements(); ++i)
		{
			e = this.getHudManager().getElement(i);
			if(e instanceof InventoryItemHUD)
			{
				final InventoryItemHUD invSlot = (InventoryItemHUD)e;
				if(invSlot.getInventoryReference() == this.myHero.getEquippedItems())
				{
					if(this.currentlyMarkedEquipSlot == invSlot.getInventorySlot())
					{
						invSlot.setMarked(false);
					}
					if(slot == invSlot.getInventorySlot())
					{
						invSlot.setMarked(true);
					}
				}
			}
		}
		this.currentlyMarkedEquipSlot = slot;
	}
	
	/**
	 * @return the camera's x-position
	 */
	public float getCamPosX()
	{
		return this.camera.position.x;
	}
	
	/**
	 * @return the camera's y-position
	 */
	public float getCamPosY()
	{
		return this.camera.position.y;
	}
	
	public HUDManager getHudManager()
	{
		return hudManager;
	}
	
	public Label getChestLabel()
	{
		return this.shownLabels.get(INV_NAME_CHEST);
	}
	
	public HUDGroup getChestHud()
	{
		return this.shownHUDGroups.get(INV_NAME_CHEST);
	}
}
