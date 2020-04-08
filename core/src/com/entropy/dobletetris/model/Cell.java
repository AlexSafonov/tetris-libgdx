package com.entropy.dobletetris.model;


import com.badlogic.gdx.math.Vector2;

public class Cell {
    private final Vector2 coordinates;
    private boolean isFull = false;
    //private Sprite sprite;

    public Cell(float x, float y) {
        this.coordinates = new Vector2(x,y);
    }

    public Vector2  getCoordinates() {
        return coordinates;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }
}
