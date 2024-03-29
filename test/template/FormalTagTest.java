/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * FormalTagTest.java
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
public class FormalTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void formalSimple() {
        try {
            System.out.println("Testing formalizing simple text");
            String template = "<template>What, <formal>hello kitty</formal></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("What, Hello Kitty", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void formalComplex() {
        try {
            System.out.println("Testing formalizing complex text");
            String template = "<template>What, <formal>"
                    + "Hello, nice to meet you "
                    + "<condition name=\"gender\" value=\"female\">美女</condition>"
                    + "<condition name=\"gender\" value=\"male\">帅哥</condition>"
                    + "</formal></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            Context context = new Context("u", "s", null);
            context.addPredicate("gender", "male");
            ps.context = context;
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("What, Hello, Nice To Meet You 帅哥", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
