/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * DollarMatcher.java
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
public class DollarMatcher extends PatternMatcher
{

    @Override
    public Nodemapper match(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
            String[] grammarStars, String[] thatStars, String[] topicStars, String matchTrace)
    {
        String uword = "$" + path.word.toUpperCase();
        Nodemapper matchedNode;
        if (path != null && NodemapperOperator.containsKey(node, uword) &&
                (matchedNode = DefaultMatcher.match(path.next, NodemapperOperator.get(node, uword), inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace)) != null) {
            return matchedNode;
        } else {
            matchTrace += "DOLLARMATCHER FAILED: | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
            return null;
        }
    }

}
