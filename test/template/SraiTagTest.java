/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SraiTagTest.java
 *
 */
package template;
import org.junit.*;

import core.bot.ab.handlers.TagHandlerCollection;
/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 21, 2015
 */
public class SraiTagTest
{
    private TagHandlerCollection handlers;
    
    @Before
    public void setup() {
        handlers = new TagHandlerCollection();
    }
}
