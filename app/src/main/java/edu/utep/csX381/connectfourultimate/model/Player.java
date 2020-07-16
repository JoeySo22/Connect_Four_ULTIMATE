package edu.utep.csX381.connectfourultimate.model;

import android.graphics.Paint;

/**
 * A player of the Connect Four game. Each player has a name.
 */
public class Player {

    /** Name of this player. */
    private final String name;
    // Our player needs unique colors. We implement inside Player rather than extend. Granular enough
    private Paint paint;

    /** Create a new player with the given name. */
    public Player(String name) {
        this.name = name;
        paint = new Paint();
    }

    /** Returns the name of this player. */
    public String name() {
        return name;
    }
    // Allows outside to give our color ID to paint.
    public void setColor(int colorID) {
        paint.setColor(colorID);
    }
    // Our game needs to get the paint to draw with Canvas
    public Paint getPaint() {
        return paint;
    }
}