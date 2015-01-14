/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * DefaultMatcher.java
 *
 */
package core.bot.ab.matchers;

import play.Logger;
import core.bot.ab.Nodemapper;
import core.bot.ab.Path;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 14, 2015
 */
public class DefaultMatcher extends PatternMatcher
{
	public int matchCount = 0;

	private NullMatcher nullMatcher = new NullMatcher();
	private DollarMatcher dollarMatcher = new DollarMatcher();
	private SharpMatcher sharpMatcher = new SharpMatcher();
	private UnderMatcher underMatcher = new UnderMatcher();
	private WordMatcher wordMatcher = new WordMatcher();
	private SetMatcher setMatcher = new SetMatcher();
	private ShortcutMatcher shortcutMatcher = new ShortcutMatcher();
	private CaretMatcher caretMatcher = new CaretMatcher();
	private StarMatcher starMatcher = new StarMatcher();

    @Override
    public Nodemapper match(Path path, Nodemapper node, String inputThatTopic, String starState, int starIndex, String[] inputStars,
            String[] grammarStars, String[] thatStars, String[] topicStars, String matchTrace)
    {
        Nodemapper matchedNode;
        Logger.info("Match: Height="+node.height+" Length="+path.length+" Path="+Path.pathToSentence(path));
        matchCount++;
        if (matchedNode = nullMatcher.match(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace) != null) {
        	return matchedNode;
        } else if (path.length < node.height) {
        	return null;
        } else if (matchedNode = dollarMatcher.match(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace) != null ) {
        	return matchedNode;
        } else if (matchedNode = sharpMatcher.match(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace) != null) {
        	return matchedNode;
        } else if (matchedNode = underMatcher.match(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace) != null) {
        	return matchedNode;
        } else if (matchedNode = wordMatcher.match(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace) != null) {
        	return matchedNode;
        } else if (matchedNode = setMatcher.match(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace) != null) {
        	return matchedNode;
        } else if (matchedNode = shortcutMatcher.match(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace) != null) {
        	return matchedNode;
        } else if (matchedNode = caretMatcher.match(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace) != null) {
        	return matchedNode;
        } else if (matchedNode = starMatcher.match(path, node, inputThatTopic, starState, starIndex, inputStars, grammarStars, thatStars, topicStars, matchTrace) != null) {
        	return matchedNode;
        }
    }

}
