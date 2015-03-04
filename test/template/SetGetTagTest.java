/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SetTagTest.java
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
 *@date Jan 21, 2015
 */
public class SetGetTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void setThenGet() {
        try {
            System.out.println("Testing set a predicate then get it back");
            
            String template = "<template>" 
                    + "<set name=\"he\">琨叔</set>是段子手"
                    + "</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            
            Context context = new Context("dummy user", "dummy session", null);
            ps.context = context;
            
            Bot bot = BotFactory.getDummyBot();
            ps.bot = bot;
            
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("琨叔是段子手", result);
            
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
