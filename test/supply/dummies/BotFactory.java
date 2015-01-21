/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * BotFactory.java
 *
 */
package supply.dummies;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import core.bot.Bot;
import core.bot.ab.AIMLMap;
import core.bot.ab.MagicBooleans;
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
    
    public static Bot getDummyBotWithMap(String path) throws Exception {
        Bot bot = getDummyBot();
        bot.mapMap = new HashMap<String, AIMLMap>();
        File file = new File(path);
        String name = file.getName().substring(0, file.getName().length() - ".txt".length());
        AIMLMap aimlMap = new AIMLMap(name, bot);
        
        FileInputStream fstream = new FileInputStream(file.getCanonicalPath());
        aimlMap.readAIMLMapFromInputStream(fstream, bot);
        
        bot.mapMap.put(name, aimlMap);
        return bot;
    }
    
    public static Bot getDummyBotWithProperties(String path) throws Exception {
        Bot bot = getDummyBot();
        bot.properties.getProperties(path);
        return bot;
    }
}
