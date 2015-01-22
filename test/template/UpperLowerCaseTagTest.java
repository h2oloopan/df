/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * UpperLowerCaseTagTest.java
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
public class UpperLowerCaseTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void uppercaseSimple() {
        try {
            System.out.println("Testing uppercase simple text");
            String template = "<template>What, <uppercase>Hello Kitty</uppercase></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("What, HELLO KITTY", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void lowercaseSimple() {
        try {
            System.out.println("Testing lowercase simple text");
            String template = "<template>What, <lowercase>Hello Kitty</lowercase></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("What, hello kitty", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test 
    public void uppercaseComplex() {
        try {
            System.out.println("Testing uppercase complex text");
            String template = "<template>What, <uppercase>"
                    + "Hello, "
                    + "<condition name=\"gender\" value=\"female\">美女</condition>"
                    + "<condition name=\"gender\" value=\"male\">帅哥</condition>"
                    + "</uppercase></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            Context context = new Context("u", "s");
            context.addPredicate("gender", "male");
            ps.context = context;
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("What, HELLO, 帅哥", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void lowercaseComplex() {
        try {
            System.out.println("Testing uppercase complex text");
            String template = "<template>What, <lowercase>"
                    + "Hello, "
                    + "<condition name=\"gender\" value=\"female\">美女</condition>"
                    + "<condition name=\"gender\" value=\"male\">帅哥</condition>"
                    + "</lowercase></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            Context context = new Context("u", "s");
            context.addPredicate("gender", "male");
            ps.context = context;
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("What, hello, 帅哥", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
