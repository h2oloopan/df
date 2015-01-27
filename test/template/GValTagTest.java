/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * GValTagTest.java
 *
 */
package template;

import org.junit.*;

import supply.dummies.BotFactory;
import core.bot.Bot;
import core.bot.ab.Category;
import core.bot.ab.handlers.TagHandlerCollection;
import core.context.Context;
import core.context.Profile;
import core.messages.SpecialText;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 27, 2015
 */
public class GValTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void something() {
        try {
            System.out.println("Testing getting a grammar key with something");
            
            Bot bot = BotFactory.getDummyBot();
            Category c = new Category(0, "*", "default.你好吗", "*", "*", "我不好<gval term=\"default.你好吗\" key=\"who\"/>", "dummy.aiml");
            bot.brain.addCategory(c);
            
            Context context = new Context("uid", "sid");
            Profile profile = new Profile("uid");
            
            String inputOriginal = "你好吗";
            String inputParsed = bot.grammarMatcher.match(inputOriginal);
            
            String result = bot.respond(inputOriginal, inputParsed, SpecialText.NULL, SpecialText.NULL, context, profile);
            
            Assert.assertEquals("我不好你", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void nothing() {
        
    }
}
