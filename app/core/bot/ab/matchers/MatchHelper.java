/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * MatchHelper.java
 *
 */
package core.bot.ab.matchers;

import play.Logger;
import core.bot.ab.MagicNumbers;
import core.bot.ab.Nodemapper;
import core.bot.ab.NodemapperOperator;
import core.bot.ab.Path;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 14, 2015
 */
public class MatchHelper
{
    public static void setStars(String starWords, int starIndex, String starState,
            String[] inputStars, String[] grammarStars, String[] thatStars, String[] topicStars) {
        if (starIndex < MagicNumbers.max_stars) {
            starWords = starWords.trim();
            switch (starState.toLowerCase()) {
                case "inputstar":
                    inputStars[starIndex] = starWords;
                    break;
                case "grammarstar":
                    grammarStars[starIndex] = starWords;
                    break;
                case "thatstar":
                    thatStars[starIndex] = starWords;
                    break;
                case "topicstar":
                    topicStars[starIndex] = starWords;
                    break;
            }
        }
    }
    
    public static Nodemapper zeroMatch(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
        String[] grammarStars, String[] thatStars, String[] topicStars, String wildcard, String matchTrace)
    {
        matchTrace += "[" + wildcard + ",]\r\n";
        if (path != null && NodemapperOperator.containsKey(node, wildcard)) {
            //TODO: set stars here
            Nodemapper nextNode = NodemapperOperator.get(node, wildcard);
            return DefaultMatcher.match(path, nextNode, inputThatTopic, starState, starIndex + 1, inputStars, grammarStars, thatStars, topicStars, matchTrace);
        } else {
            matchTrace += "ZEROMATCH FAILED: | WILDCARD: " + wildcard + " | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
            return null;
        }
    }
    
    public static Nodemapper wildMatch(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
        String[] grammarStars, String[] thatStars, String[] topicStars, String wildcard, String matchTrace) {
        Nodemapper matchedNode;
        if (path.word.equals("<THAT>") || path.word.equals("<TOPIC>")) {
            matchTrace += "WILDMATCH FAILED: | WILDCARD: " + wildcard + " | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
            return null;
        } else {
            try {
                if (path != null && NodemapperOperator.containsKey(node, wildcard)) {
                    matchTrace += "[" + wildcard + "," + path.word + "]\r\n";
                    String currentWord;
                    String starWords;
                    Path pathStart;
                    currentWord = path.word;
                    starWords = currentWord + " ";
                    pathStart = path.next;
                    Nodemapper nextNode = NodemapperOperator.get(node, wildcard);
                    if (NodemapperOperator.isLeaf(nextNode) && !nextNode.shortCut) {
                        matchedNode = nextNode;
                        starWords = Path.pathToSentence(path);
                        //TODO: set stars here
                        //setStars(starWords, starIndex, starState, inputStars, grammarStars, thatStars, topicStars);
                        return matchedNode;
                    } else {
                        for (path = pathStart; path != null && !currentWord.equals("<THAT>") && !currentWord.equals("<TOPIC>"); path = path.next) {
                            matchTrace += "[" + wildcard + "," + path.word + "]";
                            if ((matchedNode = DefaultMatcher.match(path, nextNode, inputThatTopic, starState, starIndex + 1, inputStars, grammarStars, thatStars, topicStars, matchTrace)) != null) {
                                return matchedNode;
                            } else {
                                currentWord = path.word;
                                starWords += currentWord + " ";
                            }
                        }
                        matchTrace += "WILDMATCH FAILED: | WILDCARD: " + wildcard + " | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
                        return null;
                    }
                    
                } else {
                    matchTrace += "WILDMATCH FAILED: | WILDCARD: " + wildcard + " | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
                    return null;
                }
            } catch (Exception e) {
                Logger.error(e.getMessage(), e);
                matchTrace += "WILDMATCH FAILED: | WILDCARD: " + wildcard + " | PATH: " + path.toString() + " | NODE: " + node.toString() + "\r\n";
                return null;
            }
        }
    }
}
