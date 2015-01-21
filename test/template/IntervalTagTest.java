/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * IntervalTagTest.java
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

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 21, 2015
 */
public class IntervalTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void intervalBetweenTwoDates() {
        try {
            System.out.println("Testing size of interval between two dates");
            
            String template = "<template>" 
                    + "<interval>"
                    + "<jformat>MMMMMMMMM dd, YYYY</jformat>"
                    + "<style>years</style>"
                    + "<from>October 9, 1999</from>"
                    + "<to>October 9, 2009</to>"
                    + "</interval>"
                    + "</template>";
            
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("10", result);
            
            template = "<template>" 
                    + "<interval>"
                    + "<jformat>MMMMMMMMM dd, YYYY</jformat>"
                    + "<style>years</style>"
                    + "<from>October 10, 1999</from>"
                    + "<to>October 9, 2009</to>"
                    + "</interval>"
                    + "</template>";
            
            node = DomUtils.parseString(template);
            ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            result = defaultHandler.handle(node, ps);
            Assert.assertEquals("9", result);
            
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
}
