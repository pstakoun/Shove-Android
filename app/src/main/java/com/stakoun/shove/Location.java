package com.stakoun.shove;

/**
 * The Location class stores the x and y coordinates of a location in the game.
 * 3.1a Using classes
 *
 * @author Peter Stakoun
 */
public class Location
{
    private float x, y;

    /**
     * The default constructor for the Location class.
     * 3.1b Using constructors
     *
     * @param x the x coordinate of the Location
     * @param y the y coordinate of the Location
     */
    public Location(float x, float y)
    {
        // 3.1h Calling methods in same class
        setX(x);
        setY(y);
    }

    /**
     * The getX method returns the Location's x coordinate.
     * 3.1b Using accessors
     *
     * @return x coordinate of Location
     */
    public float getX()
    {
        return x;
    }

    /**
     * The setX method sets the Location's x coordinate to the given value.
     * 3.1b Using mutators
     *
     * @param x given x coordinate
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * The getY method returns the Location's y coordinate.
     * 3.1b Using accessors
     *
     * @return y coordinate of Location
     */
    public float getY() {
        return y;
    }

    /**
     * The setY method sets the Location's y coordinate to the given value.
     * 3.1b Using mutators
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
