/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * WordMatcher.java
 *
 */
package core.bot.ab.matchers;

import play.Logger;
import core.bot.ab.Nodemapper;
import core.bot.ab.NodemapperOperator;
import core.bot.ab.Path;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 14, 2015
 */
public class WordMatcher extends PatternMatcher
{

    @Override
    public Nodemapper match(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
            String[] grammarStars, String[] thatStars, String[] topicStars, String matchTrace)
    {
        Nodemapper matchedNode;
        try {
            String uword = path.word.toUpperCase();
            if (uword.equals("<THAT>")) {
                starIndex = 0; starState = "thatStar"; 
            } else if (uword.equals("<TOPIC>")) {
                starIndex = 0;
                starState = "topicStar";
            }
            
            matchTrace += "[" + uword + "," + uword + "]\r\n";
            if (path != null && NodemapperOperator.containsKey(node, uword) &&
                    (matchedNode = DefaultMatcher.match(path.next, NodemapperOperator.get(node, uword), inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace)) != null) {
                return matchedNode;
            } else {
                matchTrace += "WORDMATCH FAILED: | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
                return null;
            }
                
            
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            matchTrace += "WORDMATCH FAILED: | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
            return null;
        }
    }

}
