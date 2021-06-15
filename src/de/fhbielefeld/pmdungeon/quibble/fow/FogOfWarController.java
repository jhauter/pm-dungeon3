package de.fhbielefeld.pmdungeon.quibble.fow;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import de.fhbielefeld.pmdungeon.quibble.DungeonStart;
import de.fhbielefeld.pmdungeon.quibble.LoggingHandler;
import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;

public class FogOfWarController
{
	private ArrayList<Animation<TextureRegion>> fogAnimTiles;
	private int fogTextureTileFit;
	private float fogAnimStateTime;
	
	private final float fogTileSize;
	private float defaultFogValue;
	private final int fogOfWarWidth;
	private final int fogOfWarHeight;
	private final float fogTilesPerSector;
	
	private FogOfWarQuadTree[][] sectors;
	private final int sectorsX;
	private final int sectorsY;
	
	private List<FogOfWarLightSource> lightSources;
	
	public FogOfWarController(int sectorSize, int fogTilesPerSector, int levelWidth, int levelHeight, float defaultFogValue)
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
		if(defaultFogValue < 0.0F || defaultFogValue > 1.0F)
		{
			throw new IllegalArgumentException("defaultFogValue must be between 0 and 1");
		}
		
		this.sectorsX = (int)Math.ceil(levelWidth / (double)sectorSize);
		this.sectorsY = (int)Math.ceil(levelHeight / (double)sectorSize);
		this.fogOfWarWidth = this.sectorsX * fogTilesPerSector;
		this.fogOfWarHeight= this.sectorsY * fogTilesPerSector;
		this.defaultFogValue = defaultFogValue;
		this.fogTileSize = sectorSize / (float)fogTilesPerSector;
		this.fogTilesPerSector = fogTilesPerSector;
		this.fogAnimTiles = new ArrayList<>();
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
					defaultFogValue);
			}
		}
		
		this.lightSources = new ArrayList<>();
	}
	
	public void loadTexture(String path, float fogTextureSize, int spriteSheetRows, int spriteSheetCols, float frameDuration)
	{
		long timeStamp = System.currentTimeMillis();
		DungeonResource<Texture> res = ResourceHandler.requestResourceInstantly(path, ResourceType.TEXTURE);
		if(!res.isLoaded())
		{
			return;
		}
		this.fogAnimTiles.clear();
		
		Texture texture = res.getResource();
		
		this.fogTextureTileFit = (int)(fogTextureSize / this.fogTileSize);
		int textureFracWidth = texture.getWidth() / spriteSheetCols / this.fogTextureTileFit;
		int textureFracHeight = texture.getHeight() / spriteSheetRows / this.fogTextureTileFit;
		TextureRegion[][] split = TextureRegion.split(texture, textureFracWidth, textureFracHeight);
		int innerSplitCount = (int)Math.pow(this.fogTextureTileFit, 2);
		
		Animation<TextureRegion> anim;
		List<TextureRegion> animFrames = new ArrayList<>();
		TextureRegion t;
		int row, col, i;
		for(i = 0; i < innerSplitCount; ++i)
		{
			animFrames.clear();
			for(row = 0; row < spriteSheetRows; ++row)
			{
				for(col = 0; col < spriteSheetCols; ++col)
				{
					t = split[row * fogTextureTileFit + i / fogTextureTileFit][(col * fogTextureTileFit) + (i % fogTextureTileFit)];
					animFrames.add(t);
				}
			}
			anim = new Animation<>(frameDuration, animFrames.toArray(new TextureRegion[0]));
			this.fogAnimTiles.add(anim);
		}
		LoggingHandler.logger.log(Level.FINE, "Loaded fog texture in " + (System.currentTimeMillis() - timeStamp) + " ms.");
	}
	
	public void update()
	{
		this.clearFog();
		this.processLightSources();
	}
	
	public void render()
	{
		this.fogAnimStateTime += Gdx.graphics.getDeltaTime();
		DungeonStart.getGameBatch().begin();
		int screenWidth = (int)Math.ceil(DrawingUtil.screenToDungeonX(DrawingUtil.CURRENT_SCREEN_WIDTH.get()) / fogTileSize);
		int screenHeight = (int)Math.ceil(DrawingUtil.screenToDungeonY(DrawingUtil.CURRENT_SCREEN_HEIGHT.get()) / fogTileSize);
		float camX = DungeonStart.getDungeonMain().getCamPosX() / fogTileSize;
		float camY = DungeonStart.getDungeonMain().getCamPosY() / fogTileSize;
		float oneTile = 1 / fogTileSize;
		int minX = (int)((camX - screenWidth / 2) - oneTile);
		int minY = (int)((camY - screenHeight / 2) - oneTile);
		int maxX = (int)Math.ceil((minX + screenWidth + 2 * oneTile));
		int maxY = (int)Math.ceil((minY + screenHeight + 2 * oneTile));
		float fogIntensity;
		int x, y;
		for(x = minX; x <= maxX; ++x)
		{
			for(y = minY; y <= maxY; ++y)
			{
				fogIntensity = this.getFogValueAt(x, y);
				DungeonStart.getGameBatch().setColor(1.0F, 1.0F, 1.0F, fogIntensity);
				DungeonStart.getGameBatch().draw(this.fogAnimTiles.get(getFogTileIndex(x, y, fogTextureTileFit)).getKeyFrame(fogAnimStateTime, true),
					x * fogTileSize, y * fogTileSize, fogTileSize, fogTileSize);
			}
		}
		
		DungeonStart.getGameBatch().setColor(1.0F, 1.0F, 1.0F, 1.0F);
		DungeonStart.getGameBatch().end();
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
	
	public int getFogTileIndex(int fogX, int fogY, int fogTextureTileFit)
	{
		if(fogX < 0)
		{
			fogX = (fogX % fogTextureTileFit) + fogTextureTileFit;
		}
		if(fogY < 0)
		{
			fogY = (fogY % fogTextureTileFit) + fogTextureTileFit;
		}
		return fogX % fogTextureTileFit + (fogTextureTileFit - 1 - (fogY % fogTextureTileFit)) * fogTextureTileFit;
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
	
	public float getFogValueAt(int fogX, int fogY)
	{
		if(fogX < 0 || fogY < 0 || fogX >= this.fogOfWarWidth || fogY >= this.fogOfWarHeight)
		{
			return this.defaultFogValue;
		}
		return this.sectors[getSectorX(fogX)][getSectorY(fogY)].get(fogX, fogY);
	}
	
	public float getFogValueAt(float dungeonX, float dungeonY)
	{
		return this.getFogValueAt(this.getFogX(dungeonX), this.getFogY(dungeonY));
	}
	
	private void setFogValueAt(int fogX, int fogY, float value)
	{
		if(fogX < 0 || fogY < 0 || fogX >= this.fogOfWarWidth || fogY >= this.fogOfWarHeight)
		{
			return;
		}
		this.sectors[getSectorX(fogX)][getSectorY(fogY)].set(fogX, fogY, value);
	}
	
	public void clearFog()
	{
		int x, y;
		for(x = 0; x < this.sectorsX; ++x)
		{
			for(y = 0; y < this.sectorsY; ++y)
			{
				this.sectors[x][y].clear(this.defaultFogValue);
			}
		}
	}
	
	public void light(float dungeonX, float dungeonY, float intensity)
	{
		this.lightSources.add(new FogOfWarLightSource(this.getFogX(dungeonX), this.getFogY(dungeonY), intensity));
	}
	
	private void processLightSources()
	{
		for(int i = 0, n = this.lightSources.size(); i < n; ++i)
		{
			this.processLightSource(this.lightSources.get(i));
		}
		this.lightSources.clear();
	}
	
	private void processLightSource(FogOfWarLightSource src)
	{
		for(int x = (int)(src.getX() - 5 / this.fogTileSize); x < src.getX() + 5 / this.fogTileSize; ++x)
		{
			for(int y = (int)(src.getY() - 5 / this.fogTileSize); y < src.getY() + 5 / this.fogTileSize; ++y)
			{
				int diffX = src.getX() - x;
				int diffY = src.getY() - y;
				float f = (float)Math.min((Math.pow(diffX, 2) + Math.pow(diffY, 2)) / (150F / fogTileSize), defaultFogValue);
				if(f < defaultFogValue)
				{
					this.setFogValueAt(x, y, f);
				}
			}
		}
	}
}
