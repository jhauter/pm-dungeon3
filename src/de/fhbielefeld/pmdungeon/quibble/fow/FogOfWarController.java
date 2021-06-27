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
	
	public void loadTexture(String path, float fogTextureSize, int spriteSheetRows, int spriteSheetCols, float frameDuration)
	{
		this.fogAnim = AnimationFactory.createAnimation(path, spriteSheetRows, spriteSheetCols, frameDuration);
		this.fogTextureSize = fogTextureSize;
	}
	
	public void update()
	{
		this.resetFog();
		this.processLightSources();
	}
	
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
	
	public float getFogTileSize()
	{
		return this.fogTileSize;
	}
	
	public int getFogX(float dungeonX)
	{
		return (int)(dungeonX / this.fogTileSize);
	}
	
	public int getFogY(float dungeonY)
	{
		return (int)(dungeonY / this.fogTileSize);
	}
	
	public int getFogOfWarWidth()
	{
		return this.fogOfWarWidth;
	}
	
	public int getFogOfWarHeight()
	{
		return this.fogOfWarHeight;
	}
	
	public int getSectorX(int fogX)
	{
		return (int)(fogX / this.fogTilesPerSector);
	}
	
	public int getSectorY(int fogY)
	{
		return (int)(fogY / this.fogTilesPerSector);
	}
	
	public int getSectorsX()
	{
		return this.sectorsX;
	}
	
	public int getSectorsY()
	{
		return this.sectorsY;
	}
	
	public float getLightValueAt(int fogX, int fogY)
	{
		if(fogX < 0 || fogY < 0 || fogX >= this.fogOfWarWidth || fogY >= this.fogOfWarHeight)
		{
			return this.defaultLightValue;
		}
		return this.sectors[getSectorX(fogX)][getSectorY(fogY)].get(fogX, fogY);
	}
	
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
