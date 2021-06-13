package de.fhbielefeld.pmdungeon.quibble;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.fhbielefeld.pmdungeon.quibble.file.DungeonResource;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceHandler;
import de.fhbielefeld.pmdungeon.quibble.file.ResourceType;
import de.fhbielefeld.pmdungeon.quibble.particle.DrawingUtil;

public class FogOfWarController
{
	private ArrayList<Animation<TextureRegion>> fogAnimTiles;
	private int fogTextureTileFit;
	private float fogAnimStateTime;
	
	private final float[][] fogTiles;
	private final int fogOfWarWidth;
	private final int fogOfWarHeight;
	private final float fogTileSize;
	
	public FogOfWarController(float fogTileSize, int levelWidth, int levelHeight)
	{
		if(fogTileSize <= 0.0F)
		{
			throw new IllegalArgumentException("fogTileSize cannot be <= 0");
		}
		if(levelWidth <= 0 || levelHeight <= 0)
		{
			throw new IllegalArgumentException("level dimensions cannot be <= 0");
		}
		
		this.fogOfWarWidth = (int)Math.ceil(levelWidth / fogTileSize);
		this.fogOfWarHeight = (int)Math.ceil(levelHeight / fogTileSize);
		this.fogTiles = new float[this.fogOfWarWidth][this.fogOfWarHeight];
		this.fogTileSize = fogTileSize;
		this.fogAnimTiles = new ArrayList<>();
		
		for(int x = 0; x < this.fogOfWarWidth; ++x)
		{
			for(int y = 0; y < this.fogOfWarHeight; ++y)
			{
				this.fogTiles[x][y] = 0.85F;
			}
		}
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
				DungeonStart.getGameBatch().draw(this.fogAnimTiles.get(getFogTileIndex(x, y, fogTextureTileFit)).getKeyFrame(fogAnimStateTime, true), x * fogTileSize, y * fogTileSize, fogTileSize, fogTileSize);
			}
		}

		DungeonStart.getGameBatch().setColor(1.0F, 1.0F, 1.0F, 1.0F);
		DungeonStart.getGameBatch().end();
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
	
	public int getFogOfWarWidth()
	{
		return this.fogOfWarWidth;
	}
	
	public int getFogOfWarHeight()
	{
		return this.fogOfWarHeight;
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
	
	public float getFogValueAt(int fogX, int fogY)
	{
		if(fogX < 0 || fogY < 0)
		{
			return 1.0F;
		}
		return this.fogTiles[fogX][fogY];
	}
	
	public float getFogValueAt(float dungeonX, float dungeonY)
	{
		return this.fogTiles[this.getFogX(dungeonX)][this.getFogY(dungeonY)];
	}
}
