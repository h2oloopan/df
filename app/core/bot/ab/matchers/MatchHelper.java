/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * MatchHelper.java
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
public class MatchHelper
{
    public static Nodemapper zeroMatch(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
        String[] grammarStars, String[] thatStars, String[] topicStars, String wildcard, String matchTrace)
    {
        matchTrace += "[" + wildcard + ",]";
        if (path != null && NodemapperOperator.containsKey(node, wildcard)) {
            //TODO: set stars here
            Nodemapper nextNode = NodemapperOperator.get(node, wildcard);
            return DefaultMatcher.match(path, nextNode, inputThatTopic, starState, starIndex + 1, inputStars, grammarStars, thatStars, topicStars, matchTrace);
        } else {
            matchTrace += "ZEROMATCH FAILED: | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
            return null;
        }
    }
    
    public static Nodemapper wildMatch(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
        String[] grammarStars, String[] thatStars, String[] topicStars, String wildcard, String matchTrace) {
        
    }
}
