/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * Stars.java
 *
 */
package core.bot.knowledge;

import java.util.ArrayList;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 8, 2015
 */
public class Stars extends ArrayList<String>
{
    public String star (int i) {
        if (i < size())
        return get(i);
        else return null;
    }
}
