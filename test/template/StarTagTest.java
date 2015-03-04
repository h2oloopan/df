/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * StarTagTest.java
 *
 */
package template;

import org.junit.*;
import org.w3c.dom.Node;

import supply.dummies.BotFactory;
import core.bot.Bot;
import core.bot.ab.Category;
import core.bot.ab.ParseState;
import core.bot.ab.handlers.TagHandler;
import core.bot.ab.handlers.TagHandlerCollection;
import core.bot.ab.utils.DomUtils;
import core.context.Context;
import core.context.Profile;
import core.messages.SpecialText;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 22, 2015
 */
public class StarTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void noStar() {
        try {
            System.out.println("Testing input star with no star");
            
            Bot bot = BotFactory.getDummyBot();
            Category c = new Category(0, "你好", "*", "*", "*", "我不好<star/>", "dummy.aiml");
            bot.brain.addCategory(c);
            
            Context context = new Context("uid", "sid", null);
            Profile profile = new Profile("uid", null);
            String result = bot.respond("你好", SpecialText.NULL, SpecialText.NULL, SpecialText.NULL, context, profile);
            
            Assert.assertEquals("我不好", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void oneStar() {
        try {
            System.out.println("Testing input star with one star");
            
            Bot bot = BotFactory.getDummyBot();
            Category c = new Category(0, "你好, *", "*", "*", "*", "我不好, <star/>", "dummy.aiml");
            bot.brain.addCategory(c);
            
            Context context = new Context("uid", "sid", null);
            Profile profile = new Profile("uid", null);
            String result = bot.respond("你好, 老秦", SpecialText.NULL, SpecialText.NULL, SpecialText.NULL, context, profile);
            
            Assert.assertEquals("我不好, 老秦", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void twoStars() {
        try {
            System.out.println("Testing input star with two stars");
            
            Bot bot = BotFactory.getDummyBot();
            Category c = new Category(0, "* 你好 *", "*", "*", "*", "<star index=\"2\"/> 我不好, <star index=\"1\"/>", "dummy.aiml");
            bot.brain.addCategory(c);
            
            Context context = new Context("uid", "sid", null);
            Profile profile = new Profile("uid", null);
            String result = bot.respond("琨叔 你好 老秦", SpecialText.NULL, SpecialText.NULL, SpecialText.NULL, context, profile);
            
            Assert.assertEquals("老秦 我不好, 琨叔", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
