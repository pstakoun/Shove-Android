package com.stakoun.shove;

/**
 * The Player class stores the information of a player in the game.
 *
 * @author Peter Stakoun
 */
public class Player
{
    private String name;
    private Location location;
    private int color;

    /**
     * A constructor for the Player class that takes in its display name as a parameter.
     *
     * @param name the Player's display name
     */
    public Player(String name)
    {
        setName(name);
        setLocation(null);
    }

    /**
     * A constructor for the Player class that takes in its display name, location, and color as parameters.
     *
     * @param name the Player's display name
     * @param location the Player's location
     * @param color the Player's color
     */
    public Player(String name, Location location, int color)
    {
        setName(name);
        setLocation(location);
        setColor(color);
    }

    /**
     * The arrayFromString method converts a String of player data into an array of Players
     *
     * @param str Player data in String format
     * @return a Player array from the given data
     */
    public static Player[] arrayFromString(String str)
    {
        // Get individual Player data
        String[] playerStrings = str.split(",");
        // Get number of Players
        int numPlayers = playerStrings.length;
        // Initialize Player array
        Player[] players = new Player[numPlayers];

        // Initialize each Player in array from given data
        for (int i = 0; i < numPlayers; i++) {
            String[] playerProperties = playerStrings[i].split(" ");
            players[i] = new Player(playerProperties[0], new Location(Float.parseFloat(playerProperties[1]), Float.parseFloat(playerProperties[2])), Integer.parseInt(playerProperties[3]));
        }

        // Return converted Player array
        return players;
    }

    /**
     * The getName method returns the Player's display name.
     *
     * @return display name of Player
     */
    public String getName() {
        return name;
    }

    /**
     * The setName method sets the Player's display name to the given value.
     *
     * @param name given display name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getLocation method returns the Player's location.
     *
     * @return Location of Player
     */
    public Location getLocation() {
        return location;
    }

    /**
     * The setLocation method sets the Player's Location to the given Location.
     *
     * @param location given Location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * The getColor method returns the Player's color id.
     *
     * @return color id of Player
     */
    public int getColor() {
        return color;
    }

    /**
     * The setColor method sets the Player's color id to the given integer.
     *
     * @param color given color id
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * The toString method returns the Player's display name and Location.
     *
     * @return a String representation of the Player
     */
    @Override
    public String toString() {
        return this.name + " " + (this.location == null ? "null null" : this.location.toString());
    }

}
