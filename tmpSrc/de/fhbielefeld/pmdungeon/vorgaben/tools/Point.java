package de.fhbielefeld.pmdungeon.vorgaben.tools;

import de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter.Coordinate;

/**
 * For easy handling of positions in the dungeon. <br>
 * No getter needed. All attributes are public. <br>
 * Point.x to get x <br>
 * Point.y to get y <br>
 */
public class Point {
    public float x;
    public float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * The dungeontiles work with Coordinates. Use this to generate a Point out of a Coordinate-Instance.
     *
     * @param c
     */
    public Point(Coordinate c) {
        this.x = c.getX();
        this.y = c.getY();
    }

    /**
     * Copy Point
     *
     * @param p
     */
    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
}
