/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * PatternMatcher.java
 *
 */
package core.bot.ab.matchers;

import core.bot.ab.*;
import core.bot.ab.NodemapperOperator;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 14, 2015
 */
public abstract class PatternMatcher
{
    public abstract Nodemapper match(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, 
            String[] inputStars, String[] grammarStars, String[] thatStars, String[] topicStars, String matchTrace);
}
