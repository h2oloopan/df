/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * NullMatcher.java
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
public class NullMatcher extends PatternMatcher
{

    @Override
    public Nodemapper match(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
            String[] grammarStars, String[] thatStars, String[] topicStars, String matchTrace)
    {
        if (path == null && node != null && NodemapperOperator.isLeaf(node) && node.category != null) {
        	return node;
        } else {
        	matchTrace += "NULLMATCHER FAILED: | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
        	return null;
        }
    }

}
