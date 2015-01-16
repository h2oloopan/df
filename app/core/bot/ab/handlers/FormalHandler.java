/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * FormalHandler.java
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
public class FormalHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result = ps.bot.handlers.getHandler("default").handle(node, ps, "", null);
        return HandlingHelper.capitalizeString(result);
    }
    
}
