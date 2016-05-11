package com.stakoun.shove;

/**
 * Created by Peter on 2016-05-06.
 */
public class Location
{
    private float x, y;

    public Location(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

}
