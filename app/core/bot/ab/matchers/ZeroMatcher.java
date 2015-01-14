/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ZeroMatcher.java
 *
 */
package core.bot.ab.matchers;

import core.bot.ab.Nodemapper;
import core.bot.ab.Path;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 14, 2015
 */
public class ZeroMatcher
{

    @Override
    public Nodemapper match(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
            String[] grammarStars, String[] thatStars, String[] topicStars, String wildcard, String matchTrace)
    {
        matchTrace += "[" + wildcard + ",]";
        
    }

}
