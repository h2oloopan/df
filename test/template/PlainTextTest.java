/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * PlainTextTest.java
 *
 */
package template;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import core.bot.ab.ParseState;
import core.bot.ab.handlers.TagHandler;
import core.bot.ab.handlers.TagHandlerCollection;
import core.bot.ab.utils.DomUtils;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 20, 2015
 */
public class PlainTextTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() {
        this.handlers = new TagHandlerCollection();
    }
    
    @Test
    public void empty() {
        try {
            String template = "<template></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            //String result = handlers.get("default").handle(n, ps);
            //Assert.assertEquals("", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
