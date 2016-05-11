package com.stakoun.shove;

/**
 * Created by Peter on 2016-05-06.
 */
public class Player
{
    private String name;
    private Location location;

    public Player(String name)
    {
        this.name = name;
        this.location = null;
    }

    public Player(String name, Location location)
    {
        this.name = name;
        this.location = location;
    }

    public static Player[] arrayFromString(String str)
    {
        String[] playerStrings = str.split(",");
        int numPlayers = playerStrings.length;

        Player[] players = new Player[numPlayers];

        for (int i = 0; i < numPlayers; i++) {
            String[] playerProperties = playerStrings[i].split(" ");
            players[i] = new Player(playerProperties[0],
                    new Location(Float.parseFloat(playerProperties[1]), Float.parseFloat(playerProperties[2])));
        }

        return players;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return this.name + " " + (this.location == null ? "null null" : this.location.toString());
    }

}
