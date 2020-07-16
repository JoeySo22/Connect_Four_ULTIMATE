package edu.utep.csX381.connectfourultimate.model;

import android.graphics.Paint;

/**
 * A player of the Connect Four game. Each player has a name.
 */
public class Player {

    /** Name of this player. */
    private final String name;
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
    public void setColor(int colorID) {
        paint.setColor(colorID);
    }

    public Paint getPaint() {
        return paint;
    }
}