/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * MapTagTest.java
 *
 */
package template;
import java.io.File;

import org.junit.*;
import org.w3c.dom.Node;

import supply.dummies.BotFactory;
import core.bot.Bot;
import core.bot.ab.ParseState;
import core.bot.ab.handlers.TagHandler;
import core.bot.ab.handlers.TagHandlerCollection;
import core.bot.ab.utils.DomUtils;
/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 21, 2015
 */
public class MapTagTest
{
    private TagHandlerCollection handlers;
    private Bot bot;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
        bot = BotFactory.getDummyBotWithMap(new File("test/supply/map.txt").getCanonicalPath());
    }
    
    @Test
    public void readMap() {
        try {
            System.out.println("Testing read map");
            String template = "<template><map name=\"map\">老秦</map>是帅哥</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            ps.bot = bot;
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("琨叔是帅哥", result);
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
}
