/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SentenceTagTest.java
 *
 */
package template;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
public class SentenceTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void sentenceSimple() {
        try {
            System.out.println("Testing sentencing simple text");
            String template = "<template>What, <sentence>hello kitty</sentence></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("What, Hello kitty", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void sentenceComplex() {
        try {
            System.out.println("Testing sentencing complex text");
            String template = "<template>What, <sentence>"
                    + "hello, nice to meet you "
                    + "<condition name=\"gender\" value=\"female\">美女</condition>"
                    + "<condition name=\"gender\" value=\"male\">帅哥</condition>"
                    + "</sentence></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            Context context = new Context("u", "s");
            context.addPredicate("gender", "male");
            ps.context = context;
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("What, Hello, nice to meet you 帅哥", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
