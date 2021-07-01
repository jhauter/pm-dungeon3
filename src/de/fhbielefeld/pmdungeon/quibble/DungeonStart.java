package de.fhbielefeld.pmdungeon.quibble;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Level;

import javax.swing.UnsupportedLookAndFeelException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import de.fhbielefeld.pmdungeon.desktop.DesktopLauncher;
import de.fhbielefeld.pmdungeon.quibble.SpatialHashGrid.Handle;
import de.fhbielefeld.pmdungeon.quibble.chest.GoldenChest;
import de.fhbielefeld.pmdungeon.quibble.entity.BoundingBox;
import de.fhbielefeld.pmdungeon.quibble.entity.Chort;
import de.fhbielefeld.pmdungeon.quibble.entity.Creature;
import de.fhbielefeld.pmdungeon.quibble.entity.Demon;
import de.fhbielefeld.pmdungeon.quibble.entity.Entity;
import de.fhbielefeld.pmdungeon.quibble.entity.Goblin;
import de.fhbielefeld.pmdungeon.quibble.entity.Knight;
import de.fhbielefeld.pmdungeon.quibble.entity.Lizard;
import de.fhbielefeld.pmdungeon.quibble.entity.Mage;
import de.fhbielefeld.pmdungeon.quibble.entity.NPC;
import de.fhbielefeld.pmdungeon.quibble.entity.Player;
import de.fhbielefeld.pmdungeon.quibble.entity.battle.CreatureStatsAttribs;
import de.fhbielefeld.pmdungeon.quibble.entity.effect.StatusEffect;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureHitTargetPostEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.CreatureStatChangeEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.EntityEventHandler;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerOpenChestEvent;
import de.fhbielefeld.pmdungeon.quibble.entity.event.PlayerQuestsChangedEvent;
import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;
import de.fhbielefeld.pmdungeon.quibble.input.DungeonInputHandler;
import de.fhbielefeld.pmdungeon.quibble.item.RandomItemGenerator;
import de.fhbielefeld.pmdungeon.quibble.item.Item;
import de.fhbielefeld.pmdungeon.quibble.memory.MemoryDataHandler;
import de.fhbielefeld.pmdungeon.quibble.menu.Window;
import de.fhbielefeld.pmdungeon.quibble.menu.WindowForPlayername;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestDummy;
import de.fhbielefeld.pmdungeon.quibble.quest.QuestFactory;
import de.fhbielefeld.pmdungeon.quibble.trap.TrapDamage;
import de.fhbielefeld.pmdungeon.quibble.trap.TrapTeleport;
import de.fhbielefeld.pmdungeon.quibble.ui.UIFonts;
import de.fhbielefeld.pmdungeon.quibble.ui.UILayerCredits;
import de.fhbielefeld.pmdungeon.quibble.ui.UILayerGameOverScreen;
import de.fhbielefeld.pmdungeon.quibble.ui.UILayerInventoryView;
import de.fhbielefeld.pmdungeon.quibble.ui.UILayerPlayerHUD;
import de.fhbielefeld.pmdungeon.quibble.ui.UILayerQuestView;
import de.fhbielefeld.pmdungeon.quibble.ui.UIManager;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.DungeonConverter;
import de.fhbielefeld.pmdungeon.vorgaben.game.GameSetup;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.EntityController;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.LevelController;
import de.fhbielefeld.pmdungeon.vorgaben.game.Controller.MainController;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.HUD;
import de.fhbielefeld.pmdungeon.vorgaben.graphic.TextStage;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Constants;
import de.fhbielefeld.pmdungeon.vorgaben.tools.DungeonCamera;
import de.fhbielefeld.pmdungeon.vorgaben.tools.Point;

public class DungeonStart extends MainController implements EntityEventHandler
{
	public static void main(String[] args)
	{	
		try
		{
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		Window w = new Window();
		w.createWindow("Dungeon Quibble");
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
	
	public static Matrix4 orthoProjMatrix = new Matrix4();
	private static GlyphLayout glyphLayout = new GlyphLayout();
	
	/**
	 * Do not use the <code>this.entityController</code> as it will not work correctly with our system
	 * <code>Entity</code> does not implement
	 * <code>IEntity</code> anymore!!
	 */
	private DungeonLevel currentLevel;
	private int levelCount = 0;
	private Player myHero;
	private Entity cameraTarget;
	private boolean setupDone = false;

	private static boolean isSaveGame;
	
	private static int playerType;
	
	private long lastFrameTimeStamp;

	private DungeonInputHandler gameInputProcessor;
	private InputMultiplexer inputMultiplexer;

	private UIManager uiManager;

	private UILayerPlayerHUD uiLayerHUD;
	private UILayerInventoryView uiLayerPlayerEquipment;
	private UILayerInventoryView uiLayerPlayerInventory;
	private UILayerInventoryView uiLayerChestView;
	private UILayerQuestView uiLayerQuestView;
	private UILayerCredits uiLayerCredits;
	private UILayerGameOverScreen uiLayerGameOverScreen;

	/************** DEBUG UTILS *************/

	private ShapeRenderer debugRenderer;

	private boolean drawBoundingBoxes = false;
	// Draws spatial hash grid cells of the level
	private boolean drawSHGCells = false;
	private boolean drawSHGCNearby = false;

	// Draws the quad trees of the fog of war controller
	private boolean drawFoWQuadTrees = false;

	/**************************************/

	// Prevents you (almost) from using entityController >:D -- use currentLevel
	// instead!
	@SuppressWarnings("unused")
	private final Object entityController = null;

	private DungeonStart() // Private constructor because singleton
	{
		if (instance != null) {
			throw new IllegalStateException("can only instantiate DungeonStart once");
		}
		instance = this;

		LoggingHandler.logger.setLevel(Level.CONFIG);
	}

	private void setUpPlayer() {
		String logMsg = "The Game has started with a new ";
		switch (DungeonStart.playerType) {
		case 0: {
			this.myHero = new Knight();
			this.myHero.getEquippedItems().addItem(RandomItemGenerator.getInstance().generateMeleeWeapon(1));
			this.myHero.setName(WindowForPlayername.getPlayerName());
			LoggingHandler.logger.info(logMsg + "knight");
			break;
		}
		case 1 : {
			this.myHero = new Mage();
			this.myHero.getEquippedItems().addItem(Item.RED_MAGIC_STAFF);
			this.myHero.setName(WindowForPlayername.getPlayerName());
			LoggingHandler.logger.info(logMsg + "mage");
			break;
		}
		case 9 : {
			this.myHero = MemoryDataHandler.getInstance().loadPlayer();
			break;
		}
		}
		
		this.myHero.addEntityEventHandler(this);
		

	}

	@Override
	protected void setup() {
		super.setup();
		
		setUpPlayer();
		

		this.uiManager = new UIManager();

		this.uiLayerHUD = new UILayerPlayerHUD();
		this.uiLayerHUD.setZIndex(0);

		this.uiLayerPlayerEquipment = new UILayerInventoryView("Equipment", 352, 80, 40, 8);
		this.uiLayerPlayerEquipment.setZIndex(0);
		
		this.uiLayerPlayerInventory = new UILayerInventoryView("Inventory", 352, 16, 40, 8);
		this.uiLayerPlayerInventory.setZIndex(0);
		
		this.uiLayerChestView = new UILayerInventoryView("Chest", 352, 144, 40, 8);
		this.uiLayerChestView.setZIndex(0);
		
		this.uiLayerQuestView = new UILayerQuestView(16, 400);
		this.uiLayerQuestView.setZIndex(1);
		
		this.uiLayerCredits = new UILayerCredits();
		this.uiLayerCredits.setZIndex(100);
		
		this.uiLayerGameOverScreen = new UILayerGameOverScreen(Color.FIREBRICK);
		this.uiLayerGameOverScreen.setZIndex(99);
		this.uiLayerPlayerEquipment.setZIndex(1);

		this.uiLayerPlayerInventory = new UILayerInventoryView("Inventory", 352, 16, 40, 8);
		this.uiLayerPlayerInventory.setZIndex(1);

		this.uiLayerChestView = new UILayerInventoryView("Chest", 352, 144, 40, 8);
		this.uiLayerChestView.setZIndex(1);

		this.uiLayerQuestView = new UILayerQuestView(16, 400);
		this.uiLayerQuestView.setZIndex(1);
		this.uiManager.addUI(this.uiLayerHUD);
		this.uiManager.addUI(this.uiLayerPlayerEquipment);
		this.uiManager.addUI(this.uiLayerPlayerInventory);
		this.uiManager.addUI(this.uiLayerChestView);
		this.uiManager.addUI(this.uiLayerQuestView);
		this.uiManager.addUI(this.uiLayerCredits);
		this.uiManager.addUI(this.uiLayerGameOverScreen);
		
		this.gameInputProcessor = new DungeonInputHandler();
		this.inputMultiplexer = new InputMultiplexer(this.uiManager.getInputProcessors());
		this.inputMultiplexer.addProcessor(this.gameInputProcessor);

		Gdx.input.setInputProcessor(this.inputMultiplexer);
		
		this.myHero.setName(WindowForPlayername.getPlayerName());
		System.out.println("Your name is: " + this.myHero.getDisplayName());
		
		this.uiLayerHUD.setPlayer(myHero);
		this.uiLayerPlayerEquipment.setInventory(myHero.getEquippedItems());
		this.uiLayerPlayerInventory.setInventory(myHero.getInventory());
		this.uiLayerQuestView.setPlayer(myHero);

		this.lastFrameTimeStamp = System.currentTimeMillis();
		Gdx.app.getGraphics().setResizable(false);

		this.debugRenderer = new ShapeRenderer();
		this.debugRenderer.setAutoShapeType(true);
		
       
		if(isSaveGame)
		try {
			this.levelController.loadDungeon(new DungeonConverter().dungeonFromJson(MemoryDataHandler.getInstance().getSavedLevel()));
		} catch (InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
		else {
			try {
				this.levelController.loadDungeon(new DungeonConverter().dungeonFromJson(Constants.PATHTOLEVEL + "small_dungeon.json"));
			} catch (InvocationTargetException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		LoggingHandler.logger.log(Level.INFO, "Setup done.");
	}

	@Override
	public void onLevelLoad() {
		super.onLevelLoad();
		this.levelCount++;
		// Clear entities from previous level
		if (this.currentLevel != null) // For the first level its null
		{
			this.currentLevel.clearEntities();
		}
		// Set current level from the level controller and entity controller
		this.currentLevel = new DungeonLevel(this.levelController.getDungeon(), 50, 50, 150, 150);

		/**** Populate dungeon ****/

		for (int i = 0; i < 5; ++i) {
			final Point pos = this.currentLevel.getDungeon().getRandomPointInDungeon();
			final Creature toSpawn = switch (currentLevel.getRNG().nextInt(4)) {
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

		// Spawn the hero at the right spot
		Coordinate startingPoint = this.levelController.getDungeon().getStartingLocation();
		this.myHero.setPosition(startingPoint.getX(), startingPoint.getY());

		this.currentLevel.spawnEntity(this.myHero);

		/**
		 * Spawn Chests
		 */
		final int num = currentLevel.getRNG().nextInt(1) + 1;
		for (int i = 0; i < num; i++) {
			final Point pos2 = this.currentLevel.getDungeon().getRandomPointInDungeon();
			this.currentLevel.spawnEntity(new GoldenChest(pos2.x, pos2.y));
			LoggingHandler.logger.log(Level.INFO, "New Chest added.");
		}

		// Placing a new Trap

		final Point pos3 = this.currentLevel.getDungeon().getRandomPointInDungeon();
		this.currentLevel.spawnEntity(currentLevel.getRNG().nextInt(2) == 0 ? new TrapTeleport(pos3.x, pos3.y)
				: new TrapDamage(pos3.x, pos3.y, 2));

		final Point pos4 = this.currentLevel.getDungeon().getRandomPointInDungeon();
		this.currentLevel.spawnEntity(new QuestDummy(QuestFactory.getRandomQuest(), pos4.x, pos4.y));

		// Set the camera to follow the hero
		this.cameraTarget = this.myHero;
		LoggingHandler.logger.log(Level.INFO, "New level loaded.");

	}

	@Override
	protected void beginFrame() {
		super.beginFrame();
		if(!setupDone)
		{
			this.setupDone = true;
			this.firstFrameReplacement();
		}

		ResourceHandler.processQueue();

		this.gameInputProcessor.update();

		this.currentLevel.update();

		// Check the triggeredNextLevel flag of the player
		if (this.myHero.triggeredNextLevel()) {
			this.levelController.triggerNextStage();
			this.myHero.onNextLevelEntered();

			LoggingHandler.logger.log(Level.INFO, "Player entered new level.");
			MemoryDataHandler.getInstance().savePlayer();
		}
		this.currentLevel.getParticleSystem().update((System.currentTimeMillis() - this.lastFrameTimeStamp) / 1000.0F);
		this.lastFrameTimeStamp = System.currentTimeMillis();
	}

	@Override
	protected void endFrame() {
		super.endFrame();
		DungeonStart.orthoProjMatrix.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (this.cameraTarget != null) {
			this.camera.setFocusPoint(new Point(this.cameraTarget.getX(), this.cameraTarget.getY()));
		}

		this.renderEntities();

		this.currentLevel.getParticleSystem().draw(this.camera.position.x, this.camera.position.y);

		this.currentLevel.getFogOfWarController().render();

		this.doDebugDrawing();

		this.uiManager.draw(); // Draw UI last
	}

	private void renderEntities() {
		getGameBatch().begin();
		Entity currentEntity;
		for(int i = 0; i < currentLevel.getNumEntities(); ++i)
		{
			currentEntity = currentLevel.getEntity(i);
			
			if(currentEntity.isInvisible())
			{
				continue;
			}
			if(!currentEntity.isVisibleInFogOfWar(currentEntity.getLevel().getFogOfWarController().getLightValueAt(currentEntity.getX(), currentEntity.getY())))
			{
				continue;
			}
			
			if(currentEntity.isDisplayNameVisible())
			{
				this.renderEntityName(currentEntity);
			}
			
			currentEntity.render();
			
			if(currentEntity instanceof Creature)
			{
				this.drawCreatureStatusEffects((Creature)currentEntity);
			}
		}
		getGameBatch().end();

	}

	private void renderEntityName(Entity entity) {
		final String text = entity.getDisplayNamePrefix() + " " + entity.getDisplayName();

		final BitmapFont font = UIFonts.DEFAULT.getFont();
		glyphLayout.setText(font, text);
		DungeonStart.getGameBatch().setProjectionMatrix(orthoProjMatrix);

		// Note: entity.renderOffsetX,Y of 0.0F means that the entity texture is
		// rendered centered on the entity
		// position. For creatures this value is often height/2 so that the feet are at
		// the actual position.
		// => Y = entity.getY() + entity.getRenderOffsetY() + entity.getRenderHeight() /
		// 2
		float barX = DrawingUtil.dungeonToScreenXCam(entity.getX() + entity.getRenderOffsetX(), this.getCamPosX());
		float barY = DrawingUtil.dungeonToScreenYCam(
				entity.getY() + entity.getRenderOffsetY() + entity.getRenderHeight() / 2, this.getCamPosY());

		if (entity instanceof NPC) {
			this.renderHealthBar((Creature) entity, barX, barY);
		}

		font.draw(DungeonStart.getGameBatch(), text, barX - glyphLayout.width / 2, barY + glyphLayout.height + 24);

		DungeonStart.getGameBatch().setProjectionMatrix(DungeonStart.getDungeonMain().getCamera().combined);
	}

	private void renderHealthBar(Creature entity, float barX, float barY) {
		DungeonResource<Texture> barTex = ResourceHandler
				.requestResourceInstantly("assets/textures/entity/healthbar.png", ResourceType.TEXTURE);
		if (!barTex.isLoaded()) {
			return;
		}

		float entityHealthP = (float) Math.max(entity.getCurrentHealth() / entity.getMaxHealth(), 0F);

		getGameBatch().setColor(1.0F, 0.65F - 0.65F * entityHealthP, 0.15F, 0.7F);

		// draw(Texture texture, float x, float y, float width, float height, int srcX,
		// int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
		getGameBatch().draw(barTex.getResource(), barX - 32, barY, 64, 16, 0, 0, 64, 16, false, false);
		getGameBatch().draw(barTex.getResource(), barX - 30, barY + 2, (int) (64 * entityHealthP), 12, 0, 16, 10, 12,
				false, false);

		getGameBatch().setColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	private void doDebugDrawing() {
		this.debugRenderer.begin();
		if (this.drawSHGCells) // Draw spatial hash grid cells of the player
		{
			final Handle<Entity> h = this.myHero.getSpatialHashGridHandle();
			final SpatialHashGrid<Entity> shg = this.currentLevel.getSpatialHashGrid();
			float colsize = shg.getWidth() / shg.getColumns();
			float rowsize = shg.getHeight() / shg.getRows();
			for (Vector2 cell : h.cellsArray()) // All cells that the player touches
			{
				this.debugRenderer.setColor(Color.YELLOW);
				this.drawBoundingBox(new BoundingBox(cell.x * colsize, cell.y * rowsize, colsize, rowsize));

				if (this.drawSHGCNearby) // Draw entity BBs that touch the player's cells
				{
					this.debugRenderer.setColor(Color.ORANGE);
					final Set<Entity> nearby = shg.nearby(h);
					for (Entity e : nearby) {
						this.drawBoundingBox(e.getBoundingBox().offset(e.getX(), e.getY()));
					}
				}
			}
		}

		this.debugRenderer.setColor(Color.GREEN);
		Entity e;
		for (int i = 0; i < currentLevel.getNumEntities(); ++i) {
			e = currentLevel.getEntity(i);
			if (this.drawBoundingBoxes) {
				this.drawBoundingBox(e.getBoundingBox().offset(e.getX(), e.getY()));
			}
		}

		this.debugRenderer.end();

		this.currentLevel.getFogOfWarController().renderDebug(debugRenderer);
	}

	private void drawBoundingBox(BoundingBox bb) {
		this.debugRenderer.rect(DrawingUtil.dungeonToScreenXCam(bb.x, this.camera.position.x),
				DrawingUtil.dungeonToScreenYCam(bb.y, this.camera.position.y), DrawingUtil.dungeonToScreenX(bb.width),
				DrawingUtil.dungeonToScreenY(bb.height));
	}

	private void drawCreatureStatusEffects(Creature c) {
		StatusEffect e;
		for (int i = 0; i < c.getNumStatusEffects(); ++i) {
			e = c.getStatusEffect(i);
			e.renderStatusEffect();
		}
	}

	@Override
	public void handleEvent(EntityEvent event) // There are events for myHero
	{
		if (event.getEventID() == Creature.EVENT_ID_STAT_CHANGE) {
			final CreatureStatChangeEvent statEvent = (CreatureStatChangeEvent) event;
			final Creature hero = statEvent.getEntity(); // In this case its hero
			if (statEvent.getStat() == CreatureStatsAttribs.HEALTH) {
				LoggingHandler.logger.log(Level.INFO,
						"Health [" + statEvent.getNewValue() + "/" + hero.getMaxHealth() + "]");
				if (statEvent.getNewValue() <= 0.0D) {
					LoggingHandler.logger.log(Level.INFO, "Game over!");
					this.getUIGameOverScreen().play();
				}
			}
		} else if (event.getEventID() == Creature.EVENT_ID_HIT_TARGET_POST) {
			final CreatureHitTargetPostEvent hitEvent = (CreatureHitTargetPostEvent) event;
			if (hitEvent.getTarget().getCurrentHealth() <= 0.0D && hitEvent.getTarget().getDeadTicks() == 0) {
				// Dead ticks must be 0 so that this doesn't get triggered
				// when the enemy is hit when it's already dead
				LoggingHandler.logger.log(Level.INFO, "Killed " + hitEvent.getTarget().getClass().getSimpleName());

				hitEvent.getEntity().heal(1.0D);
				LoggingHandler.logger.log(Level.INFO, "Healed by 1.0");
			}

		} else if (event.getEventID() == PlayerOpenChestEvent.EVENT_ID) {
			final PlayerOpenChestEvent chestEvent = (PlayerOpenChestEvent) event;
			this.uiLayerChestView.setInventory(chestEvent.getChest().getInv());
		} else if (event.getEventID() == PlayerQuestsChangedEvent.EVENT_ID) {
			this.uiLayerQuestView.refreshQuests();
		}
	}
	
    private void firstFrameReplacement()
    {
        super.entityController = new EntityController();
                this.hud = new HUD();
                this.textHUD = new TextStage(hud.getHudBatch());
                setupCamera();
                setupWorldController();
                this.setup();
    }
    
    private void setupCamera()
    {
        camera = new DungeonCamera(null, Constants.VIRTUALHEIGHT * Constants.WIDTH / (float) Constants.HEIGHT, Constants.VIRTUALHEIGHT);
                camera.position.set(0, 0, 0);
                camera.zoom += 1;
                camera.update();
    }
    
    private void setupWorldController()
    {
    try {
            //this method will be called every time a new level gets load
            Method functionToPass = this.getClass().getMethod("onLevelLoad");
            System.out.println("DEBUG: " + functionToPass);
            //if you need parameter four your method, add them here
            Object[] arguments = new Object[0];
            this.levelController = new LevelController(functionToPass, this, arguments);
            } catch (NoSuchMethodException e) {
            e.printStackTrace();
            }
    }

	/**
	 * @return the camera's x-position
	 */
	public float getCamPosX() {
		return this.camera.position.x;
	}

	/**
	 * @return the camera's y-position
	 */
	public float getCamPosY() {
		return this.camera.position.y;
	}

	public DungeonCamera getCamera() {
		return this.camera;
	}

	public UIManager getUIManager() {
		return this.uiManager;
	}

	public UILayerInventoryView getUIChestView() {
		return this.uiLayerChestView;
	}

	public UILayerInventoryView getUIInventoryView() {
		return this.uiLayerPlayerInventory;
	}

	public UILayerInventoryView getUIEquipmentView() {
		return this.uiLayerPlayerEquipment;
	}
	
	public UILayerCredits getUICredits()
	{
		return this.uiLayerCredits;
	}
	
	public UILayerGameOverScreen getUIGameOverScreen()
	{
		return this.uiLayerGameOverScreen;
	}
	
	public Player getPlayer()
	{
		return this.myHero;
	}

	public boolean getDrawBoundingBoxes() {
		return drawBoundingBoxes;
	}

	public boolean getDrawSHGCells() {
		return drawSHGCells;
	}

	public boolean getDrawSHGCNearby() {
		return drawSHGCNearby;
	}

	public boolean getDrawFoWQuadTrees() {
		return drawFoWQuadTrees;
	}

	public int getLevelCount() {
		return levelCount;
	}

	/**
	 * Starts the gameloop
	 */
	public static void startGame(boolean isSaveGame, int playerType) {
		DungeonStart.isSaveGame = isSaveGame;
		DungeonStart.playerType = playerType;
		DesktopLauncher.run(new DungeonStart());
	}
}
