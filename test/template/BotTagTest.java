/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * BotTagTest.java
 *
 */
package template;

import java.io.File;

import org.junit.*;
import org.w3c.dom.Node;

import supply.dummies.BotFactory;
import core.bot.Bot;
import core.bot.ab.MagicStrings;
import core.bot.ab.ParseState;
import core.bot.ab.handlers.TagHandler;
import core.bot.ab.handlers.TagHandlerCollection;
import core.bot.ab.utils.DomUtils;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 21, 2015
 */
public class BotTagTest
{
    private TagHandlerCollection handlers;
    private Bot bot;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
        String path = new File("test/supply/properties.txt").getCanonicalPath();
        bot = BotFactory.getDummyBotWithProperties(path);
    }
    
    @Test
    public void getExistingBotProperty() {
        try {
            System.out.println("Testing get existing bot property");
            String template = "<template><bot name=\"name\"/>是帅哥</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = bot;
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps, "", null);
            Assert.assertEquals("老秦是帅哥", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    
    @Test
    public void getNonexistingBotProperty() {
        try {
            System.out.println("Testing get nonexisting bot property");
            String template = "<template><bot name=\"namex\"/>是帅哥</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = bot;
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps, "", null);
            Assert.assertEquals(MagicStrings.default_property + "是帅哥", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
