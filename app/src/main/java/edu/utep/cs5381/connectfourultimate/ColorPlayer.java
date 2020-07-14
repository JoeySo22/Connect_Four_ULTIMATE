package edu.utep.cs5381.connectfourultimate;

import android.graphics.Color;

import edu.utep.cs5381.connectfourultimate.model.Player;

public class ColorPlayer {
    private Color color;
    private Player player;

    public ColorPlayer(Player player) {
        this.player = player;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public Player getPlayer() {
        return  this.player;
    }
}
