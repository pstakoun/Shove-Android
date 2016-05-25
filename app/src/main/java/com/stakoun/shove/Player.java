package com.stakoun.shove;

/**
 * The Player class stores the information of a player in the game.
 * 3.1a Using classes
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
     * 3.1b Using constructors
     *
     * @param name the Player's display name
     */
    public Player(String name)
    {
        // 3.1h Calling methods in same class
        setName(name);
        setLocation(null);
    }

    /**
     * A constructor for the Player class that takes in its display name, location, and color as parameters.
     * 3.1b Using overloaded methods/constructors
     *
     * @param name the Player's display name
     * @param location the Player's location
     * @param color the Player's color
     */
    public Player(String name, Location location, int color)
    {
        // 3.1h Calling methods in same class
        setName(name);
        setLocation(location);
        setColor(color);
    }

    /**
     * The arrayFromString method converts a String of player data into an array of Players
     * 3.1c Using arrays
     * 3.1f Using String parsing
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
        // 3.1d Using loops
        for (int i = 0; i < numPlayers; i++) {
            String[] playerProperties = playerStrings[i].split(" ");
            players[i] = new Player(playerProperties[0], new Location(Float.parseFloat(playerProperties[1]), Float.parseFloat(playerProperties[2])), Integer.parseInt(playerProperties[3]));
        }

        // Return converted Player array
        return players;
    }

    /**
     * The getName method returns the Player's display name.
     * 3.1b Using accessors
     *
     * @return display name of Player
     */
    public String getName() {
        return name;
    }

    /**
     * The setName method sets the Player's display name to the given value.
     * 3.1b Using mutators
     *
     * @param name given display name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getLocation method returns the Player's location.
     * 3.1b Using accessors
     *
     * @return Location of Player
     */
    public Location getLocation() {
        return location;
    }

    /**
     * The setLocation method sets the Player's Location to the given Location.
     * 3.1b Using mutators
     *
     * @param location given Location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * The getColor method returns the Player's color id.
     * 3.1b Using accessors
     *
     * @return color id of Player
     */
    public int getColor() {
        return color;
    }

    /**
     * The setColor method sets the Player's color id to the given integer.
     * 3.1b Using mutators
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
