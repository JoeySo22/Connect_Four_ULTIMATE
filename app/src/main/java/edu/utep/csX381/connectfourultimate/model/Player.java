package edu.utep.csX381.connectfourultimate.model;

/**
 * A player of the Connect Four game. Each player has a name.
 */
public class Player {

    /** Name of this player. */
    private final String name;

    /** Create a new player with the given name. */
    public Player(String name) {
        this.name = name;
    }

    /** Returns the name of this player. */
    public String name() {
        return name;
    }
}