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
            
            Context context = new Context("dummy user", "dummy session", null);
            context.addPredicate("gender", "male");
            ps.context = context;
            
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps, "", null);
            Assert.assertEquals("帅哥", result);
            
            context.addPredicate("gender", "female");
            result = defaultHandler.handle(node, ps, "", null);
            Assert.assertEquals("美女", result);
            
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void listConditionTags() {
        try {
            System.out.println("Testing list condition tags");
            String template = "<template>你猜怎么着，"
                    + "<condition>" 
                    + "<li name=\"gender\" value=\"female\">美女</li>"
                    + "<li name=\"gender\" value=\"male\">帅哥</li>"
                    + "</condition>" 
                    + "</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            
            Context context = new Context("dummy user", "dummy session", null);
            context.addPredicate("gender", "male");
            ps.context = context;
            
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps, "", null);
            Assert.assertEquals("你猜怎么着，帅哥", result);
            
            context.addPredicate("gender", "female");
            result = defaultHandler.handle(node, ps, "", null);
            Assert.assertEquals("你猜怎么着，美女", result);
            
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void singleNameListConditionTags() {
        try {
            System.out.println("Testing single name list condition tags");
            String template = "<template>你猜怎么着，"
                    + "<condition name=\"gender\">" 
                    + "<li value=\"female\">美女</li>"
                    + "<li value=\"male\">帅哥</li>"
                    + "</condition>" 
                    + "</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            
            Context context = new Context("dummy user", "dummy session", null);
            context.addPredicate("gender", "male");
            ps.context = context;
            
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps, "", null);
            Assert.assertEquals("你猜怎么着，帅哥", result);
            
            context.addPredicate("gender", "female");
            result = defaultHandler.handle(node, ps, "", null);
            Assert.assertEquals("你猜怎么着，美女", result);
            
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
    
    @Test
    public void defaultListConditionTags() {
        try {
            System.out.println("Testing default list condition tags");
            String template = "<template>你猜怎么着，"
                    + "<condition>" 
                    + "<li name=\"gender\" value=\"female\">美女</li>"
                    + "<li>帅哥</li>"
                    + "</condition>" 
                    + "</template>";
            Node node = DomUtils.parseString(template);
            ParseState ps = new ParseState();
            
            Context context = new Context("dummy user", "dummy session", null);
            context.addPredicate("gender", "xxx");
            ps.context = context;
            
            TagHandler defaultHandler = handlers.getDefaultHandler();
            String result = defaultHandler.handle(node, ps);
            Assert.assertEquals("你猜怎么着，帅哥", result);
            
            context.addPredicate("gender", "female");
            result = defaultHandler.handle(node, ps);
            Assert.assertEquals("你猜怎么着，美女", result);
            
        } catch (Exception e) {
            Assert.fail("Should not get exception here, but " + e.getMessage());
        }
    }
}
