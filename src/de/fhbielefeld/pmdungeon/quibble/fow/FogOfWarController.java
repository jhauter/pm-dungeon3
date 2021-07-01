package de.fhbielefeld.pmdungeon.quibble.fow;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.fhbielefeld.pmdungeon.quibble.DungeonLevel;
import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.animation.AnimationFactory;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;

public class FogOfWarController
{
	private Animation<TextureRegion> fogAnim;
	private float fogTextureSize;
	private float fogAnimStateTime;
	
	private final float fogTileSize;
	private float defaultLightValue;
	private final int fogOfWarWidth;
	private final int fogOfWarHeight;
	private final float fogTilesPerSector;
	
	private FogOfWarQuadTree[][] sectors;
	private final int sectorsX;
	private final int sectorsY;
	
	private final float fullFogAlphaValue = 0.9F;
	
	private List<FogOfWarLightSource> lightSources;
	
	private DungeonLevel level;
	
	private Pixmap lightMap;
	
	private Texture lightMapTexture;
	
	private FrameBuffer frameBuffer;
	
	/**
	 * Creates a fog of war controller than calculates the fog of war and is able to render the fog of war.
	 * The level is sliced in sectors which are sliced again to contain "fog tiles".
	 * A fog tile is a tile that has a value of fog density. Fog tiles can be smaller than normal dungeon tiles.
	 * @param level the dungeon that is used to detect tiles
	 * @param sectorSize width of a sector in dungeon tiles
	 * @param fogTilesPerSector number of fog tiles that make up the width of a sector
	 * @param levelWidth width of the level in dungeon tiles
	 * @param levelHeight height of the level in dungeon tiles
	 * @param defaultLightValue default light value that fills the level. (<code>0</code> means most fog density;
	 * <code>&gt;= 1</code> means no fog)
	 * @throws IllegalArgumentException if fogTilesPerSector is <= 0
	 * @throws IllegalArgumentException if fogTilesPerSector is not a power of 2
	 * @throws IllegalArgumentException if sectorSize is <= 2
	 * @throws IllegalArgumentException if levelWidth or levelHeight is <= 0
	 * @throws IllegalArgumentException if defauleLightValue is < 0
	 */
	public FogOfWarController(DungeonLevel level, int sectorSize, int fogTilesPerSector, int levelWidth, int levelHeight, float defaultLightValue)
	{
		if(fogTilesPerSector <= 0)
		{
			throw new IllegalArgumentException("fogTilesPerSector cannot be <= 0");
		}
		if((fogTilesPerSector & (fogTilesPerSector - 1)) != 0)
		{
			throw new IllegalArgumentException("fogTilesPerSector must be power of two");
		}
		if(sectorSize <= 0)
		{
			throw new IllegalArgumentException("sectorSize cannot be <= 0");
		}
		if(levelWidth <= 0 || levelHeight <= 0)
		{
			throw new IllegalArgumentException("level dimensions cannot be <= 0");
		}
		if(defaultLightValue < 0.0F)
		{
			throw new IllegalArgumentException("defaultLightValue must be >= 0");
		}
		
		this.level = level;
		this.sectorsX = (int)Math.ceil(levelWidth / (double)sectorSize);
		this.sectorsY = (int)Math.ceil(levelHeight / (double)sectorSize);
		this.fogOfWarWidth = this.sectorsX * fogTilesPerSector;
		this.fogOfWarHeight = this.sectorsY * fogTilesPerSector;
		this.defaultLightValue = defaultLightValue;
		this.fogTileSize = sectorSize / (float)fogTilesPerSector;
		this.fogTilesPerSector = fogTilesPerSector;
		this.sectors = new FogOfWarQuadTree[this.sectorsX][this.sectorsY];
		int x, y;
		for(x = 0; x < this.sectorsX; ++x)
		{
			for(y = 0; y < this.sectorsY; ++y)
			{
				this.sectors[x][y] = new FogOfWarQuadTree(
					x * fogTilesPerSector,
					y * fogTilesPerSector,
					x * fogTilesPerSector + fogTilesPerSector,
					y * fogTilesPerSector + fogTilesPerSector,
					defaultLightValue);
			}
		}
		
		this.lightSources = new ArrayList<>();
		
		this.frameBuffer = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
	}
	
	/**
	 * Sets the size of the texture that is used as light map.
	 * <code>width</code> and <code>height</code> must be in dungeon units
	 * and should be the dimensions of the visible part of the dungeon on the screen.
	 * @param width width in dungeon units used to calculate the size of the light map
	 * @param height height in dungeon units used to calculate the size of the light map
	 */
	public void setLightmapSize(int width, int height)
	{
		if(this.lightMap != null)
		{
			this.lightMap.dispose();
			this.lightMapTexture.dispose();
		}
		this.lightMap = new Pixmap((int)Math.ceil((width + 2) / this.fogTileSize), (int)Math.ceil((height + 2) / this.fogTileSize), Format.RGBA8888);
		this.lightMapTexture = new Texture(this.lightMap);
		this.lightMapTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	
	/**
	 * Loads a sprite sheet that is used as the animated fog texture
	 * @param path texture file
	 * @param fogTextureSize size that the texture should be displayed in dungeon units
	 * @param spriteSheetRows rows of the spritesheet
	 * @param spriteSheetCols columns of the spritesheet
	 * @param frameDuration how long in seconds a frame of the animation takes
	 */
	public void loadTexture(String path, float fogTextureSize, int spriteSheetRows, int spriteSheetCols, float frameDuration)
	{
		this.fogAnim = AnimationFactory.createAnimation(path, spriteSheetRows, spriteSheetCols, frameDuration);
		this.fogTextureSize = fogTextureSize;
	}
	
	/**
	 * Must be called on every frame in order to do the logic and calculations.
	 */
	public void update()
	{
		this.resetFog();
		this.processLightSources();
	}
	
	/**
	 * Renders the fog.
	 */
	public void render()
	{
		this.lightMap.setColor(0.0F, 0.0F, 0.0F, 0.0F);
		this.lightMap.fill();
		this.lightMap.setColor(Color.BLACK);
		
		this.fogAnimStateTime += Gdx.graphics.getDeltaTime();
		float camXDungeon = DungeonStart.getDungeonMain().getCamPosX();
		float camYDungeon = DungeonStart.getDungeonMain().getCamPosY();
		
		//==== Create the low res light map ====
		
		//in fog coordinates:
		int screenWidth = (int)Math.ceil(this.lightMap.getWidth());
		int screenHeight = (int)Math.ceil(this.lightMap.getHeight());
		float camX = camXDungeon / fogTileSize;
		float camY = camYDungeon / fogTileSize;
		
		int minX = (int)(camX - screenWidth / 2);
		int minY = (int)(camY - screenHeight / 2);
		float fogIntensity;
		int x, y;
		
		for(x = 0; x < screenWidth; ++x)
		{
			for(y = 0; y < screenHeight; ++y)
			{
				fogIntensity = Math.max((1.0F - this.getLightValueAt(minX + x, minY + y)) * this.fullFogAlphaValue, 0);
				
				this.lightMap.setColor(1.0F, 1.0F, 1.0F, fogIntensity);
				this.lightMap.drawPixel(x, screenHeight - y - 1);
			}
		}
		this.lightMapTexture.draw(lightMap, 0, 0);
		
		//==== Draw the light map on the frame buffer and mix it with the fog texture: ====
		this.frameBuffer.begin();
		Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		DungeonStart.getGameBatch().begin();
		
		DungeonStart.getGameBatch().draw(lightMapTexture, minX * this.fogTileSize, minY * this.fogTileSize, this.lightMap.getWidth() * this.fogTileSize,
			this.lightMap.getHeight() * this.fogTileSize);
		
		//==== Draw the fog texture, masking with the light map
		
		int srcFunc = DungeonStart.getGameBatch().getBlendSrcFunc();
		int dstFunc = DungeonStart.getGameBatch().getBlendDstFunc();
		DungeonStart.getGameBatch().setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);
		
		int fogTextureScreenFitX = (int)Math.ceil(screenWidth * this.fogTileSize / this.fogTextureSize);
		int fogTextureScreenFitY = (int)Math.ceil(screenHeight * this.fogTileSize / this.fogTextureSize);
		int minXDungeon = (int)(minX * this.fogTileSize);
		int minYDungeon = (int)(minY * this.fogTileSize);
		for(x = -1; x < fogTextureScreenFitX + 2; ++x)
		{
			for(y = -1; y < fogTextureScreenFitY + 2; ++y)
			{
				DungeonStart.getGameBatch().draw(this.fogAnim.getKeyFrame(this.fogAnimStateTime, true),
					(((int)(minXDungeon / this.fogTextureSize)) * this.fogTextureSize) + x * this.fogTextureSize,
					(((int)(minYDungeon / this.fogTextureSize)) * this.fogTextureSize) + y * this.fogTextureSize,
					this.fogTextureSize,
					this.fogTextureSize);
			}
		}
		
		DungeonStart.getGameBatch().setBlendFunction(srcFunc, dstFunc);
		
		DungeonStart.getGameBatch().end();
		this.frameBuffer.end();
		
		//==== Draw the frame buffer containing the final fog texture on the screen: ====
		DungeonStart.getGameBatch().begin();
		DungeonStart.getGameBatch().setProjectionMatrix(DungeonStart.getGameBatch().getProjectionMatrix().idt());
		DungeonStart.getGameBatch().draw(this.frameBuffer.getColorBufferTexture(), -1, 1, 2, -2);
		DungeonStart.getGameBatch().setProjectionMatrix(DungeonStart.getDungeonMain().getCamera().combined);
		DungeonStart.getGameBatch().end();
		DungeonStart.getGameBatch().setColor(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	/**
	 * Renders the quad trees used to calculate the fog.
	 * @param renderer <code>ShapeRenderer</code> used for drawing
	 */
	public void renderDebug(ShapeRenderer renderer)
	{
		if(!DungeonStart.getDungeonMain().getDrawFoWQuadTrees())
		{
			return;
		}
		
		renderer.begin();
		
		final float camX = DungeonStart.getDungeonMain().getCamPosX();
		final float camY = DungeonStart.getDungeonMain().getCamPosY();
		
		int x, y;
		for(x = 0; x < this.sectorsX; ++x)
		{
			for(y = 0; y < this.sectorsY; ++y)
			{
				this.sectors[x][y].traversePreorder(n ->
				{
					float rx = DrawingUtil.dungeonToScreenXCam(n.getMinX() * this.fogTileSize, camX);
					float ry = DrawingUtil.dungeonToScreenYCam(n.getMinY() * this.fogTileSize, camY);
					float rwidth = DrawingUtil.dungeonToScreenX(n.getMaxX() - n.getMinX()) * this.fogTileSize;
					float rheight = DrawingUtil.dungeonToScreenY(n.getMaxY() - n.getMinY()) * this.fogTileSize;
					renderer.rect(rx, ry, rwidth, rheight);
				});
			}
		}
		renderer.end();
	}
	
	/**
	 * @return size of a fog tile in dungeon units
	 */
	public float getFogTileSize()
	{
		return this.fogTileSize;
	}
	
	/**
	 * Converts dungeon units in fog units.
	 * @param dungeonX the x-position in dungeon units
	 * @return the x-position in fog units
	 */
	public int getFogX(float dungeonX)
	{
		return (int)(dungeonX / this.fogTileSize);
	}
	
	/**
	 * Converts dungeon units in fog units.
	 * @param dungeonY the y-position in dungeon units
	 * @return the y-position in fog units
	 */
	public int getFogY(float dungeonY)
	{
		return (int)(dungeonY / this.fogTileSize);
	}
	
	/**
	 * @return the width of the level in fog units
	 */
	public int getFogOfWarWidth()
	{
		return this.fogOfWarWidth;
	}
	
	/**
	 * @return the height of the level in fog units
	 */
	public int getFogOfWarHeight()
	{
		return this.fogOfWarHeight;
	}
	
	/**
	 * Converts fog units in sector units.
	 * @param fogX the x-position in dungeon units
	 * @return the x-position of the sectors that contain the specified <code>fogX</code>
	 */
	public int getSectorX(int fogX)
	{
		return (int)(fogX / this.fogTilesPerSector);
	}
	
	/**
	 * Converts fog units in sector units.
	 * @param fogY the y-position in dungeon units
	 * @return the y-position of the sectors that contain the specified <code>fogY</code>
	 */
	public int getSectorY(int fogY)
	{
		return (int)(fogY / this.fogTilesPerSector);
	}
	
	/**
	 * @return the number of sectors along the x-axis
	 */
	public int getSectorsX()
	{
		return this.sectorsX;
	}
	
	/**
	 * @return the number of sectors along the y-axis
	 */
	public int getSectorsY()
	{
		return this.sectorsY;
	}
	
	/**
	 * Returns the light value at the specified position in fog units
	 * @param fogX x-position in fog units
	 * @param fogY y-position in fog units
	 * @return the light value at the specified position. (<code>0</code> means no light;
	 * <code>&gt;= 1</code> means completely lit)
	 */
	public float getLightValueAt(int fogX, int fogY)
	{
		if(fogX < 0 || fogY < 0 || fogX >= this.fogOfWarWidth || fogY >= this.fogOfWarHeight)
		{
			return this.defaultLightValue;
		}
		return this.sectors[getSectorX(fogX)][getSectorY(fogY)].get(fogX, fogY);
	}
	
	/**
	 * Returns the light value at the specified position in dungeon units
	 * @param dungeonX x-position in dungeon units
	 * @param dungeonY y-position in dungeon units
	 * @return the light value at the specified position. (<code>0</code> means no light;
	 * <code>&gt;= 1</code> means completely lit)
	 */
	public float getLightValueAt(float dungeonX, float dungeonY)
	{
		return this.getLightValueAt(this.getFogX(dungeonX), this.getFogY(dungeonY));
	}
	
	private void setLightValueAt(int fogX, int fogY, float value)
	{
		if(fogX < 0 || fogY < 0 || fogX >= this.fogOfWarWidth || fogY >= this.fogOfWarHeight)
		{
			return;
		}
		this.sectors[getSectorX(fogX)][getSectorY(fogY)].set(fogX, fogY, value);
	}
	
	/**
	 * Resets all light values to the default value specified in the <code>FogOfWarController</code> constructor.
	 */
	public void resetFog()
	{
		int x, y;
		for(x = 0; x < this.sectorsX; ++x)
		{
			for(y = 0; y < this.sectorsY; ++y)
			{
				this.sectors[x][y].clear(this.defaultLightValue);
			}
		}
	}
	
	/**
	 * Shines a light at the specified position in the dungeon. The light will last one frame and will then be removed.
	 * Removed means that the light will start to fade out quickly.
	 * To create constant light, this method must be called every frame with the same arguments.
	 * @param dungeonX the x-position of the light in dungeon units
	 * @param dungeonY the y-position of the light in dungeon units
	 * @param intensity the intensity of the light. (<code>0</code> means no light; <code>1</code> means completely lit;
	 * <code>&gt;1</code> is not brighter but makes the light go farther)
	 */
	public void light(float dungeonX, float dungeonY, float intensity)
	{
		this.lightSources.add(new FogOfWarLightSource(this.getFogX(dungeonX), this.getFogY(dungeonY), intensity));
	}
	
	private void processLightSources()
	{
		//		long start = System.currentTimeMillis();
		FogOfWarLightSource source;
		for(int i = 0; i < this.lightSources.size(); ++i)
		{
			source = this.lightSources.get(i);
			this.processLightSource(source);
			if(!this.processLightSource(source))
			{
				this.lightSources.set(i, this.lightSources.get(this.lightSources.size() - 1));
				this.lightSources.remove(this.lightSources.size() - 1);
			}
			source.addDeltaTime(Gdx.graphics.getDeltaTime());
			
		}
		//		System.out.println(System.currentTimeMillis() - start);
	}
	
	private boolean processLightSource(FogOfWarLightSource src)
	{
		float intensity = src.getLightSpreadFunction().getIntensityOverTime(src.getIntensity(), src.getDeltaTime());
		if(intensity <= 0.0F)
		{
			return false;
		}
		//long start = System.currentTimeMillis();
		this.spreadLight(src.getX(), src.getY(), 0, intensity, src.getLightSpreadFunction());
		//System.out.println(System.currentTimeMillis() - start);
		return true;
	}
	
	private void spreadLight(int fowX, int fowY, int dist, float intensity, LightSpreadFunction function)
	{
		float newLightVal = Math.max(intensity, 0);
		float currentLightValue = this.getLightValueAt(fowX, fowY);
		if(newLightVal <= currentLightValue || fowX < 0 || fowY < 0 || dist > function.getMaxDist(this.fogTileSize))
		{
			return;
		}
		this.setLightValueAt(fowX, fowY, newLightVal);
		if(!this.level.getDungeon().isTileAccessible((int)(fowX * this.fogTileSize), (int)(fowY * this.fogTileSize)))
		{
			intensity -= function.getLightIntensityFallOffBlocked(dist + 1, this.fogTileSize);
		}
		else
		{
			intensity -= function.getLightIntensityFallOff(dist + 1, this.fogTileSize);
		}
		if(intensity <= 0.0F)
		{
			return;
		}
		this.spreadLight(fowX + 1, fowY, dist + 1, intensity, function);
		this.spreadLight(fowX - 1, fowY, dist + 1, intensity, function);
		this.spreadLight(fowX, fowY + 1, dist + 1, intensity, function);
		this.spreadLight(fowX, fowY - 1, dist + 1, intensity, function);
		this.spreadLight(fowX + 1, fowY + 1, dist + 1, intensity * 0.9F, function);
		this.spreadLight(fowX + 1, fowY - 1, dist + 1, intensity * 0.9F, function);
		this.spreadLight(fowX - 1, fowY + 1, dist + 1, intensity * 0.9F, function);
		this.spreadLight(fowX - 1, fowY - 1, dist + 1, intensity * 0.9F, function);
	}
}
