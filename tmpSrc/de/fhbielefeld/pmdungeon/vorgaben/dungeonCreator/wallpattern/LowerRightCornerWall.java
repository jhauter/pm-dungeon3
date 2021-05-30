package de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.wallpattern;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectMap;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonTextures;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;

public class LowerRightCornerWall extends WallPattern {

    public LowerRightCornerWall(ObjectMap<DungeonTextures, Texture> textureMap) {
        super(textureMap);

        this.patternList.add(new Tile.Type[][]{
                {A, W, A},
                {W, W, A},
                {A, A, A}
        });
    }

    @Override
    public void render(SpriteBatch batch, Coordinate position) {
        batch.draw(textureMap.get(DungeonTextures.WALL_SIDE_MID_RIGHT), position.getX(), position.getY() + 1f, 1, 1);
        batch.draw(textureMap.get(DungeonTextures.WALL_SIDE_FRONT_RIGHT), position.getX(), position.getY(), 1, 1);
        batch.draw(textureMap.get(DungeonTextures.WALL_CORNER_FRONT_RIGHT), position.getX() - 1f, position.getY(), 1, 1);
    }
}
