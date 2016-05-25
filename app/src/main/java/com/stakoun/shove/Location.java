package com.stakoun.shove;

/**
 * The Location class stores the x and y coordinates of a location in the game.
 *
 * @author Peter Stakoun
 */
public class Location
{
    private float x, y;

    /**
     * The default constructor for the Location class.
     *
     * @param x the x coordinate of the Location
     * @param y the y coordinate of the Location
     */
    public Location(float x, float y)
    {
        setX(x);
        setY(y);
    }

    /**
     * The getX method returns the Location's x coordinate.
     *
     * @return x coordinate of Location
     */
    public float getX()
    {
        return x;
    }

    /**
     * The setX method sets the Location's x coordinate to the given value.
     *
     * @param x given x coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * The getY method returns the Location's y coordinate.
     *
     * @return y coordinate of Location
     */
    public float getY() {
        return y;
    }

    /**
     * The setY method sets the Location's y coordinate to the given value.
     *
     * @param y given y coordinate
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * The toString method returns the x and y coordinates of the Location separated by a single space.
     *
     * @return a String representation of the Location
     */
    @Override
    public String toString() {
        return x + " " + y;
    }

}
