/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * RandomTagTest.java
 *
 */
package template;

import org.junit.*;
import org.w3c.dom.Node;

import core.bot.ab.ParseState;
import core.bot.ab.handlers.TagHandler;
import core.bot.ab.handlers.TagHandlerCollection;
import core.bot.ab.utils.DomUtils;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 20, 2015
 */
public class RandomTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void oneOption() {
        try {
            System.out.println("Testing random tag with one option");
            String template = "<template><random><li>唯一</li></random></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("唯一", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void twoOptions() {
        try {
            System.out.println("Testing random tag with two options");
            String template = "<template><random><li>唯一</li><li>唯二</li></random></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            if (!result.equals("唯一") && !result.equals("唯二")) {
                Assert.fail("The result must be one of the two options");
            }
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
