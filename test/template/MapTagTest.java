/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * MapTagTest.java
 *
 */
package template;
import java.io.File;

import org.junit.*;

import supply.dummies.BotFactory;
import core.bot.Bot;
import core.bot.ab.handlers.TagHandlerCollection;
/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 21, 2015
 */
public class MapTagTest
{
    private TagHandlerCollection handlers;
    private Bot bot;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
        bot = BotFactory.getDummyBotWithMap(new File("test/supply/map.txt").getCanonicalPath());
    }
    
    
}
