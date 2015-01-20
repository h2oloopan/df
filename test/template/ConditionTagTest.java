/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ConditionTagTest.java
 *
 */
package template;

import org.junit.*;
import org.w3c.dom.Node;

import core.bot.ab.ParseState;
import core.bot.ab.handlers.TagHandler;
import core.bot.ab.handlers.TagHandlerCollection;
import core.bot.ab.utils.DomUtils;
import core.context.Context;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 20, 2015
 */
public class ConditionTagTest
{
private TagHandlerCollection handlers;
    
    @Before
    public void setup() {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void multiConditionTags() {
        try {
            System.out.println("Testing multi condition tags");
            String template = "<template>" 
                    + "<condition name=\"gender\" value=\"female\">美女</condition>"
                    + "<condition name=\"gender\" value=\"male\">帅哥</condition>"
                    + "</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            
            Context context = new Context("dummy user", "dummy session");
            context.addPredicate("gender", "male");
            ps.context = context;
            
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("帅哥", result);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
