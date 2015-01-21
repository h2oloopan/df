/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SizeTagTest.java
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
 *@date Jan 21, 2015
 */
public class SizeTagTest
{
private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
    
    @Test
    public void zeroSize() {
        try {
            System.out.println("Testing size of brain without any category");
            String template = "<template>我的脑容量是<size/></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = BotFactory.getDummyBot();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("我的脑容量是0", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
