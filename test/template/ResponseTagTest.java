/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ResponseTagTest.java
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
 *@date Jan 26, 2015
 */
public class ResponseTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void nothing() {
        try {
            System.out.println("Testing response tag with nothing in history");
            
            Bot bot = BotFactory.getDummyBot();
            
            Category c1 = new Category(0, "你好", "*", "*", "*", "我不好", "dummy.aiml");
            bot.brain.addCategory(c1);
            
            Category c2 = new Category(0, "你说了什么", "*", "*", "*", "没什么<response index=\"2\"/>", "dummy.aiml");
            bot.brain.addCategory(c2);
            
            Context context = new Context("uid", "sid", null);
            Profile profile = new Profile("uid", null);
            String result = bot.respond("你好", SpecialText.NULL, SpecialText.NULL, SpecialText.NULL, context, profile);
            result = bot.respond("你说了什么", SpecialText.NULL, SpecialText.NULL, SpecialText.NULL, context, profile);
            
            Assert.assertEquals("没什么" + SpecialText.NULL, result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void something() {
        try {
            System.out.println("Testing response tag with something in history");
            
               Bot bot = BotFactory.getDummyBot();
            
            Category c1 = new Category(0, "你好", "*", "*", "*", "我不好", "dummy.aiml");
            bot.brain.addCategory(c1);
            
            Category c2 = new Category(0, "你说了什么", "*", "*", "*", "没什么<response index=\"1\"/>", "dummy.aiml");
            bot.brain.addCategory(c2);
            
            Context context = new Context("uid", "sid", null);
            Profile profile = new Profile("uid", null);
            String result = bot.respond("你好", SpecialText.NULL, SpecialText.NULL, SpecialText.NULL, context, profile);
            context.insert("你好", result);
            result = bot.respond("你说了什么", SpecialText.NULL, SpecialText.NULL, SpecialText.NULL, context, profile);
            
            Assert.assertEquals("没什么我不好", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
