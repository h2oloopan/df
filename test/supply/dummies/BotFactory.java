/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * BotFactory.java
 *
 */
package supply.dummies;

import java.io.File;

import core.bot.Bot;
import core.grammar.GrammarMatcher;
import core.grammar.LaoMaGrammarMatcher;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 21, 2015
 */
public class BotFactory
{
    public static Bot getDummyBot() throws Exception {
        String gramsPath = new File("test/supply/execs/grams.bin").getCanonicalPath();
        GrammarMatcher matcher = new LaoMaGrammarMatcher().initialize(gramsPath);
        Bot bot = new Bot("dummy", new File("test/supply").getCanonicalPath(), matcher);
        return bot;
    }
}
