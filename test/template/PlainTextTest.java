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
            System.out.println("Testing empty template");
            String template = "<template></template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void simple() {
        try {
            //English
            System.out.println("Testing one line (plain text) template");
            String template = "<template>Hello World</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("Hello World", result);
            //Chinese
            template = "<template>大家好 我不好</template>";
            node = DomUtils.parseString(template);
            ps = new ParseState();
            result = defaultHandler.handle(node, ps);
            Assert.assertEquals("大家好 我不好", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}