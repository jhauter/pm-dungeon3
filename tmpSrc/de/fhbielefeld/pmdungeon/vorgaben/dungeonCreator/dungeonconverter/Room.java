package de.fhbielefeld.pmdungeon.vorgaben.dungeonCreator.dungeonconverter;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a room of the dungeon
 */
public class Room {

    @SerializedName("Node")
    private int node;
    @SerializedName("Shape")
    private Coordinate[] shape;
    @SerializedName("Position")
    private Coordinate position;
    @SerializedName("IsCorridor")
    private boolean isCorridor;
    @SerializedName("Doors")
    private Door[] doors;

    public Room(int node, Coordinate[] shape, Coordinate position, boolean isCorridor, Door[] doors) {
        this.node = node;
        this.shape = shape;
        this.position = position;
        this.isCorridor = isCorridor;
        this.doors = doors;
    }

    /**
     * Determines the size of the room
     *
     * @return The size of the room in x and y direction
     */
    public Coordinate getExtension() {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Coordinate coordinate : this.getShape()) {
            if (maxX < coordinate.getX()) maxX = coordinate.getX();
            if (maxY < coordinate.getY()) maxY = coordinate.getY();
        }
        return new Coordinate(maxX, maxY);
    }

    /**
     * Moves the room and its doors by the given offset
     *
     * @param offset Offset to move the room
     */
    public void move(Coordinate offset) {
        if (offset != null) {
            this.position.add(offset);
            if (doors != null) {
                for (Door door : doors) {
                    door.getFrom().add(offset);
                    door.getTo().add(offset);
                }
            }
        }
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public Coordinate[] getShape() {
        return shape;
    }

    public void setShape(Coordinate[] shape) {
        this.shape = shape;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public boolean isCorridor() {
        return isCorridor;
    }

    public void setCorridor(boolean corridor) {
        isCorridor = corridor;
    }

    public Door[] getDoors() {
        return doors;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }
}
