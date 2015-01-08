/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * StarBindings.java
 *
 */
package core.bot.knowledge;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 8, 2015
 */
public class StarBindings
{
    public Stars inputStars;
    public Stars thatStars;
    public Stars topicStars;
    /** Constructor  -- this class has public members
     *
     */
    public StarBindings () {
        inputStars = new Stars();
        thatStars = new Stars();
        topicStars = new Stars();
    }
}
