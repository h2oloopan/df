/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * ResponseTagTest.java
 *
 */
package template;

import org.junit.*;

import core.bot.ab.handlers.TagHandlerCollection;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 26, 2015
 */
public class ResponseTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() throws Exception {
        handlers = new TagHandlerCollection();
    }
}
