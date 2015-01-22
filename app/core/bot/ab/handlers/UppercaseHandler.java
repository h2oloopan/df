/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * UppercaseHandler.java
 *
 */
package core.bot.ab.handlers;

import java.util.Set;

import org.w3c.dom.Node;

import core.bot.ab.ParseState;

/**
 *@author Shengying Pan (s5pan@uwaterloo.ca) 
 *@date Jan 16, 2015
 */
public class UppercaseHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result = HandlingHelper.handleChildren(node, ps, previousResult, ignoreAttributes, handlers);
        return result.toUpperCase();
    }

}
