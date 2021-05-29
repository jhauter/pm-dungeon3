package de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.wallpattern;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.DungeonWorld;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;
import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.tiles.Tile;

/**
 * Reference pattern
 */
public class DungeonCutout extends WallPattern {

    public DungeonCutout() {
        super(null);
        this.patternList.add(new Tile.Type[3][3]);
    }

    /**
     * Cuts a pattern from the dungeon
     *
     * @param dungeon Complete dungeon
     * @param center  Center of the reference pattern.
     */
    public void fromDungeonTiles(DungeonWorld dungeon, Coordinate center) {
        // inverted arraycopy to account for internal mirrored representation
        Tile.Type[][] pattern = new Tile.Type[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (center.getX() - 1 + x < 0 || center.getX() - 1 + x >= dungeon.getWidth() ||
                        center.getY() - 1 + y < 0 || center.getY() - 1 + y >= dungeon.getHeight()) {
                    pattern[2 - y][x] = A;
                } else {
                    pattern[2 - y][x] = dungeon.getTileTypeAt(center.getX() - 1 + x, center.getY() - 1 + y);
                }
            }
        }
        patternList.add(pattern);
    }

    @Override
    public void render(SpriteBatch batch, Coordinate position) {
        throw new UnsupportedOperationException("DungeonCutout can't be rendered");
    }
}
