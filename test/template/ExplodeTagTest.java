/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ExplodeTagTest.java
 *
 */
package template;

import org.junit.*;
import org.w3c.dom.Node;

import supply.dummies.BotFactory;
import core.bot.ab.ParseState;
import core.bot.ab.handlers.TagHandler;
import core.bot.ab.handlers.TagHandlerCollection;
import core.bot.ab.utils.DomUtils;
import core.context.Context;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 22, 2015
 */
public class ExplodeTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void explodeEnglishWord() {
        try {
            System.out.println("Testing exploding an English word");
            String template = "<template><explode>Hello Kitty</explode></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("H e l l o K i t t y", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void explodeChineseWord() {
        try {
            System.out.println("Testing exploding an Chinese word");
            String template = "<template><explode>老秦和琨叔</explode></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("老 秦 和 琨 叔", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
