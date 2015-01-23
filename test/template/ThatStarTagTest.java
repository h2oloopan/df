/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ThatStarTagTest.java
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
 *@date Jan 23, 2015
 */
public class ThatStarTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void noStar() {
        try {
            System.out.println("Testing that star with no star");
            
            Bot bot = BotFactory.getDummyBot();
            Category c = new Category(0, "你好", "*", "什么", "*", "我不好<star/>", "dummy.aiml");
            bot.brain.addCategory(c);
            
            Context context = new Context("uid", "sid");
            Profile profile = new Profile("uid");
            String result = bot.respond("你好", SpecialText.NULL, "什么", SpecialText.NULL, context, profile);
            
            Assert.assertEquals("我不好", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void oneStar() {
        try {
            System.out.println("Testing that star with one star");
            
            Bot bot = BotFactory.getDummyBot();
            Category c = new Category(0, "你好", "*", "* 什么", "*", "我不好, <thatstar/>", "dummy.aiml");
            bot.brain.addCategory(c);
            
            Context context = new Context("uid", "sid");
            Profile profile = new Profile("uid");
            String result = bot.respond("你好", SpecialText.NULL, "为 什么", SpecialText.NULL, context, profile);
            
            Assert.assertEquals("我不好, 为", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void twoStars() {
        try {
            System.out.println("Testing that star with two stars");
            
            Bot bot = BotFactory.getDummyBot();
            Category c = new Category(0, "你好", "*", "* 什么 *", "*", "<thatstar index=\"2\"/> 我不好, <thatstar index=\"1\"/>", "dummy.aiml");
            bot.brain.addCategory(c);
            
            Context context = new Context("uid", "sid");
            Profile profile = new Profile("uid");
            String result = bot.respond("你好", SpecialText.NULL, "为 什么 阿", SpecialText.NULL, context, profile);
            
            Assert.assertEquals("阿 我不好, 为", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
