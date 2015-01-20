/**
 *
 * Copyright 2015 RSVP Technologies Inc. All rights reserved.
 * DenormalizeHandler.java
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
public class DenormalizeHandler extends TagHandler
{

    @Override
    public String handle(Node node, ParseState ps, String previousResult, Set<String> ignoreAttributes) throws Exception
    {
        String result = handlers.getDefaultHandler().handle(node, ps, "", null);
        return ps.bot.preProcessor.denormalize(result);
    }

}
