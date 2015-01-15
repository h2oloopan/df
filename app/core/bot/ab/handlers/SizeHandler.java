/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * SizeHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 15, 2015
 */
public class SizeHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        int size = ps.bot.brain.getCategories().size();
        return String.valueOf(size);
    }

}
