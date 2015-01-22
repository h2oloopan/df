/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ThinkTagTest.java
 *
 */
package template;

import org.junit.*;
import org.w3c.dom.Node;

import supply.dummies.BotFactory;
import core.bot.Bot;
import core.bot.ab.ParseState;
import core.bot.ab.handlers.TagHandler;
import core.bot.ab.handlers.TagHandlerCollection;
import core.bot.ab.utils.DomUtils;
import core.context.Context;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 22, 2015
 */
public class ThinkTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void thinkSetGetDisplay() {
        try {
            System.out.println("Testing setting a predicate, then getting it back while thinking");
            
            String template = "<template>" 
                    + "<think>"
                    + "<set name=\"he\">琨叔</set>是段子手"
                    + "</think>"
                    + "</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            
            Context context = new Context("dummy user", "dummy session");
            ps.context = context;
            
            Bot bot = BotFactory.getDummyBot();
            ps.bot = bot;
            
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("", result);
            
            template = "<template>" 
                    + "<get name=\"he\"/>还是段子手"
                    + "</template>";
            
            node = DomUtils.parseString(template);
            result = defaultHandler.handle(node, ps);
            Assert.assertEquals("琨叔还是段子手", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
