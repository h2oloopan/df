/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * StarMatcher.java
 *
 */
package core.bot.ab.matchers;

import core.bot.ab.Nodemapper;
import core.bot.ab.NodemapperOperator;
import core.bot.ab.Path;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 14, 2015
 */
public class StarMatcher extends PatternMatcher
{

    @Override
    public Nodemapper match(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
            String[] grammarStars, String[] thatStars, String[] topicStars, String matchTrace)
    {
        return MatchHelper.wildMatch(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, "*", matchTrace);
    }

}